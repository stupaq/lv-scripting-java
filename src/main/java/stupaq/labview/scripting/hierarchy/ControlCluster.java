package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.ControlClusterCreate;

public class ControlCluster extends Control {
  public ControlCluster(Generic owner, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .getTool(ControlClusterCreate.class)
        .apply(owner.viPath(), owner.uid(), false, label, connPaneIndex, hasTerminal(owner)));
  }
}
