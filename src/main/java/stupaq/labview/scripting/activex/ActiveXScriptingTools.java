package stupaq.labview.scripting.activex;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.jacob.extensions.DLLFromJARClassLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.VIPath;
import stupaq.labview.activex.ActiveXApplication;
import stupaq.labview.scripting.ScriptingTools;

public class ActiveXScriptingTools extends ActiveXApplication implements ScriptingTools {
  static {
    DLLFromJARClassLoader.loadLibrary();
  }

  private static final String SCRIPTING_TOOLS_PATH = "scripting.tools.path";
  private final LoadingCache<Class<ScriptingTool>, ScriptingTool> tools;
  private Path viToolsPath;

  public ActiveXScriptingTools() {
    viToolsPath = getScriptingToolsPath().toAbsolutePath();
    tools = CacheBuilder.newBuilder()
        .concurrencyLevel(1)
        .build(new CacheLoader<Class<ScriptingTool>, ScriptingTool>() {
          @SuppressWarnings("unchecked")
          @Override
          public ScriptingTool load(Class<ScriptingTool> toolClass) throws Exception {
            Class implClass =
                Class.forName(ActiveXScriptingTools.class.getPackage().getName() + "." +
                        toolClass.getSimpleName());
            return (ScriptingTool) implClass.getConstructor(ActiveXScriptingTools.class)
                .newInstance(ActiveXScriptingTools.this);
          }
        });
  }

  private static Path getScriptingToolsPath() {
    return Paths.get(System.getProperty(SCRIPTING_TOOLS_PATH, Paths.get("").toString()));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends ScriptingTool> T get(Class<T> aClass) {
    return (T) tools.getUnchecked((Class<ScriptingTool>) aClass);
  }

  public VIPath resolveToolPath(String name) {
    return new VIPath(viToolsPath, name);
  }
}
