package stupaq.labview.scripting.tools;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.ToolVI;

public class CreateBlock extends ToolVI {
  public CreateBlock(ScriptingTools application) {
    super(application, new VIPath("CreateBlock.vi"));
  }

  public UID apply(final VIPath targetVi) throws VIErrorException {
    return new UID(stdCall(targetVi));
  }
}
