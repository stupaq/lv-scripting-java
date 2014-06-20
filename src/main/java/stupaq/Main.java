// FIXME remove when deploying package
package stupaq;

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
    ScriptingTools tools = new ScriptingTools();
    EditableVI vi = new EditableVI(tools, new VIName("target3.vi"));
    RefNum b1 = vi.insertBlock();
    System.out.println(b1.toString());
  }
}
