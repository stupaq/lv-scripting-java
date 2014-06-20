package stupaq.labview.scripting;

import stupaq.labview.Application;
import stupaq.labview.StdCallVI;
import stupaq.labview.VIName;

public abstract class ToolVI extends StdCallVI {
  protected ToolVI(Application application, VIName viName) {
    super(application, viName);
  }
}
