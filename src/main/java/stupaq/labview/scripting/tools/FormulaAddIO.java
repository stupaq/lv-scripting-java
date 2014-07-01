package stupaq.labview.scripting.tools;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class FormulaAddIO extends ScriptingTool {
  public FormulaAddIO(ScriptingTools application) {
    super(application);
  }

  public Entry<UID, UID> apply(VIPath targetVi, int formulaType, UID uid, boolean isInput,
      String name) throws VIErrorException {
    SafeArray result = vi.stdCall(targetVi, formulaType, uid, isInput, name).toSafeArray();
    return new SimpleImmutableEntry<>(new UID(result.getVariant(0)), new UID(result.getVariant(1)));
  }
}
