package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.ConnectorPanePattern;
import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlStyle;

public class Indicator extends ConcreteGObjectWithOptionalTerminal<Indicator> {
  public Indicator(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex,
      String description) {
    super(owner, owner.scriptingTools()
        .get(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), true, style, label, connPaneIndex, hasTerminal(owner),
            description));
  }

  public Indicator(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex) {
    this(owner, style, label, connPaneIndex, "");
  }

  public Indicator(Generic owner, ControlStyle style, Optional<String> label) {
    this(owner, style, label, ConnectorPanePattern.DO_NOT_CONNECT);
  }

  protected Indicator(Generic owner, Entry<UID, Optional<UID>> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }

  protected static boolean hasTerminal(Generic owner) {
    return !(owner instanceof Indicator);
  }
}
