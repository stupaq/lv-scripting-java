package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.FormulaAddIO;
import stupaq.labview.scripting.tools.FormulaCreate;

import static com.google.common.base.Optional.of;

public abstract class Formula extends Node {
  public Formula(Generic owner, UID uid) {
    super(owner, uid);
  }

  protected Formula(Generic owner, int formulaType, String content, String label) {
    super(owner, owner.scriptingTools()
        .getTool(FormulaCreate.class)
        .apply(owner.viPath(), owner.uid(), formulaType, content, label));
  }

  public final Terminal<FormulaParameter> addIO(boolean isInput, Optional<String> name) {
    return new FormulaParameter(this, scriptingTools().getTool(FormulaAddIO.class)
        .apply(viPath(), formulaType(), uid().get(), isInput, name)).terminal();
  }

  protected abstract int formulaType();

  public final Terminal<FormulaParameter> addInput(String name) {
    return addIO(true, of(name));
  }

  public final Terminal<FormulaParameter> addOutput(String name) {
    return addIO(false, of(name));
  }
}
