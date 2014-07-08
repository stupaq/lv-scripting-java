package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class ControlArrayCreate extends ScriptingTool {
  public static final int DO_NOT_CONNECT = -1;

  public ControlArrayCreate(ScriptingTools application) {
    super(application);
  }

  public Entry<UID, Optional<UID>> apply(VIPath targetVi, Optional<UID> owner, boolean isIndicator,
      int dimensions, Optional<String> label, int connPaneIndex, boolean hasTerminal)
      throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), isIndicator, dimensions, label.or(""),
            connPaneIndex, hasTerminal).toSafeArray();
    UID controlUID = new UID(result.getVariant(0));
    Optional<UID> terminalUID =
        hasTerminal ? Optional.of(new UID(result.getVariant(1))) : Optional.<UID>absent();
    return new SimpleImmutableEntry<>(controlUID, terminalUID);
  }
}