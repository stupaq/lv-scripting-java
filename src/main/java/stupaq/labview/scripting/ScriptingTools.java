package stupaq.labview.scripting;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.jacob.com.Dispatch;

import java.nio.file.Path;

import stupaq.labview.Application;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ScriptingTool;

public class ScriptingTools extends Application {

  private final LoadingCache<Class<ScriptingTool>, ScriptingTool> tools;
  private Path viToolsPath;

  public ScriptingTools(Path viToolsPath) {
    this.viToolsPath = viToolsPath;
    tools = CacheBuilder.newBuilder()
        .concurrencyLevel(1)
        .build(new CacheLoader<Class<ScriptingTool>, ScriptingTool>() {
          @Override
          public ScriptingTool load(Class<ScriptingTool> aClass) throws Exception {
            return aClass.getConstructor(ScriptingTools.class).newInstance(ScriptingTools.this);
          }
        });
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
