package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;

public final class EagerTerminal<T extends GObject> extends Terminal<T> {
  private final UID uid;
  private final T owner;

  public EagerTerminal(T owner, UID uid) {
    this.uid = uid;
    this.owner = owner;
  }

  @Override
  public Optional<UID> uid() {
    return Optional.of(uid);
  }

  @Override
  protected T owner() {
    return owner;
  }
}
