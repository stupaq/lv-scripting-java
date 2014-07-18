package stupaq.labview.parsing;

public abstract class LVProperty<T> {
  protected LVProperty() {
  }

  public abstract T get(ElementProperties properties);

  public static <T> LVProperty<T> Cast(String name, LVPropertyCast<T> cast) {
    return new LVNamedProperty<>(name, cast);
  }

  private static final class LVNamedProperty<P> extends LVProperty<P> {
    private final String name;
    private final LVPropertyCast<P> cast;

    public LVNamedProperty(String name, LVPropertyCast<P> cast) {
      this.name = name;
      this.cast = cast;
    }

    @Override
    public P get(ElementProperties properties) {
      return cast.get(properties.get(name));
    }
  }
}

