// FIXME remove when deploying package
package stupaq;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.EditableVI;
import stupaq.labview.scripting.ScriptingTools;

@SuppressWarnings("deprecation")
public class Main {

  public static void main(String[] args) {
    try {
      run(args);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  private static void run(String[] args) throws Exception {
    Path viToolsPath = Paths.get("C:\\Documents and Settings\\user\\Pulpit\\lv-scripting\\");
    ScriptingTools tools = new ScriptingTools(viToolsPath);
    EditableVI vi = new EditableVI(tools, new VIPath(viToolsPath, "target3.vi"));
    //vi.create();
    UID b1 = vi.insertBlock();
    System.out.println(b1);
    UID b2 = vi.insertBlock();
    System.out.println(b2);
    vi.removeObject(b1);
    vi.removeObject(b2);
  }
}
