package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class WireConnect extends ScriptingTool {
  public WireConnect(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, UID source, UID destination, Optional<String> label)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi, source, destination, label.or("")));
  }
}
