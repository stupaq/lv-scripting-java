package stupaq.labview.scripting;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.jacob.com.Dispatch;
import com.jacob.extensions.DLLFromJARClassLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.Application;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ScriptingTool;

public class ScriptingTools extends Application {
  static {
    DLLFromJARClassLoader.loadLibrary();
  }

  private static final String SCRIPTING_TOOLS_PATH = "scripting.tools.path";
  private final LoadingCache<Class<ScriptingTool>, ScriptingTool> tools;
  private Path viToolsPath;

  public ScriptingTools() {
    viToolsPath = getScriptingToolsPath().toAbsolutePath();
    tools = CacheBuilder.newBuilder()
        .concurrencyLevel(1)
        .build(new CacheLoader<Class<ScriptingTool>, ScriptingTool>() {
          @Override
          public ScriptingTool load(Class<ScriptingTool> aClass) throws Exception {
            return aClass.getConstructor(ScriptingTools.class).newInstance(ScriptingTools.this);
          }
        });
  }

  public Path getScriptingToolsPath() {
    return Paths.get(System.getProperty(SCRIPTING_TOOLS_PATH, Paths.get("").toString()));
  }

  @SuppressWarnings("unchecked")
  public <T> T getTool(Class<T> aClass) {
    return (T) tools.getUnchecked((Class<ScriptingTool>) aClass);
  }

  @Override
  public Dispatch openVI(VIPath viPath) {
    return super.openVI(viPath);
  }

  public VIPath resolveToolPath(String name) {
    return new VIPath(viToolsPath, name);
  }
}
