package stupaq.labview.parsing;

public final class LVProperty<T> {
  private final String name;
  private final LVPropertyCast<T> cast;

  private LVProperty(String name, LVPropertyCast<T> cast) {
    this.name = name;
    this.cast = cast;
  }

  public static <T> LVProperty<T> Cast(String name, LVPropertyCast<T> cast) {
    return new LVProperty<>(name, cast);
  }

  public T get(ElementProperties properties) {
    return cast.get(properties.get(name));
  }
}

