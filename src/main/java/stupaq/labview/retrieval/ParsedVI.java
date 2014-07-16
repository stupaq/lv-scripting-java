package stupaq.labview.retrieval;

import com.ni.labview.LVDataRootType;
import com.ni.labview.ObjectFactory;

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

public class ParsedVI {
  private static final String XML_SCHEMA_RESOURCE = "/LVXMLSchema.xsd";
  private static final Logger LOGGER = LoggerFactory.getLogger(ParsedVI.class);

  public ParsedVI(VIPath viPath) throws IOException, JAXBException, SAXException {
  }

  public static ParsedVI parseVI(ScriptingTools tools, VIPath viPath)
      throws IOException, SAXException, JAXBException {
    try (Reader xmlReader = openVIXML(tools, viPath)) {
      Schema schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI)
          .newSchema(ParsedVI.class.getResource(XML_SCHEMA_RESOURCE));
      Unmarshaller unmarshaller = JAXBContext.newInstance(ObjectFactory.class).createUnmarshaller();
      unmarshaller.setSchema(schema);
      LVDataRootType root =
          ((JAXBElement<LVDataRootType>) unmarshaller.unmarshal(xmlReader)).getValue();
      // FIXME
      System.out.println(root.getVersionOrI8OrI16());
      // FIXME
      return new ParsedVI(viPath);
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
      xmlString = xmlString.replace("<Val></Val>", "<Val>0</Val>");
      StringBuilder builder = new StringBuilder();
      builder.append("<LVData xmlns=\"http://www.ni.com/labview\">");
      builder.append(xmlString);
      builder.append("</LVData>");
      xmlString = builder.toString();
      try (PrintWriter writer = new PrintWriter(xmlFile)) {
        writer.println(xmlString);
      }
      return new StringReader(xmlString);
    }
  }
}
