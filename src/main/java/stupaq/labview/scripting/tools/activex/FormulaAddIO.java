package stupaq.labview.scripting.tools.activex;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class FormulaAddIO extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.FormulaAddIO {
  public FormulaAddIO(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public Entry<UID, UID> apply(VIPath targetVi, int formulaType, UID uid, boolean isInput,
      Optional<String> name) throws VIErrorException {
    SafeArray result = vi.stdCall(targetVi, formulaType, uid, isInput, name.or("")).toSafeArray();
    return new SimpleImmutableEntry<>(new UID(result.getVariant(0)), new UID(result.getVariant(1)));
  }
}
