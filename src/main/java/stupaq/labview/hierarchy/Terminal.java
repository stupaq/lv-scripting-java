package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.VIErrorException;
import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.tools.GObjectDelete;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castBoolean;
import static stupaq.labview.parsing.LVPropertyCast.castUID;
import static stupaq.labview.parsing.LVPropertyCast.castString;

public abstract class Terminal<T extends GObject> extends GObject {
  public static final String XML_NAME = "Terminal";
  public static final LVProperty<stupaq.labview.UID> Wire = Cast("Wire", castUID);
  public static final LVProperty<Boolean> IsSource = Cast("IsSource", castBoolean);
  public static final LVProperty<String> Name = Cast("Name", castString);

  public Wire connectTo(Terminal sink) {
    return connectTo(sink, Optional.<String>absent());
  }

  public Wire connectTo(Terminal sink, Optional<String> label) {
    return new Wire(rootOwner(), this, sink, label);
  }

  @Override
  public abstract T owner();

  @Override
  public void delete() throws VIErrorException {
    scriptingTools().get(GObjectDelete.class).apply(viPath(), owner().uid().get());
  }
}
