package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

public enum ArithmeticMode implements ActiveXType {
  ADD(0),
  MULTIPLY(1),
  AND(2),
  OR(3),
  XOR(4);

  private final int mode;

  ArithmeticMode(int mode) {
    this.mode = mode;
  }

  @Override
  public Variant toVariant() {
    return new Variant(mode);
  }
}
