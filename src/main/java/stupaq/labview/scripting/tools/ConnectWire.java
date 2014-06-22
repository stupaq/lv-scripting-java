package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class ConnectWire extends ScriptingTool {
  public ConnectWire(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, UID source, UID destination) throws VIErrorException {
    return new UID(vi.stdCall(targetVi, source, destination));
  }
}
