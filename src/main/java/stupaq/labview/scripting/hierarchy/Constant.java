package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;

public class Constant extends ConcreteGObjectWithOptionalTerminal<Constant> {
  protected Constant(Generic owner, Entry<UID, Optional<UID>> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }

  protected static boolean hasTerminal(Generic owner) {
    return !(owner instanceof Constant);
  }
}
