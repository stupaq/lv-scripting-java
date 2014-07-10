package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;

public class LeftShiftRegister extends ShiftRegister {
  private final EagerTerminal<LeftShiftRegister> inner;
  private final EagerTerminal<LeftShiftRegister> outer;
  private final RightShiftRegister right;

  protected LeftShiftRegister(Generic owner, UID uid, UID inner, UID outer,
      RightShiftRegister right) {
    super(owner, uid);
    this.inner = new EagerTerminal<>(this, inner);
    this.outer = new EagerTerminal<>(this, outer);
    this.right = right;
  }

  public Terminal<LeftShiftRegister> inner() {
    return inner;
  }

  public Terminal<LeftShiftRegister> outer() {
    return outer;
  }

  public RightShiftRegister right() {
    return right;
  }
}
