package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlStyle;

public class Control extends ConcreteGObjectWithOptionalTerminal<Control> {
  public Control(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), false, style, label, connPaneIndex,
            hasTerminal(owner)));
  }

  protected Control(Generic owner, Entry<UID, Optional<UID>> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }

  protected static boolean hasTerminal(Generic owner) {
    return !(owner instanceof Control);
  }
}