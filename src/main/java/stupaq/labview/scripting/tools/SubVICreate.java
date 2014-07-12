package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface SubVICreate extends ScriptingTool {
  Entry<UID, List<UID>> apply(VIPath targetVi, Optional<UID> owner, VIPath path,
      Optional<String> description) throws VIErrorException;
}
