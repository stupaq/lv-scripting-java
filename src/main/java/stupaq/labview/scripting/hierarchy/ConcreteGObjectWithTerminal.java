package stupaq.labview.scripting.hierarchy;

import java.util.Map.Entry;

import stupaq.labview.UID;

public class ConcreteGObjectWithTerminal<T extends GObject> extends ConcreteGObject {
  private Terminal<T> terminal;

  protected ConcreteGObjectWithTerminal(Generic owner, UID uid, Terminal<T> terminal) {
    super(owner, uid);
    this.terminal = terminal;
  }

  @SuppressWarnings("unchecked")
  protected ConcreteGObjectWithTerminal(Generic owner, UID uid, UID terminal) {
    super(owner, uid);
    this.terminal = new Terminal<>((T) this, terminal);
  }

  protected ConcreteGObjectWithTerminal(Generic owner, Entry<UID, UID> objectAndTerminal) {
    this(owner, objectAndTerminal.getKey(), objectAndTerminal.getValue());
  }

  public Terminal<T> terminal() {
    return terminal;
  }
}
