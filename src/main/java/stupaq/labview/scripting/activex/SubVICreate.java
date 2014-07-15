package stupaq.labview.scripting.activex;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class SubVICreate extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.SubVICreate {
  public SubVICreate(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
  public Entry<UID, List<UID>> apply(VIPath targetVi, Optional<UID> owner, VIPath path,
      Optional<String> description) throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), path, description.or("")).toSafeArray();
    SafeArray terminals = result.getVariant(1).toSafeArray();
    List<UID> terminals1 = Lists.newArrayList();
    for (int term : terminals.toIntArray()) {
      terminals1.add(new UID(term));
    }
    return new SimpleImmutableEntry<>(new UID(result.getVariant(0)), terminals1);
  }
}
