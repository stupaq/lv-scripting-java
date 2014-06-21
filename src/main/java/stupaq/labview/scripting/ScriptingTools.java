package stupaq.labview.scripting;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import com.jacob.com.Dispatch;

import java.nio.file.Path;

import stupaq.labview.Application;
import stupaq.labview.VIPath;

public class ScriptingTools extends Application {

  private final LoadingCache<Class<? extends ToolVI>, ? extends ToolVI> tools;
  private Path viToolsPath;

  public ScriptingTools(Path viToolsPath) {
    this.viToolsPath = viToolsPath;
    tools = CacheBuilder.newBuilder()
        .concurrencyLevel(1)
        .build(new CacheLoader<Class<? extends ToolVI>, ToolVI>() {
          @Override
          public ToolVI load(Class<? extends ToolVI> aClass) throws Exception {
            return aClass.getConstructor(ScriptingTools.class).newInstance(ScriptingTools.this);
          }
        });
  }

  @SuppressWarnings("unchecked")
  public <Tool> Tool getTool(Class<Tool> aClass) {
    return (Tool) tools.getUnchecked((Class<? extends ToolVI>) aClass);
  }

  @Override
  public Dispatch openVI(VIPath viPath) {
    return super.openVI(new VIPath(viToolsPath, viPath));
  }
}
