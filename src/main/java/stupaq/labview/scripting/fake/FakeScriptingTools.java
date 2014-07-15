package stupaq.labview.scripting.fake;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class FakeScriptingTools implements ScriptingTools {
  @Override
  public <T extends ScriptingTool> T get(Class<T> aClass) {
    throw new RuntimeException(getClass().getSimpleName());
  }

  public VIPath resolveToolPath(String name) {
    throw new RuntimeException(getClass().getSimpleName());
  }
}
