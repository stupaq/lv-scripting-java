package stupaq.labview.hierarchy;

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

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ReadVI;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class ParsedHierarchy {
  private static final String XML_SCHEMA_RESOURCE = "/LVXMLSchema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(ParsedHierarchy.class);

  public ParsedHierarchy(VIPath viPath, VIDump root) {
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
  public static ParsedHierarchy parseVI(ScriptingTools tools, VIPath viPath)
      throws IOException, SAXException, JAXBException {
    try (Reader xmlReader = openVIXML(tools, viPath)) {
      Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI)
          .newSchema(ParsedHierarchy.class.getResource(XML_SCHEMA_RESOURCE));
      Unmarshaller unmarshaller = JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
      unmarshaller.setSchema(schema);
      VIDump root = ((JAXBElement<VIDump>) unmarshaller.unmarshal(xmlReader)).getValue();
      return new ParsedHierarchy(viPath, root);
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

  private static Map<String, ElementParser> createParsers(final ParsedHierarchy hierarchy) {
    Map<String, ElementParser> parsers = Maps.newHashMap();
    parsers.put(Formula.XML_NAME, new ElementParser() {
      @Override
      public void parse(Element element) {
      }
    });
    return parsers;
  }

  private static abstract class ElementParser {
    public abstract void parse(Element element);
  }

  private static class ElementProperties {
    public ElementProperties(Element element) {
    }
  }
}
