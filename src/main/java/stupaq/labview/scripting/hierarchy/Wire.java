package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.WireConnect;

public final class Wire extends ConcreteGObject {
  public Wire(GObject owner, UID uid) {
    super(owner, uid);
  }

  public Wire(Terminal source, Terminal sink, String label) {
    this(source, source.scriptingTools()
        .getTool(WireConnect.class)
        .apply(source.viPath(), source.uid().get(), sink.uid().get(), label));
  }

  public Wire(Terminal source, Terminal sink) {
    this(source, sink, "");
  }
}
