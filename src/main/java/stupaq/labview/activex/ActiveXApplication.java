package stupaq.labview.activex;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

import stupaq.labview.VIPath;

public class ActiveXApplication {
  private final ActiveXComponent activeX;

  public ActiveXApplication() {
    activeX = new ActiveXComponent("LabVIEW.Application");
  }

  public Dispatch openVI(VIPath viPath) {
    return activeX.invoke("GetVIReference", viPath.toVariant()).toDispatch();
  }
}
