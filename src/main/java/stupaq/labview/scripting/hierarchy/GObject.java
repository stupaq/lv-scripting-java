package stupaq.labview.scripting.hierarchy;

import stupaq.labview.VIErrorException;
import stupaq.labview.scripting.tools.GObjectDelete;

public abstract class GObject extends Generic {
  public void delete() throws VIErrorException {
    scriptingTools().getTool(GObjectDelete.class).apply(viPath(), uid().get());
  }
}
