package stupaq.labview.hierarchy;

import java.util.Map.Entry;

import stupaq.labview.UID;

public final class FormulaParameter extends ConcreteGObjectWithTerminal<FormulaParameter> {
  FormulaParameter(Formula owner, Entry<UID, UID> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }
}
