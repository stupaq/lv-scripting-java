package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface FormulaAddIO extends ScriptingTool {
  Entry<UID, UID> apply(VIPath targetVi, int formulaType, UID uid, boolean isInput,
      Optional<String> name) throws VIErrorException;
}
