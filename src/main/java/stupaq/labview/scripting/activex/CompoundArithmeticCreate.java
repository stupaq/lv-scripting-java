package stupaq.labview.scripting.activex;

import com.google.common.base.Optional;
import com.google.common.base.Verify;
import com.google.common.collect.Lists;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ArithmeticMode;

class CompoundArithmeticCreate extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.CompoundArithmeticCreate {
  public CompoundArithmeticCreate(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public Entry<UID, List<UID>> apply(VIPath targetVi, Optional<UID> owner, ArithmeticMode mode,
      int inputs, Optional<String> label) throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), mode, inputs, label.or("")).toSafeArray();
    SafeArray terminals = result.getVariant(1).toSafeArray();
    List<UID> terminals1 = Lists.newArrayList();
    for (int term : terminals.toIntArray()) {
      terminals1.add(new UID(term));
    }
    Verify.verify(terminals1.size() == inputs + 1);
    return new SimpleImmutableEntry<>(new UID(result.getVariant(0)), terminals1);
  }
}
