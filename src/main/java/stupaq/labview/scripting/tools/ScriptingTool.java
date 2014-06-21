package stupaq.labview.scripting.tools;

import stupaq.labview.StdCallVI;
import stupaq.labview.scripting.ScriptingTools;

public abstract class ScriptingTool {
  protected final StdCallVI vi;

  public ScriptingTool(ScriptingTools tools) {
    vi = new StdCallVI(tools, tools.resolveToolPath(getClass().getSimpleName() + ".vi"));
  }
}
