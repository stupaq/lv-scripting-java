package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.ScriptingTools;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castOwner;
import static stupaq.labview.parsing.LVPropertyCast.castString;

public abstract class Generic {
  public static final LVProperty<String> ClassName = Cast("ClassName", castString);
  public static final LVProperty<Optional<stupaq.labview.UID>> Owner = Cast("Owner", castOwner);

  public abstract Optional<UID> uid();

  public abstract Generic owner();

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
