package stupaq.labview;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class Application {
  private final ActiveXComponent activex;

  public Application() {
    activex = new ActiveXComponent("LabVIEW.Application");
  }

  Dispatch openVI(VIName viName) {
    Variant viRef =
        activex.invoke("GetVIReference", viName.toVariant(), new Variant(""), new Variant(false),
            new Variant(0));
    return viRef.toDispatch();
  }
}
