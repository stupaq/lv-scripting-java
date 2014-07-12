package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public abstract class Generic {
  public abstract Optional<UID> uid();

  protected abstract Generic owner();

  protected ScriptingTools scriptingTools() {
    return owner().scriptingTools();
  }

  protected VIPath viPath() {
    return owner().viPath();
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }

  @Override
  public final boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public String toString() {
    return Terminal.class.getSimpleName() + "{" + uid().get() + '}';
  }

  protected Generic rootOwner() {
    Generic owner = owner();
    while (owner != owner.owner()) {
      owner = owner.owner();
    }
    return owner;
  }
}
