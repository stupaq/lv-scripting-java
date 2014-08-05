package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface HighlightByUID extends ScriptingTool {
  void apply(VIPath targetVi, UID uid) throws VIErrorException;
}
