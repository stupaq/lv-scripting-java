package stupaq.labview.scripting.tools;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class CleanUpDiagram extends ScriptingTool {
  public CleanUpDiagram(ScriptingTools application) {
    super(application);
  }

  public void apply(VIPath targetVi) throws VIErrorException {
    vi.stdCall(targetVi);
  }
}
