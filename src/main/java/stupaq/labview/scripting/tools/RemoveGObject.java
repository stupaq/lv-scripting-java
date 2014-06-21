package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class RemoveGObject extends ScriptingTool {
  public RemoveGObject(ScriptingTools application) {
    super(application);
  }

  public void apply(VIPath targetVi, UID uid) throws VIErrorException {
    vi.stdCall(targetVi, uid);
  }
}
