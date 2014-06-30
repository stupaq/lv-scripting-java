package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

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
      String label) throws VIErrorException {
    SafeArray owner1 = new SafeArray(Variant.VariantVariant, 2);
    owner1.setBoolean(0, owner.isPresent());
    owner1.setVariant(1, owner.or(UID.ZERO).toVariant());
    return new UID(vi.stdCall(targetVi, owner1, formulaType, content, label));
  }
}
