package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import java.util.Map;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface RingConstantCreate extends ScriptingTool {
  Entry<UID, Optional<UID>> apply(VIPath targetVi, Optional<UID> owner,
      Map<String, ?> stringsAndValues, DataRepresentation representation, Optional<String> label,
      boolean hasTerminal) throws VIErrorException;
}
