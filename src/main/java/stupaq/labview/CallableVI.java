package stupaq.labview;

import com.jacob.com.Variant;

import java.util.Map;

public interface CallableVI {
  public void call(Map<String, Variant> params);

  public void run();

  public void run(boolean async);

  public Variant getControlValue(String controlName);

  public void setControlValue(String controlName, Variant controlValue);

  public VIPath getVIPath();
}
