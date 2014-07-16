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
import java.nio.file.Path;
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
import stupaq.labview.hierarchy.Formula;
import stupaq.labview.hierarchy.FormulaNode;
import stupaq.labview.hierarchy.GObject;
import stupaq.labview.hierarchy.Generic;
import stupaq.labview.hierarchy.InlineCNode;
import stupaq.labview.hierarchy.Node;
import stupaq.labview.hierarchy.Wire;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ReadVI;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class HierarchyParser {
  private static final String XML_SCHEMA_RESOURCE = "/LVXMLSchema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(HierarchyParser.class);

  private HierarchyParser() {
  }

  public static void visitVI(ScriptingTools tools, VIPath viPath, HierarchyVisitor visitor)
      throws JAXBException, SAXException, IOException {
    visitVI(parseVI(tools, viPath), visitor);
  }

  public static void visitVI(VIDump root, HierarchyVisitor visitor) {
    Map<String, ElementList> lists = Maps.newHashMap();
    for (ElementList objects : root.getArray()) {
      if (!objects.getCluster().isEmpty() && objects.getDimsize() > 0) {
        String name = objects.getCluster().get(0).getName();
        Verify.verify(!name.isEmpty());
        Verify.verify(!lists.containsKey(name));
        lists.put(name, objects);
      }
    }
    Map<String, ElementParser> parsers = createParsers(visitor);
    for (Entry<String, ElementParser> entry : parsers.entrySet()) {
      for (Element object : lists.get(entry.getKey()).getCluster()) {
        entry.getValue().parse(object, new ElementProperties(object));
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static VIDump parseVI(ScriptingTools tools, stupaq.labview.VIPath viPath)
  throws IOException, SAXException, JAXBException {
    try (Reader xmlReader = openVIXML(tools, viPath)) {
      Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI)
          .newSchema(HierarchyParser.class.getResource(XML_SCHEMA_RESOURCE));
      Unmarshaller unmarshaller = JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
      unmarshaller.setSchema(schema);
      return ((JAXBElement<VIDump>) unmarshaller.unmarshal(xmlReader)).getValue();
    }
  }

  private static Reader openVIXML(ScriptingTools tools, VIPath viPath)
      throws FileNotFoundException {
    Path path = viPath.path();
    File viFile = path.toFile();
    File xmlFile = path.resolveSibling(path.getFileName() + ".xml").toFile();
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

  private static Map<String, ElementParser> createParsers(final HierarchyVisitor visitor) {
    Map<String, ElementParser> parsers = Maps.newLinkedHashMap();
    parsers.put(Wire.XML_NAME, new ElementParser() {
      @Override
      public void parse(Element element, ElementProperties p) {
        Optional<UID> ownerUID = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        visitor.Wire(ownerUID, uid, label);
      }
    });
    parsers.put(FormulaNode.XML_NAME, new ElementParser() {
      @Override
      public void parse(Element element, ElementProperties p) {
        String className = Generic.ClassName.get(p);
        Optional<UID> ownerUID = Generic.Owner.get(p);
        UID uid = GObject.UID.get(p);
        Optional<String> label = GObject.Label.get(p);
        String expression = Formula.Expression.get(p);
        List<UID> terms = Node.Terminals.get(p);
        if (FormulaNode.XML_NAME.equals(className)) {
          visitor.FormulaNode(ownerUID, uid, expression, label, terms);
        } else if (InlineCNode.XML_NAME.equals(className)) {
          visitor.InlineCNode(ownerUID, uid, expression, label, terms);
        }
      }
    });
    parsers.put(InlineCNode.XML_NAME, parsers.get(FormulaNode.XML_NAME));
    return parsers;
  }
}
