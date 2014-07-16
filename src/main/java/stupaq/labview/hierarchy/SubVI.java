package stupaq.labview.hierarchy;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.SubVICreate;

public class SubVI extends ConcreteGObject {
  public static final String XML_NAME = "SubVI";
  private final List<Terminal<SubVI>> terminals = Lists.newArrayList();

  protected SubVI(Generic owner, Entry<UID, List<UID>> subViAndTerminals) {
    super(owner, subViAndTerminals.getKey());
    for (UID term : subViAndTerminals.getValue()) {
      terminals.add(new EagerTerminal<>(this, term));
    }
  }

  public SubVI(Generic owner, VIPath path, Optional<String> description) {
    this(owner, owner.scriptingTools()
        .get(SubVICreate.class)
        .apply(owner.viPath(), owner.uid(), path, description));
  }

  public Terminal terminal(int index) {
    return terminals.get(index);
  }
}
