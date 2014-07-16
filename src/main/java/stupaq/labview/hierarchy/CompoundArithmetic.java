package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.scripting.tools.ArithmeticMode;
import stupaq.labview.scripting.tools.CompoundArithmeticCreate;

public final class CompoundArithmetic extends GrowableFunction<CompoundArithmetic> {
  public static final String XML_NAME = "CompoundArithmetic";

  public CompoundArithmetic(Generic owner, ArithmeticMode mode, int inputs,
      Optional<String> label) {
    super(owner, owner.scriptingTools()
        .get(CompoundArithmeticCreate.class)
        .apply(owner.viPath(), owner.uid(), mode, inputs, label));
  }

  public Terminal<CompoundArithmetic> output() {
    return single();
  }

  public List<Terminal<CompoundArithmetic>> inputs() {
    return multiple();
  }
}
