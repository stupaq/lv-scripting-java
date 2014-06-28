import java.nio.file.Files;

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
    if (args.length == 1) {
      ScriptingTools tools = new ScriptingTools();
      VIPath path = new VIPath(args[0]);
      Files.deleteIfExists(path.path());
      EditableVI vi = new EditableVI(tools, path);
      vi.create();
      UID b1 = vi.inlineCNodeCreate("block 1");
      UID b2 = vi.inlineCNodeCreate("block 2");
      UID t1 = vi.inlineCNodeAddIO(b1, false, "out 1");
      UID t2 = vi.inlineCNodeAddIO(b2, true, "in 2");
      UID w1 = vi.connectWire(t1, t2, "wire 1");
      UID b3 = vi.inlineCNodeCreate("to be removed 1");
      vi.cleanUpDiagram();
      System.out.println(b1 + " " + b2 + " " + t1 + " " + t2 + " " + w1 + " " + b3);
      vi.removeObject(b3);
    } else {
      throw new IllegalArgumentException("Arguments: path-to-a-VI");
    }
  }
}
