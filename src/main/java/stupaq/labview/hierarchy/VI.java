package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.tools.CleanUpDiagram;
import stupaq.labview.scripting.tools.ConnectorPanePattern;
import stupaq.labview.scripting.tools.VICreate;

public class VI extends Generic {
  private final ScriptingTools tools;
  private final VIPath viPath;

  public VI(ScriptingTools tools, VIPath viPath, ConnectorPanePattern pattern) {
    this.tools = tools;
    this.viPath = viPath;
    tools.get(VICreate.class).apply(viPath, pattern);
  }

  @Override
  public Optional<UID> uid() {
    return Optional.absent();
  }

  @Override
  public Generic owner() {
    return this;
  }

  @Override
  protected ScriptingTools scriptingTools() {
    return tools;
  }

  @Override
  protected VIPath viPath() {
    return viPath;
  }

  public void cleanUpDiagram() throws VIErrorException {
    tools.get(CleanUpDiagram.class).apply(viPath);
  }
}
