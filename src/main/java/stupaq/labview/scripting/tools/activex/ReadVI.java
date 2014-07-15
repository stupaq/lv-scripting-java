package stupaq.labview.scripting.tools.activex;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class ReadVI extends ActiveXScriptingTool implements stupaq.labview.scripting.tools.ReadVI {
  public ReadVI(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public String apply(VIPath targetVi) throws VIErrorException {
    return vi.stdCall(targetVi).getString();
  }
}
