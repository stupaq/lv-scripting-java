package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlCreate.ControlStyle;

public class ControlArray extends Control {
  public ControlArray(Generic owner, Optional<String> label, int connPaneIndex, int depth,
      ControlSupplier element) {
    super(owner, ControlStyle.ARRAY, label, connPaneIndex);
    Preconditions.checkArgument(depth > 0);
    if (depth == 1) {
      element.apply(this);
    } else {
      new ControlArray(this, Optional.<String>absent(), ControlCreate.DO_NOT_CONNECT, depth - 1,
          element);
    }
  }

  public static class ControlSupplier implements Function<Control, Control> {
    private Function<Control, Control> delegate;

    public ControlSupplier(final ControlStyle style) {
      delegate = new Function<Control, Control>() {
        @Override
        public Control apply(Control owner) {
          return new Control(owner, style, Optional.<String>absent(), ControlCreate.DO_NOT_CONNECT);
        }
      };
    }

    @Override
    public Control apply(Control input) {
      return delegate.apply(input);
    }
  }
}
