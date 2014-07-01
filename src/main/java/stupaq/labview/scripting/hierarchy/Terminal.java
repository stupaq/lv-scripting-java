package stupaq.labview.scripting.hierarchy;

import stupaq.labview.VIErrorException;
import stupaq.labview.scripting.tools.GObjectDelete;

public abstract class Terminal<T extends GObject> extends GObject {
  @Override
  protected abstract T owner();

  @Override
  public void delete() throws VIErrorException {
    scriptingTools().getTool(GObjectDelete.class).apply(viPath(), owner().uid().get());
  }
}
