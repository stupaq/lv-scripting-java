package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class ConnectWire extends ScriptingTool {
  public ConnectWire(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, UID source, UID destination, String label)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi.toVariant(), source.toVariant(), destination.toVariant(),
        new Variant(label)));
  }
}
