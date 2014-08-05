package stupaq.labview;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import java.util.List;

public interface CallableVI {
  public SafeArray call(List<String> names, Object... values);

  public void run();

  public void run(boolean async);

  public Variant getControlValue(String controlName);

  public void setControlValue(String controlName, Variant controlValue);

  public VIPath getVIPath();
}
