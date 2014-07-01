package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.scripting.tools.GObjectDelete;

public final class Terminal<T extends GObject> extends ConcreteGObject {
  public Terminal(T owner, UID uid) {
    super(owner, uid);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected T owner() {
    return (T) super.owner();
  }

  @Override
  public void delete() throws VIErrorException {
    scriptingTools().getTool(GObjectDelete.class).apply(viPath(), owner().uid().get());
  }
}
