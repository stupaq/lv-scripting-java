package stupaq.labview.parsing;

import com.google.common.base.Optional;
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
import java.util.Map;

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
import stupaq.labview.hierarchy.InlineCNode;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ReadVI;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class HierarchyParser {
  private static final String XML_SCHEMA_RESOURCE = "/LVXMLSchema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(HierarchyParser.class);

  public HierarchyParser(ScriptingTools tools, VIPath viPath, VIDump root) {
    Map<String, ElementParser> parsers = createParsers(this);
    for (ElementList objects : root.getArray()) {
      if (!objects.getCluster().isEmpty() && objects.getDimsize() > 0) {
        // We can trim the number of parser look-ups because we know that elements are emitted into
        // arrays by type.
        String name = objects.getCluster().get(0).getName();
        ElementParser parser = parsers.get(name);
        if (parser != null) {
          for (Element object : objects.getCluster()) {
            parser.parse(object);
          }
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static HierarchyParser parseVI(ScriptingTools tools, VIPath viPath)
  throws IOException, SAXException, JAXBException {
    try (Reader xmlReader = openVIXML(tools, viPath)) {
      Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI)
          .newSchema(HierarchyParser.class.getResource(XML_SCHEMA_RESOURCE));
      Unmarshaller unmarshaller = JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
      unmarshaller.setSchema(schema);
      VIDump root = ((JAXBElement<VIDump>) unmarshaller.unmarshal(xmlReader)).getValue();
      return new HierarchyParser(tools, viPath, root);
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

  private static Map<String, ElementParser> createParsers(final HierarchyParser hierarchy) {
    Map<String, ElementParser> parsers = Maps.newHashMap();
    parsers.put(Formula.XML_NAME, new ElementParser() {
      @Override
      public void parse(Element element) {
        ElementProperties p = new ElementProperties(element);
        UID uid = Formula.UID.get(p);
        String className = Formula.ClassName.get(p);
        Optional<UID> ownerUID = Formula.Owner.get(p);
        // FIXME
        String expression = Formula.Expression.get(p);
        Optional<String> label = Formula.Label.get(p);
        Formula formula;
        if (FormulaNode.XML_NAME.equals(className)) {
        } else if (InlineCNode.XML_NAME.equals(className)) {
        }
        System.out.println(uid + " | " + className);
      }
    });
    return parsers;
  }
}
