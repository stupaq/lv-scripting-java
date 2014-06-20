package stupaq.labview;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

public abstract class VI {
  private final ActiveXComponent activeX;

  protected VI(Application application, VIName viName) {
    activeX = new ActiveXComponent(application.openVI(viName));
  }

  /* FIXME
  private void call(SafeArray argNames, SafeArray argValues) {
    Preconditions.checkArgument(
        argNames.toVariantArray().length == argValues.toVariantArray().length);
    activeX.invoke("Call", new Variant(argNames, true), new Variant(argNames, true));
  }

  public void call(String[] argNames, Variant[] argValues) {
    SafeArray argNames1 = new SafeArray(Variant.VariantString, argNames.length);
    argNames1.fromStringArray(argNames);
    SafeArray argValues1 = new SafeArray(Variant.VariantVariant, argValues.length);
    argValues1.fromVariantArray(argValues);
    call(argNames1, argValues1);
  }
  */

  public void run() {
    run(false);
  }

  public void run(boolean async) {
    activeX.invoke("Run", async);
  }

  public Variant getControlValue(String controlName) {
    return activeX.invoke("GetControlValue", new Variant(controlName));
  }

  public void setControlValue(String controlName, Variant controlValue) {
    activeX.invoke("SetControlValue", new Variant(controlName), controlValue);
  }
}
