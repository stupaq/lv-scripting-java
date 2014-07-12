package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;

public final class LazyTerminal<T extends GObject> extends Terminal<T> {
  private Supplier<Terminal<T>> supplier;
  private Terminal<T> terminal;

  public LazyTerminal(Supplier<Terminal<T>> supplier) {
    this.supplier = supplier;
    this.terminal = null;
  }

  private Terminal<T> terminal() {
    if (terminal == null) {
      terminal = supplier.get();
      supplier = null;
    }
    return terminal;
  }

  @Override
  public Optional<UID> uid() {
    return Optional.of(terminal().uid().get());
  }

  @Override
  public String toString() {
    if (terminal != null) {
      return super.toString();
    } else if (supplier != null) {
      return Terminal.class.getSimpleName() + "{not created}";
    } else {
      return Terminal.class.getSimpleName() + "{deleted}";
    }
  }

  @Override
  protected T owner() {
    return terminal().owner();
  }

  @Override
  public void delete() throws VIErrorException {
    if (terminal != null) {
      super.delete();
    }
    terminal = null;
    supplier = null;
  }
}
