package stupaq.labview.scripting.activex;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class FormulaCreate extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.FormulaCreate {
  public FormulaCreate(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public UID apply(VIPath targetVi, Optional<UID> owner, int formulaType, String content,
      Optional<String> label) throws VIErrorException {
    return new UID(
        vi.stdCall(targetVi, encapsulateOwner(owner), formulaType, content, label.or("")));
  }
}
