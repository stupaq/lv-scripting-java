package stupaq.labview.hierarchy;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import stupaq.labview.UID;

abstract class ConcreteGObject extends GObject {
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
  public Generic owner() {
    return owner;
  }
}
