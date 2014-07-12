package stupaq.labview.scripting.tools.activex;

import com.google.common.base.Preconditions;

import com.jacob.com.SafeArray;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;

class LoopAddShiftRegister extends ActiveXScriptingTool
    implements stupaq.labview.scripting.tools.LoopAddShiftRegister {
  public LoopAddShiftRegister(ActiveXScriptingTools application) {
    super(application);
  }

  @Override
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
}
