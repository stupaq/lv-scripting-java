package stupaq.labview.activex;

import com.google.common.base.Preconditions;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import java.util.List;

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
  public SafeArray call(List<String> names, Object... values) {
    final int argc = names.size();
    Preconditions.checkArgument(argc >= values.length);
    SafeArray paramNames = new SafeArray(Variant.VariantString, argc);
    SafeArray paramValues = new SafeArray(Variant.VariantVariant, argc);
    for (int i = 0; i < argc; ++i) {
      paramNames.setString(i, names.get(i));
      paramValues.setVariant(i, new Variant(i < values.length ? values[i] : null));
    }
    activeX.invoke("Call", new Variant(paramNames, true), new Variant(paramValues, true));
    return paramValues;
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
