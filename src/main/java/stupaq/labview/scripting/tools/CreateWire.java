package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class CreateWire extends ScriptingTool {
  public CreateWire(ScriptingTools application) {
    super(application);
  }

  public UID apply(final VIPath targetVi, final UID first, final UID second)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi, first, second));
  }
}
