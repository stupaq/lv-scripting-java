package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class ControlCreate extends ScriptingTool {
  public static final int VARIANT = 3310;

  public ControlCreate(ScriptingTools application) {
    super(application);
  }

  public Entry<UID, UID> apply(VIPath targetVi, Optional<UID> owner, boolean isIndicator, int style,
      String label) throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), isIndicator, style, label).toSafeArray();
    return new SimpleImmutableEntry<>(new UID(result.getVariant(0)), new UID(result.getVariant(1)));
  }
}
