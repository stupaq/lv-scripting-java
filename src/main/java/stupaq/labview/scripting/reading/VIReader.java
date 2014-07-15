package stupaq.labview.scripting.reading;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.ReadVI;

public class VIReader {
  public VIReader(ScriptingTools tools, VIPath path) {
    String viXml = tools.get(ReadVI.class).apply(path);
    System.out.println(viXml);
  }
}
