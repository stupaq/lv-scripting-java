package stupaq.labview.scripting.tools;

import com.google.common.collect.Lists;

import java.util.List;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

public interface LoopAddShiftRegister extends ScriptingTool {
  Result apply(VIPath targetVi, UID uid, int stackLevel) throws VIErrorException;

  public static class Result {
    public UID right, rightInner, rightOuter;
    public List<UID> left = Lists.newArrayList(), leftInner = Lists.newArrayList(), leftOuter =
        Lists.newArrayList();
  }
}
