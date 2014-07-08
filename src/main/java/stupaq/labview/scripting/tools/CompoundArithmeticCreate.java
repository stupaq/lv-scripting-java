package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;

import stupaq.activex.ActiveXType;
import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class CompoundArithmeticCreate extends ScriptingTool {
  public CompoundArithmeticCreate(ScriptingTools application) {
    super(application);
  }

  public Entry<UID, List<UID>> apply(VIPath targetVi, Optional<UID> owner, ArithmeticMode mode,
      int inputs, String label) throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), mode, inputs, label).toSafeArray();
    SafeArray terminals = result.getVariant(1).toSafeArray();
    List<UID> terminals1 = Lists.newArrayList();
    for (int term : terminals.toIntArray()) {
      terminals1.add(new UID(term));
    }
    return new SimpleImmutableEntry<>(new UID(result.getVariant(0)), terminals1);
  }

  public static enum ArithmeticMode implements ActiveXType {
    ADD(0),
    MULTIPLY(1),
    AND(2),
    OR(3),
    XOR(4);

    private final int mode;

    private ArithmeticMode(int mode) {
      this.mode = mode;
    }

    @Override
    public Variant toVariant() {
      return new Variant(mode);
    }
  }
}
