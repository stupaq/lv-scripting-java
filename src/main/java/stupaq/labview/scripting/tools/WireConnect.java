package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface WireConnect extends ScriptingTool {
  UID apply(VIPath targetVi, UID source, UID destination, Optional<String> label)
      throws VIErrorException;
}
