package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;

abstract class ConcreteGObjectWithOptionalTerminal<T extends GObject> extends ConcreteGObject {
  private Optional<Terminal<T>> terminal;

  @SuppressWarnings("unchecked")
  protected ConcreteGObjectWithOptionalTerminal(Generic owner, UID uid, Optional<UID> terminal) {
    super(owner, uid);
    this.terminal = terminal.transform(new Function<UID, Terminal<T>>() {
      @Override
      public Terminal<T> apply(UID input) {
        return new EagerTerminal<>((T) ConcreteGObjectWithOptionalTerminal.this, input);
      }
    });
  }

  protected ConcreteGObjectWithOptionalTerminal(Generic owner,
      Entry<UID, Optional<UID>> objectAndTerminal) {
    this(owner, objectAndTerminal.getKey(), objectAndTerminal.getValue());
  }

  protected ConcreteGObjectWithOptionalTerminal(Generic owner, UID uid, Terminal<T> terminal) {
    super(owner, uid);
    this.terminal = Optional.of(terminal);
  }

  public Optional<Terminal<T>> endpoint() {
    return terminal;
  }

  public Terminal<T> terminal() throws IllegalStateException {
    return terminal.get();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{uid=" + uid().get() + ", terminal=" +
        endpoint() + '}';
  }
}
