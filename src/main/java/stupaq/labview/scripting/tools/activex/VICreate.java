package stupaq.labview.scripting.tools.activex;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ConnectorPanePattern;

class VICreate extends ActiveXScriptingTool implements stupaq.labview.scripting.tools.VICreate {
  public VICreate(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public void apply(VIPath targetVi, ConnectorPanePattern pattern) throws VIErrorException {
    vi.stdCall(targetVi, pattern);
  }
}
