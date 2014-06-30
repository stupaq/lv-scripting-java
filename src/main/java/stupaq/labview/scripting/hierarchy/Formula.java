package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.FormulaAddIO;
import stupaq.labview.scripting.tools.FormulaCreate;

public abstract class Formula extends Node {
  public Formula(Generic owner, UID uid) {
    super(owner, uid);
  }

  protected Formula(Generic owner, int formulaType, String content, String label) {
    super(owner, owner.scriptingTools()
        .getTool(FormulaCreate.class)
        .apply(owner.viPath(), owner.uid(), formulaType, content, label));
  }

  public final Terminal addIO(boolean isInput, String name) {
    UID terminal = scriptingTools().getTool(FormulaAddIO.class)
        .apply(viPath(), formulaType(), uid().get(), isInput, name);
    return new Terminal(this, terminal);
  }

  protected abstract int formulaType();

  public final Terminal addInput() {
    return addInput("");
  }

  public final Terminal addInput(String name) {
    return addIO(true, name);
  }

  public final Terminal addOutput() {
    return addOutput("");
  }

  public final Terminal addOutput(String name) {
    return addIO(false, name);
  }
}
