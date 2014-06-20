package stupaq.labview.scripting.tools;

import stupaq.labview.RefNum;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIName;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.ToolVI;

public class CreateWire extends ToolVI {
  public CreateWire(ScriptingTools application) {
    super(application, new VIName(application.viToolsPath(), "CreateWire.vi"));
  }

  public RefNum apply(final VIName targetVi, final RefNum first, final RefNum second)
      throws VIErrorException {
    return new RefNum(stdCall(targetVi, first, second));
  }
}
