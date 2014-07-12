package stupaq.labview.scripting;

public interface ScriptingTools {
  public <T extends ScriptingTool> T get(Class<T> aClass);

  public interface ScriptingTool {
  }
}
