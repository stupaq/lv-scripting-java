package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public abstract class Generic {
  public abstract Optional<UID> uid();

  protected abstract ScriptingTools scriptingTools();

  protected abstract Generic owner();

  protected abstract VIPath viPath();
}
