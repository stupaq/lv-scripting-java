package stupaq.labview.scripting.activex;

import com.google.common.base.Optional;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import stupaq.labview.UID;
import stupaq.labview.activex.StdCallRunnableVI;
import stupaq.labview.scripting.ScriptingTools.ScriptingTool;

abstract class ActiveXScriptingTool implements ScriptingTool {
  protected final StdCallRunnableVI vi;

  public ActiveXScriptingTool(ActiveXScriptingTools tools) {
    vi = new StdCallRunnableVI(tools, tools.resolveToolPath(getClass().getSimpleName() + ".vi"));
  }

  protected static Variant encapsulateOwner(Optional<UID> owner) {
    SafeArray owner1 = new SafeArray(Variant.VariantVariant, 2);
    owner1.setBoolean(0, owner.isPresent());
    owner1.setVariant(1, owner.or(UID.ZERO).toVariant());
    return new Variant(owner1);
  }
}
