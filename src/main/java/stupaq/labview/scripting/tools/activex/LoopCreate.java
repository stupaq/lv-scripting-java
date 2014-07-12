package stupaq.labview.scripting.tools.activex;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class LoopCreate extends ActiveXScriptingTool implements stupaq.labview.scripting.tools.LoopCreate {
  public LoopCreate(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public Entry<UID, UID> apply(VIPath targetVi, Optional<UID> owner, int loopType,
      Optional<String> label) throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), loopType, label.or("")).toSafeArray();
    UID controlUID = new UID(result.getVariant(0));
    UID diagramUID = new UID(result.getVariant(1));
    return new SimpleImmutableEntry<>(controlUID, diagramUID);
  }
}
