package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.CleanUpDiagram;
import stupaq.labview.scripting.tools.VICreate;

public class VI {
  private final ScriptingTools tools;
  private final VIPath viPath;
  private final Generic rootOwner = new Generic() {
    @Override
    public Optional<UID> uid() {
      return Optional.absent();
    }

    @Override
    protected ScriptingTools scriptingTools() {
      return tools;
    }

    @Override
    protected Generic owner() {
      return this;
    }

    @Override
    protected VIPath viPath() {
      return viPath;
    }
  };

  public VI(ScriptingTools tools, VIPath viPath) {
    this.tools = tools;
    this.viPath = viPath;
  }

  public void create() throws VIErrorException {
    tools.getTool(VICreate.class).apply(viPath);
  }

  public void cleanUpDiagram() throws VIErrorException {
    tools.getTool(CleanUpDiagram.class).apply(viPath);
  }

  public Generic generic() {
    return rootOwner;
  }
}
