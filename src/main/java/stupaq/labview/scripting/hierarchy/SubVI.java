package stupaq.labview.scripting.hierarchy;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.SubVICreate;

public final class SubVI extends ConcreteGObject {
  private final List<Terminal<SubVI>> terminals = Lists.newArrayList();

  private SubVI(Generic owner, Entry<UID, List<UID>> subViAndTerminals) {
    this(owner, subViAndTerminals.getKey());
    for (UID term : subViAndTerminals.getValue()) {
      terminals.add(new EagerTerminal<>(this, term));
    }
  }

  public SubVI(Generic owner, UID uid) {
    super(owner, uid);
  }

  public SubVI(Generic owner, VIPath path, String label) {
    this(owner, owner.scriptingTools()
        .getTool(SubVICreate.class)
        .apply(owner.viPath(), owner.uid(), path, label));
  }

  public List<Terminal<SubVI>> terminals() {
    return terminals;
  }
}
