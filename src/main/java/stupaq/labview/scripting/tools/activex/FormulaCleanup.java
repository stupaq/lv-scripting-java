package stupaq.labview.scripting.tools.activex;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class FormulaCleanup extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.FormulaCleanup {
  public FormulaCleanup(ActiveXScriptingTools tools) {
    super(tools);
  }

  @Override
  public void apply(VIPath targetVi, int formulaType, UID uid) throws VIErrorException {
    vi.stdCall(targetVi, formulaType, uid);
  }
}
