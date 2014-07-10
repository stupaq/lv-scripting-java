package stupaq.labview.scripting.hierarchy;

import com.google.common.collect.Lists;

import java.util.List;

import stupaq.labview.scripting.tools.LoopAddShiftRegister;
import stupaq.labview.scripting.tools.LoopAddShiftRegister.Result;

public class RightShiftRegister extends ShiftRegister {
  private final Terminal<RightShiftRegister> inner;
  private final Terminal<RightShiftRegister> outer;
  private final List<LeftShiftRegister> lefts = Lists.newArrayList();

  RightShiftRegister(Generic owner, int stackLevel) {
    this(owner, owner.scriptingTools()
        .getTool(LoopAddShiftRegister.class)
        .apply(owner.viPath(), owner.uid().get(), stackLevel));
  }

  private RightShiftRegister(Generic owner, Result result) {
    super(owner, result.right);
    this.inner = new EagerTerminal<>(this, result.rightInner);
    this.outer = new EagerTerminal<>(this, result.rightOuter);
    for (int i = 0; i < result.left.size(); ++i) {
      lefts.add(new LeftShiftRegister(owner, result.left.get(i), result.leftInner.get(i),
          result.leftOuter.get(i), this));
    }
  }

  public Terminal<RightShiftRegister> inner() {
    return inner;
  }

  public Terminal<RightShiftRegister> outer() {
    return outer;
  }

  public LeftShiftRegister left(int index) {
    return lefts.get(index);
  }
}
