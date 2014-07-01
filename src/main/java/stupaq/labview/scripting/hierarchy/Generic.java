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
  public int hashCode() {
    return uid().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this == o ||
        !(o == null || getClass() != o.getClass()) && uid().equals(((ConcreteGObject) o).uid());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "uid=" + uid().get() + '}';
  }
}
