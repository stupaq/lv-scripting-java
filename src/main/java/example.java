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
      UID b1 = vi.formulaNodeCreate("block 1", "block label 1", false);
      UID b2 = vi.formulaNodeCreate("block 2", "block label 2", false);
      UID t1 = vi.formulaNodeAddIO(b1, false, "out 1", false);
      UID t2 = vi.formulaNodeAddIO(b2, true, "in 2", false);
      UID w1 = vi.connectWire(t1, t2, "wire 1");
      UID b3 = vi.formulaNodeCreate("this block will be removed", "", false);
      vi.cleanUpDiagram();
      System.out.println(b1 + " " + b2 + " " + t1 + " " + t2 + " " + w1 + " " + b3);
      vi.removeObject(b3);
    } else {
      throw new IllegalArgumentException("Arguments: path-to-a-VI");
    }
  }
}
