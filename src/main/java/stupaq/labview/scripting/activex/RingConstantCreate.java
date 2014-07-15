package stupaq.labview.scripting.activex;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.DataRepresentation;

class RingConstantCreate extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.RingConstantCreate {
  public RingConstantCreate(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public Entry<UID, Optional<UID>> apply(VIPath targetVi, Optional<UID> owner,
      Map<String, ?> stringsAndValues, DataRepresentation representation, Optional<String> label,
      boolean hasTerminal) throws VIErrorException {
    SafeArray strings = new SafeArray(Variant.VariantVariant, stringsAndValues.size());
    SafeArray values = new SafeArray(Variant.VariantVariant, stringsAndValues.size());
    int index = 0;
    for (Entry<String, ?> entry : stringsAndValues.entrySet()) {
      strings.setVariant(index, new Variant(entry.getKey()));
      values.setVariant(index, new Variant(entry.getValue()));
      ++index;
    }
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), strings, values, representation, label.or(""),
            hasTerminal).toSafeArray();
    UID controlUID = new UID(result.getVariant(0));
    Optional<UID> terminalUID =
        hasTerminal ? Optional.of(new UID(result.getVariant(1))) : Optional.<UID>absent();
    return new SimpleImmutableEntry<>(controlUID, terminalUID);
  }
}
