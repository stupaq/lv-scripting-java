package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlCreate.ControlStyle;

public class Indicator extends ConcreteGObjectWithOptionalTerminal<Indicator> {
  public Indicator(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .getTool(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), true, style, label, connPaneIndex, hasTerminal(owner)));
  }

  private static boolean hasTerminal(Generic owner) {
    return !(owner instanceof Indicator || owner instanceof Control);
  }
}
