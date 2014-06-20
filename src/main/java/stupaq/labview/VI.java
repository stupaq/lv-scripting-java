package stupaq.labview;

import com.google.common.base.Function;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public abstract class VI {
  protected final ActiveXComponent activex;

  protected VI(Application application, VIName viName) {
    activex = new ActiveXComponent(application.openVI(viName));
  }

  protected <Result> Result run(Function<ActiveXComponent, Result> runner) throws VIErrorException {
    activex.invoke("Revert");
    activex.invoke("ReinitializeAllToDefault");
    activex.invoke("OpenFrontPanel", new Variant(true), new Variant(3));
    try {
      return runner.apply(activex);
    } finally {
      activex.invoke("CloseFrontPanel");
    }
  }

  // TODO reflect ActiveX API of a VI
}
