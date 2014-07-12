package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface LoopCreate extends ScriptingTool {
  int FOR_LOOP = 0;
  int WHILE_LOOP = 1;

  Entry<UID, UID> apply(VIPath targetVi, Optional<UID> owner, int loopType, Optional<String> label)
      throws VIErrorException;
}
