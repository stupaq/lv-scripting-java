package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import stupaq.labview.scripting.tools.ControlArrayCreate;
import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlCreate.ControlStyle;

public class ControlArray extends Control {
  public ControlArray(Generic owner, int dimensions, ControlStyle style, Optional<String> label,
      int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .getTool(ControlArrayCreate.class)
        .apply(owner.viPath(), owner.uid(), false, dimensions, label, connPaneIndex,
            hasTerminal(owner)));
    Preconditions.checkArgument(dimensions > 0);
    new Control(this, style, Optional.<String>absent(), ControlCreate.DO_NOT_CONNECT);
  }
}
