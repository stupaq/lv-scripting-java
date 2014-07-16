package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlStyle;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castBoolean;
import static stupaq.labview.parsing.LVPropertyCast.castInteger;

public class Control extends ConcreteGObjectWithOptionalTerminal<Control> {
  public static final String NUMERIC_XML_NAME = "Numeric";
  public static final LVProperty<Boolean> IsIndicator = Cast("Indicator", castBoolean);
  public static final LVProperty<Integer> Style = Cast("Style", castInteger);
  public static final LVProperty<Integer> Representation = Cast("Representation", castInteger);

  public Control(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex) {
    super(owner, owner.scriptingTools()
        .get(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), false, style, label, connPaneIndex,
            hasTerminal(owner)));
  }

  protected Control(Generic owner, Entry<UID, Optional<UID>> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }

  protected static boolean hasTerminal(Generic owner) {
    return !(owner instanceof Control);
  }
}
