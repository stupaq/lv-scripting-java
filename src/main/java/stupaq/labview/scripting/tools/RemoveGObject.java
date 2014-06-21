package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.ToolVI;

public class RemoveGObject extends ToolVI {
  public RemoveGObject(ScriptingTools application) {
    super(application, new VIPath("RemoveGObject.vi"));
  }

  public void apply(VIPath targetVi, UID uid) throws VIErrorException {
    stdCall(targetVi, uid);
  }
}
