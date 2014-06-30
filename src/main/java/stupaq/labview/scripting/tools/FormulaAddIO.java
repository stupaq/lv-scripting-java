package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class FormulaAddIO extends ScriptingTool {
  public FormulaAddIO(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, int formulaType, UID uid, boolean isInput, String name)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi, formulaType, uid, isInput, name));
  }
}
