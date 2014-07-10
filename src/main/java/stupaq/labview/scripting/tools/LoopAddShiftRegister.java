package stupaq.labview.scripting.tools;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.jacob.com.SafeArray;

import java.util.List;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public class LoopAddShiftRegister extends ScriptingTool {
  public LoopAddShiftRegister(ScriptingTools application) {
    super(application);
  }

  public Result apply(VIPath targetVi, UID uid, int stackLevel) throws VIErrorException {
    Preconditions.checkArgument(stackLevel > 0);
    SafeArray result = vi.stdCall(targetVi, uid, stackLevel).toSafeArray();
    Result result1 = new Result();
    result1.right = new UID(result.getInt(0));
    result1.rightOuter = new UID(result.getInt(1));
    result1.rightInner = new UID(result.getInt(2));
    for (int i = 0; i < stackLevel; ++i) {
      result1.left.add(new UID(result.getInt(3 + i)));
    }
    for (int i = 0; i < stackLevel; ++i) {
      result1.leftOuter.add(new UID(result.getInt(3 + stackLevel + i)));
    }
    for (int i = 0; i < stackLevel; ++i) {
      result1.leftInner.add(new UID(result.getInt(3 + 2 * stackLevel + i)));
    }
    return result1;
  }

  public static class Result {
    public UID right, rightInner, rightOuter;
    public List<UID> left = Lists.newArrayList(), leftInner = Lists.newArrayList(), leftOuter =
        Lists.newArrayList();

    private Result() {
    }
  }
}
