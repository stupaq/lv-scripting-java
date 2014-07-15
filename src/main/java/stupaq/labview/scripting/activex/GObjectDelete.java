package stupaq.labview.scripting.activex;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class GObjectDelete extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.GObjectDelete {
  public GObjectDelete(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public void apply(VIPath targetVi, UID uid) throws VIErrorException {
    vi.stdCall(targetVi, uid);
  }
}
