package stupaq.labview.hierarchy;

import stupaq.labview.VIErrorException;
import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.tools.GObjectDelete;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castUID;

public abstract class GObject extends Generic {
  public static final LVProperty<stupaq.labview.UID> UID = Cast("UID", castUID);

  public void delete() throws VIErrorException {
    scriptingTools().get(GObjectDelete.class).apply(viPath(), uid().get());
  }
}
