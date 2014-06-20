// FIXME remove when deploying package
package stupaq;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.RefNum;
import stupaq.labview.VIName;
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
    EditableVI vi = new EditableVI(tools, new VIName(viToolsPath, "target3.vi"));
    RefNum b1 = vi.insertBlock();
    System.out.println(b1.toString());
  }
}
