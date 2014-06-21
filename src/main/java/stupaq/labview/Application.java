package stupaq.labview;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class Application {
  private final ActiveXComponent activeX;

  public Application() {
    activeX = new ActiveXComponent("LabVIEW.Application");
  }

  public Dispatch openVI(VIPath viPath) {
    return activeX.invoke("GetVIReference", viPath.toVariant()).toDispatch();
  }
}
