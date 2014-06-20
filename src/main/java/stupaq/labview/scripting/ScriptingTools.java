package stupaq.labview.scripting;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.nio.file.Path;

import stupaq.labview.Application;
import stupaq.labview.scripting.tools.CreateBlock;
import stupaq.labview.scripting.tools.CreateWire;

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

  public CreateBlock blockCreator() {
    return (CreateBlock) tools.getUnchecked(CreateBlock.class);
  }

  public CreateWire wireCreator() {
    return (CreateWire) tools.getUnchecked(CreateWire.class);
  }

  public Path viToolsPath() {
    return viToolsPath;
  }
}
