package stupaq.labview.scripting.tools;

import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.ToolVI;

public class CreateVI extends ToolVI {
  public CreateVI(ScriptingTools application) {
    super(application, new VIPath("CreateVI.vi"));
  }

  public void apply(VIPath targetVi) throws VIErrorException {
    stdCall(targetVi);
  }
}
