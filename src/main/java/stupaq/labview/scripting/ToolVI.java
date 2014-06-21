package stupaq.labview.scripting;

import stupaq.labview.Application;
import stupaq.labview.StdCallVI;
import stupaq.labview.VIPath;

public abstract class ToolVI extends StdCallVI {
  protected ToolVI(Application application, VIPath viPath) {
    super(application, viPath);
  }
}
