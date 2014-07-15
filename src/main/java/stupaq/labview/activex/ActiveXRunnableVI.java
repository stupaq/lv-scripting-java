package stupaq.labview.activex;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import java.util.Map;
import java.util.Map.Entry;

import stupaq.labview.CallableVI;
import stupaq.labview.VIPath;

public abstract class ActiveXRunnableVI implements CallableVI {
  private final VIPath viPath;
  private final ActiveXComponent activeX;

  protected ActiveXRunnableVI(ActiveXApplication application, VIPath viPath) {
    this.viPath = viPath;
    activeX = new ActiveXComponent(application.openVI(viPath));
  }

  @Override
  public void call(Map<String, Variant> params) {
    SafeArray paramNames = new SafeArray(Variant.VariantString, params.size());
    SafeArray paramValues = new SafeArray(Variant.VariantVariant, params.size());
    int index = 0;
    for (Entry<String, Variant> param : params.entrySet()) {
      paramNames.setString(index, param.getKey());
      paramValues.setVariant(index, param.getValue());
      ++index;
    }
    activeX.invoke("Call", new Variant(paramNames), new Variant(paramValues));
  }

  @Override
  public void run() {
    run(false);
  }

  @Override
  public void run(boolean async) {
    activeX.invoke("Run", async);
  }

  @Override
  public Variant getControlValue(String controlName) {
    return activeX.invoke("GetControlValue", new Variant(controlName));
  }

  @Override
  public void setControlValue(String controlName, Variant controlValue) {
    activeX.invoke("SetControlValue", new Variant(controlName), controlValue);
  }

  @Override
  public VIPath getVIPath() {
    return viPath;
  }
}
