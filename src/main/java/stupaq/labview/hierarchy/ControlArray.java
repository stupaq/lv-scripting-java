package stupaq.labview.hierarchy;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import stupaq.labview.scripting.tools.ConnectorPanePattern;
import stupaq.labview.scripting.tools.ControlArrayCreate;
import stupaq.labview.scripting.tools.ControlStyle;

public class ControlArray extends Control {
  public ControlArray(Generic owner, int dimensions, ControlStyle style, Optional<String> label,
      int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlArrayCreate.class)
        .apply(owner.viPath(), owner.uid(), false, dimensions, label, connPaneIndex,
            hasTerminal(owner)));
    Preconditions.checkArgument(dimensions > 0);
    new Control(this, style, Optional.<String>absent(), ConnectorPanePattern.DO_NOT_CONNECT);
  }
}
