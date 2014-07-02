package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class FormulaCreate extends ScriptingTool {
  public static final int FORMULA_NODE = 0, INLINE_C_NODE = 1;

  public FormulaCreate(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, Optional<UID> owner, int formulaType, String content,
      Optional<String> label) throws VIErrorException {
    return new UID(
        vi.stdCall(targetVi, encapsulateOwner(owner), formulaType, content, label.or("")));
  }
}
