// FIXME remove when deploying package

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.EditableVI;
import stupaq.labview.scripting.ScriptingTools;

@SuppressWarnings("deprecation")
public class example {

  public static void main(String[] args) {
    try {
      run(args);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  private static void run(String[] args) throws Exception {
    Path projectPath = Paths.get(System.getProperty(ScriptingTools.SCRIPTING_TOOLS_PATH));
    ScriptingTools tools = new ScriptingTools();
    EditableVI vi = new EditableVI(tools, new VIPath(projectPath, "target3.vi"));
    //vi.create();
    UID b1 = vi.inlineCNodeCreate("b1");
    UID b2 = vi.inlineCNodeCreate("b2");
    UID t1 = vi.inlineCNodeAddIO(b1, false, "out1");
    UID t2 = vi.inlineCNodeAddIO(b2, true, "in2");
    vi.connectWire(t1, t2);
    //vi.removeObject(b1);
    //vi.removeObject(b2);
  }
}
