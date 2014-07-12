package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface FormulaCreate extends ScriptingTool {
  int FORMULA_NODE = 0;
  int INLINE_C_NODE = 1;

  UID apply(VIPath targetVi, Optional<UID> owner, int formulaType, String content,
      Optional<String> label) throws VIErrorException;
}
