package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.ControlClusterCreate;

public class ControlCluster extends Control {
  public static final String XML_NAME = "Cluster";

  public ControlCluster(Generic owner, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlClusterCreate.class)
        .apply(owner.viPath(), owner.uid(), false, label, connPaneIndex, hasTerminal(owner)));
  }
}
