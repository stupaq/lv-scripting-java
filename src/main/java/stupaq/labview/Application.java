package stupaq.labview;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Application {
  private final ActiveXComponent activex;

  public Application() {
    activex = new ActiveXComponent("LabVIEW.Application");
  }

  public Dispatch openVI(VIPath viPath) {
    Variant viRef =
        activex.invoke("GetVIReference", viPath.toVariant(), new Variant(""), new Variant(false),
            new Variant(0));
    return viRef.toDispatch();
  }
}
