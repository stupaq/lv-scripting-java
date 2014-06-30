package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class FormulaNodeAddIO extends ScriptingTool {
  public FormulaNodeAddIO(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, UID uid, boolean isInput, String name, boolean isInlineCNode)
      throws VIErrorException {
    return new UID(
        vi.stdCall(targetVi.toVariant(), uid.toVariant(), new Variant(isInput), new Variant(name),
            new Variant(isInlineCNode)));
  }
}
