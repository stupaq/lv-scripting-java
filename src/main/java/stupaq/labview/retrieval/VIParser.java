package stupaq.labview.retrieval;

import com.ni.labview.ClusterType;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ReadVI;

public class VIParser {
  public VIParser(ScriptingTools tools, VIPath viPath) throws IOException {
    this(tools, viPath, true);
  }

  public VIParser(ScriptingTools tools, VIPath viPath, boolean fromCache) throws IOException {
    String xmlString;
    Path path = viPath.path();
    Path xmlPath = path.resolveSibling(path.getFileName() + ".xml");
    if (fromCache && Files.exists(xmlPath)) {
      xmlString = com.google.common.io.Files.toString(xmlPath.toFile(), Charset.defaultCharset());
    } else {
      xmlString = tools.get(ReadVI.class).apply(viPath);
      try (PrintWriter writer = new PrintWriter(xmlPath.toString())) {
        writer.println(xmlString);
      }
    }
    ClusterType allElements = JAXB.unmarshal(new StringReader(xmlString), ClusterType.class);
    System.out.println(allElements.getName());
    System.out.println(allElements.getNumElts());
  }
}
