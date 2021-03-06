package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.ControlClusterCreate;

public class IndicatorCluster extends Indicator {
  public IndicatorCluster(Generic owner, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlClusterCreate.class)
        .apply(owner.viPath(), owner.uid(), true, label, connPaneIndex, hasTerminal(owner)));
  }
}
