package stupaq.labview.hierarchy;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import stupaq.labview.scripting.tools.ControlArrayCreate;
import stupaq.labview.scripting.tools.ControlStyle;

public class IndicatorArray extends Indicator {
  public IndicatorArray(Generic owner, int dimensions, ControlStyle style, Optional<String> label,
      int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlArrayCreate.class)
        .apply(owner.viPath(), owner.uid(), true, dimensions, label, connPaneIndex,
            hasTerminal(owner)));
    Preconditions.checkArgument(dimensions > 0);
    new Indicator(this, style, Optional.<String>absent());
  }
}
