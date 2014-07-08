package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Verify;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.CompoundArithmeticCreate;
import stupaq.labview.scripting.tools.CompoundArithmeticCreate.ArithmeticMode;

public final class CompoundArithmetic extends Node {
  private final Terminal<CompoundArithmetic> output;
  private final List<Terminal<CompoundArithmetic>> inputs = Lists.newArrayList();

  private CompoundArithmetic(Generic owner, Entry<UID, List<UID>> nodeAndTerminals) {
    super(owner, nodeAndTerminals.getKey());
    List<UID> terminals = nodeAndTerminals.getValue();
    Verify.verify(terminals.size() >= 1);
    output = new EagerTerminal<>(this, terminals.get(0));
    for (UID term : FluentIterable.from(terminals).skip(1)) {
      inputs.add(new EagerTerminal<>(this, term));
    }
  }

  public CompoundArithmetic(Generic owner, ArithmeticMode mode, int inputs, String label) {
    this(owner, owner.scriptingTools()
        .getTool(CompoundArithmeticCreate.class)
        .apply(owner.viPath(), owner.uid(), mode, inputs, label));
  }

  public Terminal<CompoundArithmetic> output() {
    return output;
  }

  public List<Terminal<CompoundArithmetic>> inputs() {
    return inputs;
  }
}
