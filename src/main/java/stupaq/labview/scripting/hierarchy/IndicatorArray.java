package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlCreate.ControlStyle;

public class IndicatorArray extends Indicator {
  public IndicatorArray(Generic owner, Optional<String> label, int connPaneIndex, int depth,
      IndicatorSupplier element) {
    super(owner, ControlStyle.ARRAY, label, connPaneIndex);
    if (depth == 1) {
      element.apply(this);
    } else {
      new IndicatorArray(this, Optional.<String>absent(), ControlCreate.DO_NOT_CONNECT, depth - 1,
          element);
    }
  }

  public static class IndicatorSupplier implements Function<Indicator, Indicator> {
    private Function<Indicator, Indicator> delegate;

    public IndicatorSupplier(final ControlStyle style) {
      delegate = new Function<Indicator, Indicator>() {
        @Override
        public Indicator apply(Indicator owner) {
          return new Indicator(owner, style, Optional.<String>absent(),
              ControlCreate.DO_NOT_CONNECT);
        }
      };
    }

    @Override
    public Indicator apply(Indicator input) {
      return delegate.apply(input);
    }
  }
}
