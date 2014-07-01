package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.ControlCreate;

public class Indicator extends ConcreteGObjectWithTerminal<Indicator> {
  public Indicator(Generic owner, UID uid, Terminal<Indicator> terminal) {
    super(owner, uid, terminal);
  }

  public Indicator(Generic owner, int style, String label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .getTool(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), true, style, label, connPaneIndex));
  }
}
