package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.WireConnect;

public final class Wire extends ConcreteGObject {
  public Wire(Generic owner, UID uid) {
    super(owner, uid);
  }

  Wire(Generic owner, Terminal source, Terminal sink, Optional<String> label) {
    this(owner, owner.scriptingTools()
        .get(WireConnect.class)
        .apply(source.viPath(), source.uid().get(), sink.uid().get(), label));
  }
}
