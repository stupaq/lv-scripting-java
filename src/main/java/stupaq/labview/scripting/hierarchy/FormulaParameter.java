package stupaq.labview.scripting.hierarchy;

import java.util.Map.Entry;

import stupaq.labview.UID;

public final class FormulaParameter extends ConcreteGObjectWithTerminal<FormulaParameter> {
  public FormulaParameter(Formula owner, UID uid, UID terminal) {
    super(owner, uid, terminal);
  }

  FormulaParameter(Formula owner, Entry<UID, UID> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }
}
