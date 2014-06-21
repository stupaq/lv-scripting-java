package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class CreateBlock extends ScriptingTool {
  public CreateBlock(ScriptingTools application) {
    super(application);
  }

  public UID apply(final VIPath targetVi) throws VIErrorException {
    return new UID(vi.stdCall(targetVi));
  }
}
