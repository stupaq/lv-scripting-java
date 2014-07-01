package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;

public abstract class ConcreteGObject extends GObject {
  private final Optional<UID> uid;
  private final Generic owner;

  protected ConcreteGObject(Generic owner, UID uid) {
    Preconditions.checkNotNull(owner);
    Preconditions.checkNotNull(uid);
    this.owner = owner;
    this.uid = Optional.of(uid);
  }

  @Override
  public final Optional<UID> uid() {
    return uid;
  }

  @Override
  protected final ScriptingTools scriptingTools() {
    return owner().scriptingTools();
  }

  @Override
  protected Generic owner() {
    return owner;
  }

  @Override
  protected final VIPath viPath() {
    return owner().viPath();
  }

  @Override
  public int hashCode() {
    return uid.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this == o ||
        !(o == null || getClass() != o.getClass()) && uid.equals(((ConcreteGObject) o).uid);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "uid=" + uid.get() + '}';
  }
}
