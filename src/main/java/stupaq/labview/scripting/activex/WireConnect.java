package stupaq.labview.scripting.activex;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class WireConnect extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.WireConnect {
  public WireConnect(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public UID apply(VIPath targetVi, UID source, UID destination, Optional<String> label)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi, source, destination, label.or("")));
  }
}
