package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.ToolVI;

public class CreateWire extends ToolVI {
  public CreateWire(ScriptingTools application) {
    super(application, new VIPath("CreateWire.vi"));
  }

  public UID apply(final VIPath targetVi, final UID first, final UID second)
      throws VIErrorException {
    return new UID(stdCall(targetVi, first, second));
  }
}
