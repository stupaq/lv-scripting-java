package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class WireConnect extends ScriptingTool {
  public WireConnect(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, UID source, UID destination, String label)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi, source, destination, label));
  }
}
