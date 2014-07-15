package stupaq.labview.hierarchy;

import com.google.common.base.Verify;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map.Entry;

import stupaq.labview.UID;

public abstract class GrowableFunction<T extends GObject> extends Node {
  private final Terminal<T> single;
  private final List<Terminal<T>> multiple = Lists.newArrayList();

  @SuppressWarnings("unchecked")
  protected GrowableFunction(Generic owner, Entry<UID, List<UID>> singleAndMultiple) {
    super(owner, singleAndMultiple.getKey());
    List<UID> terminals = singleAndMultiple.getValue();
    Verify.verify(terminals.size() >= 1, "Missing terminals on one side.");
    single = new EagerTerminal<>((T) this, terminals.get(0));
    for (UID term : FluentIterable.from(terminals).skip(1)) {
      multiple.add(new EagerTerminal<>((T) this, term));
    }
  }

  protected Terminal<T> single() {
    return single;
  }

  protected List<Terminal<T>> multiple() {
    return multiple;
  }
}
