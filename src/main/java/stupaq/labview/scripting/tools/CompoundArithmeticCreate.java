package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface CompoundArithmeticCreate extends ScriptingTool {
  Entry<UID, List<UID>> apply(VIPath targetVi, Optional<UID> owner, ArithmeticMode mode, int inputs,
      Optional<String> label) throws VIErrorException;
}
