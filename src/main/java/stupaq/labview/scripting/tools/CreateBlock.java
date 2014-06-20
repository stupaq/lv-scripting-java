package stupaq.labview.scripting.tools;

import stupaq.labview.RefNum;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIName;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.ToolVI;

public class CreateBlock extends ToolVI {
  public CreateBlock(ScriptingTools application) {
    super(application, new VIName(application.viToolsPath(), "CreateBlock.vi"));
  }

  public RefNum apply(final VIName targetVi) throws VIErrorException {
    return new RefNum(stdCall(targetVi));
  }
}
