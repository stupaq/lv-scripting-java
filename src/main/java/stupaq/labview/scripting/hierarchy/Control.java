package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.ControlCreate;

public class Control extends ConcreteGObjectWithTerminal<Control> {
  public Control(Generic owner, UID uid, Terminal<Control> terminal) {
    super(owner, uid, terminal);
  }

  public Control(Generic owner, int style, String label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .getTool(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), false, style, label, connPaneIndex));
  }
}
