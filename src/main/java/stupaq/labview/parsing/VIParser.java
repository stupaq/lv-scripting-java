package stupaq.labview.parsing;

import com.google.common.base.Optional;
import com.google.common.base.Verify;
import com.google.common.collect.Maps;

import com.ni.labview.Element;
import com.ni.labview.ElementList;
import com.ni.labview.ObjectFactory;
import com.ni.labview.VIDump;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import stupaq.labview.Configuration;
import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.hierarchy.*;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ControlStyle;
import stupaq.labview.scripting.tools.DataRepresentation;
import stupaq.labview.scripting.tools.ReadVI;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public final class VIParser {
  private static final String XML_SCHEMA_RESOURCE = "/LVXMLSchema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(VIParser.class);
  private static final boolean USE_CACHE = Configuration.getParserCache();

  private VIParser() {
  }

  public static <E extends Exception> void visitVI(ScriptingTools tools, VIPath viPath,
      VIElementsVisitor<E> visitor) throws JAXBException, SAXException, IOException, E {
    visitVI(parseVI(tools, viPath), visitor);
  }

  public static <E extends Exception> void visitVI(VIDump root, VIElementsVisitor<E> visitor)
      throws E {
    Map<String, ElementList> lists = Maps.newHashMap();
    for (ElementList objects : root.getArray()) {
      if (!objects.getCluster().isEmpty() && objects.getDimsize() > 0) {
        String name = objects.getCluster().get(0).getName();
        Verify.verify(!name.isEmpty());
        Verify.verify(!lists.containsKey(name));
        lists.put(name, objects);
      }
    }
    Map<String, ElementParser<E>> parsers = prepareParsers(visitor);
    for (Entry<String, ElementParser<E>> entry : parsers.entrySet()) {
      ElementList list = lists.get(entry.getKey());
      if (list != null) {
        for (Element object : list.getCluster()) {
          entry.getValue().parse(object, new ElementProperties(object));
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static VIDump parseVI(ScriptingTools tools, stupaq.labview.VIPath viPath)
      throws IOException, SAXException, JAXBException {
    try (Reader xmlReader = openVIXML(tools, viPath)) {
      Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI)
          .newSchema(VIParser.class.getResource(XML_SCHEMA_RESOURCE));
      Unmarshaller unmarshaller = JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
      unmarshaller.setSchema(schema);
      return ((JAXBElement<VIDump>) unmarshaller.unmarshal(xmlReader)).getValue();
    }
  }

  private static Reader openVIXML(ScriptingTools tools, VIPath viPath)
      throws FileNotFoundException {
    File viFile = viPath.path().toFile();
    File xmlFile = viPath.path().resolveSibling(viPath.getFileName() + ".xml").toFile();
    if (USE_CACHE) {
      if (xmlFile.exists() && xmlFile.lastModified() > viFile.lastModified()) {
        LOGGER.debug("Reading XML from file cache: {}", xmlFile.getPath());
        return new FileReader(xmlFile);
      }
    }
    LOGGER.debug("Querying LabVIEW for XML description of: {}", viFile.getPath());
    String xmlString = tools.get(ReadVI.class).apply(viPath);
    xmlString =
        "<Cluster xmlns=\"http://www.ni.com/labview\">" + xmlString.substring("<Cluster>".length());
    if (USE_CACHE) {
      try (PrintWriter writer = new PrintWriter(xmlFile)) {
        LOGGER.debug("Writing XML cache file: {}", xmlFile.getPath());
        writer.println(xmlString);
      }
    }
    return new StringReader(xmlString);
  }

  private static <E extends Exception> Map<String, ElementParser<E>> prepareParsers(
      final VIElementsVisitor<E> visitor) {
    ElementParser<E> parser;
    Map<String, ElementParser<E>> allParsers = Maps.newLinkedHashMap();
    /** {@link Diagram} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        Optional<UID> owner = Generic.OwnerOptional.get(p);
        UID uid = GObject.UID.get(p);
        visitor.Diagram(owner, uid);
      }
    };
    allParsers.put(Diagram.XML_NAME, parser);
    /** {@link Panel} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        Optional<UID> owner = Generic.OwnerOptional.get(p);
        UID uid = GObject.UID.get(p);
        visitor.Panel(owner, uid);
      }
    };
    allParsers.put(Panel.XML_NAME, parser);
    /** {@link Terminal} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        UID wire = Terminal.Wire.get(p);
        boolean isSource = Terminal.IsSource.get(p);
        String name = Terminal.Name.get(p);
        visitor.Terminal(owner, uid, wire, isSource, name);
      }
    };
    allParsers.put(Terminal.XML_NAME, parser);
    /** {@link Wire} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        visitor.Wire(owner, uid, label);
      }
    };
    allParsers.put(Wire.XML_NAME, parser);
    /** {@link Formula} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        String className = Generic.ClassName.get(p);
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        String expression = Formula.Expression.get(p);
        List<UID> terms = Node.Terminals.get(p);
        switch (className) {
          case FormulaNode.XML_NAME:
            visitor.FormulaNode(owner, uid, expression, label, terms);
            break;
          case InlineCNode.XML_NAME:
            visitor.InlineCNode(owner, uid, expression, label, terms);
            break;
        }
      }
    };
    allParsers.put(InlineCNode.XML_NAME, parser);
    allParsers.put(FormulaNode.XML_NAME, parser);
    /** {@link GrowableFunction} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        String className = Generic.ClassName.get(p);
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        List<UID> multiple = Node.Terminals.get(p);
        Verify.verify(!multiple.isEmpty());
        UID single = multiple.get(0);
        multiple = multiple.subList(1, multiple.size());
        switch (className) {
          case CompoundArithmetic.XML_NAME:
            visitor.CompoundArithmetic(owner, uid, single, multiple);
            break;
          case Bundler.XML_NAME:
            multiple = multiple.subList(1, multiple.size());
            visitor.Bundler(owner, uid, single, multiple);
            break;
          case Unbundler.XML_NAME:
            visitor.Unbundler(owner, uid, single, multiple);
            break;
        }
      }
    };
    allParsers.put(CompoundArithmetic.XML_NAME, parser);
    allParsers.put(Bundler.XML_NAME, parser);
    allParsers.put(Unbundler.XML_NAME, parser);
    /** {@link ConnectorPane} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        List<UID> controls = ConnectorPane.Controls.get(p);
        visitor.ConnectorPane(controls);
      }
    };
    allParsers.put(ConnectorPane.XML_NAME, parser);
    /** {@link ControlCluster} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        UID terminal = Node.Terminal.get(p);
        boolean isIndicator = Control.IsIndicator.get(p);
        List<UID> controls = ControlCluster.Controls.get(p);
        visitor.ControlCluster(owner, uid, label, terminal, isIndicator, controls);
      }
    };
    allParsers.put(ControlCluster.XML_NAME, parser);
    /** {@link ControlArray} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        UID terminal = Node.Terminal.get(p);
        boolean isIndicator = Control.IsIndicator.get(p);
        visitor.ControlArray(owner, uid, label, terminal, isIndicator);
      }
    };
    allParsers.put(ControlArray.XML_NAME, parser);
    /** {@link Control} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        String description = GObject.Description.get(p);
        UID terminal = Node.Terminal.get(p);
        boolean isIndicator = Control.IsIndicator.get(p);
        int style = Control.Style.get(p);
        DataRepresentation representation = Control.Representation.get(p);
        visitor.Control(owner, uid, label, terminal, isIndicator,
            ControlStyle.resolve(style, representation), description);
      }
    };
    allParsers.put(Control.NUMERIC_XML_NAME, parser);
    allParsers.put(Control.STRING_XML_NAME, parser);
    allParsers.put(Control.VARIANT_XML_NAME, parser);
    /** {@link RingConstant} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        UID terminal = Node.Terminal.get(p);
        Map<String, Object> stringsAndValues = RingConstant.StringsAndValues.get(p);
        visitor.RingConstant(owner, uid, label, terminal, stringsAndValues);
      }
    };
    allParsers.put(RingConstant.XML_NAME, parser);
    /** {@link SubVI} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        String description = GObject.Description.get(p);
        List<UID> terms = Node.Terminals.get(p);
        VIPath viPath = SubVI.ViPath.get(p);
        visitor.SubVI(owner, uid, terms, viPath, description);
      }
    };
    allParsers.put(SubVI.XML_NAME, parser);
    /** Rearrange entries */
    Iterable<String> order = visitor.parsersOrder();
    if (order == null) {
      return allParsers;
    } else {
      Map<String, ElementParser<E>> sortedParsers = Maps.newLinkedHashMap();
      for (String name : order) {
        sortedParsers.put(name, allParsers.get(name));
      }
      return sortedParsers;
    }
  }
}
