package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;

abstract class ConcreteGObjectWithTerminal<T extends GObject>
    extends ConcreteGObjectWithOptionalTerminal<T> {
  protected ConcreteGObjectWithTerminal(Generic owner, Entry<UID, UID> objectAndTerminal) {
    super(owner, objectAndTerminal.getKey(), Optional.of(objectAndTerminal.getValue()));
  }

  public Terminal<T> terminal() {
    return endpoint().get();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{uid=" + uid().get() + ", terminal=" + terminal() + '}';
  }
}
