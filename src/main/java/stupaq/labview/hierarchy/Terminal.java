package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.VIErrorException;
import stupaq.labview.scripting.tools.GObjectDelete;

public abstract class Terminal<T extends GObject> extends GObject {
  public Wire connectTo(Terminal sink) {
    return connectTo(sink, Optional.<String>absent());
  }

  public Wire connectTo(Terminal sink, Optional<String> label) {
    return new Wire(rootOwner(), this, sink, label);
  }

  @Override
  protected abstract T owner();

  @Override
  public void delete() throws VIErrorException {
    scriptingTools().get(GObjectDelete.class).apply(viPath(), owner().uid().get());
  }
}
