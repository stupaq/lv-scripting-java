package stupaq.labview.scripting.tools.activex;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class CleanUpDiagram extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.CleanUpDiagram {
  public CleanUpDiagram(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public void apply(VIPath targetVi) throws VIErrorException {
    vi.stdCall(targetVi);
  }
}
