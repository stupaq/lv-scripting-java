package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface ControlArrayCreate extends ScriptingTool {
  Entry<UID, Optional<UID>> apply(VIPath targetVi, Optional<UID> owner, boolean isIndicator,
      int dimensions, Optional<String> label, int connPaneIndex, boolean hasTerminal)
      throws VIErrorException;
}
