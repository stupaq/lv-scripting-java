package stupaq.labview.scripting.tools.activex;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import stupaq.labview.StdCallVI;
import stupaq.labview.UID;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

abstract class ActiveXScriptingTool implements ScriptingTool {
  protected final StdCallVI vi;

  public ActiveXScriptingTool(ActiveXScriptingTools tools) {
    vi = new StdCallVI(tools, tools.resolveToolPath(getClass().getSimpleName() + ".vi"));
  }

  protected static Variant encapsulateOwner(Optional<UID> owner) {
    SafeArray owner1 = new SafeArray(Variant.VariantVariant, 2);
    owner1.setBoolean(0, owner.isPresent());
    owner1.setVariant(1, owner.or(UID.ZERO).toVariant());
    return new Variant(owner1);
  }
}
