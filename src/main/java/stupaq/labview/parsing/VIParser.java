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

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.hierarchy.Bundler;
import stupaq.labview.hierarchy.CompoundArithmetic;
import stupaq.labview.hierarchy.Control;
import stupaq.labview.hierarchy.ControlArray;
import stupaq.labview.hierarchy.ControlCluster;
import stupaq.labview.hierarchy.Diagram;
import stupaq.labview.hierarchy.Formula;
import stupaq.labview.hierarchy.FormulaNode;
import stupaq.labview.hierarchy.GObject;
import stupaq.labview.hierarchy.Generic;
import stupaq.labview.hierarchy.GrowableFunction;
import stupaq.labview.hierarchy.InlineCNode;
import stupaq.labview.hierarchy.Node;
import stupaq.labview.hierarchy.Panel;
import stupaq.labview.hierarchy.RingConstant;
import stupaq.labview.hierarchy.SubVI;
import stupaq.labview.hierarchy.Terminal;
import stupaq.labview.hierarchy.Unbundler;
import stupaq.labview.hierarchy.Wire;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ControlStyle;
import stupaq.labview.scripting.tools.DataRepresentation;
import stupaq.labview.scripting.tools.ReadVI;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class VIParser {
  private static final String XML_SCHEMA_RESOURCE = "/LVXMLSchema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(VIParser.class);

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
    Map<String, ElementParser<E>> parsers = createParsers(visitor);
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
    if (xmlFile.exists() && xmlFile.lastModified() > viFile.lastModified()) {
      LOGGER.debug("Reading XML from file cache: {}", xmlFile.getPath());
      return new FileReader(xmlFile);
    } else {
      LOGGER.debug("Querying LabVIEW for XML description of: {}", viFile.getPath());
      String xmlString = tools.get(ReadVI.class).apply(viPath);
      xmlString = "<Cluster xmlns=\"http://www.ni.com/labview\">" +
          xmlString.substring("<Cluster>".length());
      try (PrintWriter writer = new PrintWriter(xmlFile)) {
        writer.println(xmlString);
      }
      return new StringReader(xmlString);
    }
  }

  private static <E extends Exception> Map<String, ElementParser<E>> createParsers(
      final VIElementsVisitor<E> visitor) {
    ElementParser<E> parser;
    Map<String, ElementParser<E>> parsers = Maps.newLinkedHashMap();
    /** {@link Diagram} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        Optional<UID> owner = Generic.OwnerOptional.get(p);
        UID uid = GObject.UID.get(p);
        visitor.Diagram(owner, uid);
      }
    };
    parsers.put(Diagram.XML_NAME, parser);
    /** {@link Panel} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        Optional<UID> owner = Generic.OwnerOptional.get(p);
        UID uid = GObject.UID.get(p);
        visitor.Panel(owner, uid);
      }
    };
    parsers.put(Panel.XML_NAME, parser);
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
    parsers.put(Terminal.XML_NAME, parser);
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
    parsers.put(Wire.XML_NAME, parser);
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
    parsers.put(InlineCNode.XML_NAME, parser);
    parsers.put(FormulaNode.XML_NAME, parser);
    /** {@link GrowableFunction} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        String className = Generic.ClassName.get(p);
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        List<UID> terms = Node.Terminals.get(p);
        Verify.verify(!terms.isEmpty());
        switch (className) {
          case CompoundArithmetic.XML_NAME:
            visitor.CompoundArithmetic(owner, uid, terms);
            break;
          case Bundler.XML_NAME:
            visitor.Bundler(owner, uid, terms);
            break;
          case Unbundler.XML_NAME:
            visitor.Unbundler(owner, uid, terms);
            break;
        }
      }
    };
    parsers.put(CompoundArithmetic.XML_NAME, parser);
    parsers.put(Bundler.XML_NAME, parser);
    parsers.put(Unbundler.XML_NAME, parser);
    /** {@link ControlCluster} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        UID terminal = Node.Terminal.get(p);
        boolean isIndicator = Control.IsIndicator.get(p);
        int controlIndex = Control.ControlIndex.get(p);
        List<UID> controls = ControlCluster.Controls.get(p);
        visitor.ControlCluster(owner, uid, label, terminal, isIndicator, controlIndex, controls);
      }
    };
    parsers.put(ControlCluster.XML_NAME, parser);
    /** {@link ControlArray} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        UID terminal = Node.Terminal.get(p);
        boolean isIndicator = Control.IsIndicator.get(p);
        int controlIndex = Control.ControlIndex.get(p);
        visitor.ControlArray(owner, uid, label, terminal, isIndicator, controlIndex);
      }
    };
    parsers.put(ControlArray.XML_NAME, parser);
    /** {@link Control} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        UID terminal = Node.Terminal.get(p);
        boolean isIndicator = Control.IsIndicator.get(p);
        int style = Control.Style.get(p);
        DataRepresentation representation = Control.Representation.get(p);
        int controlIndex = Control.ControlIndex.get(p);
        visitor.Control(owner, uid, label, terminal, isIndicator,
            ControlStyle.resolve(style, representation), controlIndex);
      }
    };
    parsers.put(Control.NUMERIC_XML_NAME, parser);
    parsers.put(Control.STRING_XML_NAME, parser);
    parsers.put(Control.VARIANT_XML_NAME, parser);
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
    parsers.put(RingConstant.XML_NAME, parser);
    /** {@link SubVI} */
    parser = new ElementParser<E>() {
      @Override
      public void parse(Element element, ElementProperties p) throws E {
        UID owner = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        List<UID> terms = Node.Terminals.get(p);
        VIPath viPath = SubVI.ViPath.get(p);
        visitor.SubVI(owner, uid, terms, viPath);
      }
    };
    parsers.put(SubVI.XML_NAME, parser);
    return parsers;
  }
}
