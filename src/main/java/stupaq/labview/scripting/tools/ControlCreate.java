package stupaq.labview.scripting.tools;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import stupaq.activex.ActiveXType;
import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class ControlCreate extends ScriptingTool {
  public static final int DO_NOT_CONNECT = -1;

  public ControlCreate(ScriptingTools application) {
    super(application);
  }

  public Entry<UID, Optional<UID>> apply(VIPath targetVi, Optional<UID> owner, boolean isIndicator,
      ControlStyle style, Optional<String> label, int connPaneIndex, boolean hasTerminal)
      throws VIErrorException {
    SafeArray result =
        vi.stdCall(targetVi, encapsulateOwner(owner), isIndicator, style, label.or(""),
            connPaneIndex, hasTerminal).toSafeArray();
    UID controlUID = new UID(result.getVariant(0));
    Optional<UID> terminalUID =
        hasTerminal ? Optional.of(new UID(result.getVariant(1))) : Optional.<UID>absent();
    return new SimpleImmutableEntry<>(controlUID, terminalUID);
  }

  public static enum ControlStyle implements ActiveXType {
    VARIANT(3310),
    BOOLEAN(21002),
    NUMERIC(21003),
    STRING(21071),
    ARRAY(21608);

    private final int style;

    private ControlStyle(int style) {
      this.style = style;
    }

    @Override
    public Variant toVariant() {
      return new Variant(style);
    }
  }
}
