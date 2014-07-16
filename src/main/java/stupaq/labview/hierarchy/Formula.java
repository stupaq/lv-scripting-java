package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.tools.FormulaCleanup;
import stupaq.labview.scripting.tools.FormulaCreate;

import static com.google.common.base.Optional.of;
import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castListUID;
import static stupaq.labview.parsing.LVPropertyCast.castString;

public abstract class Formula extends Node {
  public static final LVProperty<String> Expression = Cast("FormExpr", castString);
  public static final LVProperty<List<stupaq.labview.UID>> Parameters =
      Cast("FormulaParameters[]", castListUID);

  protected Formula(Generic owner, int formulaType, String content, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .get(FormulaCreate.class)
        .apply(owner.viPath(), owner.uid(), formulaType, content, label));
  }

  public final Terminal<FormulaParameter> addIO(boolean isInput, Optional<String> name) {
    return new FormulaParameter(this,
        scriptingTools().get(stupaq.labview.scripting.tools.FormulaAddIO.class)
            .apply(viPath(), formulaType(), uid().get(), isInput, name)).terminal();
  }

  protected abstract int formulaType();

  public final Terminal<FormulaParameter> addInput(String name) {
    return addIO(true, of(name));
  }

  public final Terminal<FormulaParameter> addOutput(String name) {
    return addIO(false, of(name));
  }

  public final void cleanupFormula() {
    scriptingTools().get(FormulaCleanup.class).apply(viPath(), formulaType(), uid().get());
  }
}
