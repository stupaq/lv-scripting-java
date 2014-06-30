package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class FormulaNodeCreate extends ScriptingTool {
  public FormulaNodeCreate(ScriptingTools application) {
    super(application);
  }

  public UID apply(VIPath targetVi, String content, String label, boolean isInlineCNode)
      throws VIErrorException {
    return new UID(vi.stdCall(targetVi.toVariant(), new Variant(content), new Variant(label),
        new Variant(isInlineCNode)));
  }
}
