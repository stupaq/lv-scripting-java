package stupaq.labview.scripting.tools;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface CleanUpDiagram extends ScriptingTool {
  void apply(VIPath targetVi) throws VIErrorException;
}
