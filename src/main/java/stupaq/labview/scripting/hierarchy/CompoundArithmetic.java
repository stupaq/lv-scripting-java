package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.scripting.tools.CompoundArithmeticCreate;
import stupaq.labview.scripting.tools.CompoundArithmeticCreate.ArithmeticMode;

public final class CompoundArithmetic extends GrowableFunction<CompoundArithmetic> {
  public CompoundArithmetic(Generic owner, ArithmeticMode mode, int inputs,
      Optional<String> label) {
    super(owner, owner.scriptingTools()
        .getTool(CompoundArithmeticCreate.class)
        .apply(owner.viPath(), owner.uid(), mode, inputs, label));
  }

  public Terminal<CompoundArithmetic> output() {
    return single();
  }

  public List<Terminal<CompoundArithmetic>> inputs() {
    return multiple();
  }
}
