package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.tools.ControlClusterCreate;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castListUID;

public class ControlCluster extends Control {
  public static final String XML_NAME = "Cluster";
  public static final LVProperty<List<stupaq.labview.UID>> Controls =
      Cast("Controls[]", castListUID);

  public ControlCluster(Generic owner, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlClusterCreate.class)
        .apply(owner.viPath(), owner.uid(), false, label, connPaneIndex, hasTerminal(owner)));
  }
}
