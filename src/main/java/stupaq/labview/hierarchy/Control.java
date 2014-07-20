package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import com.ni.labview.ELType;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.parsing.LVProperty;
import stupaq.labview.parsing.LVPropertyCast;
import stupaq.labview.scripting.tools.ConnectorPanePattern;
import stupaq.labview.scripting.tools.ControlCreate;
import stupaq.labview.scripting.tools.ControlStyle;
import stupaq.labview.scripting.tools.DataRepresentation;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castBoolean;
import static stupaq.labview.parsing.LVPropertyCast.castInteger;
import static stupaq.labview.parsing.LVPropertyCast.castString;

public class Control extends ConcreteGObjectWithOptionalTerminal<Control> {
  public static final String NUMERIC_XML_NAME = "Numeric";
  public static final String STRING_XML_NAME = "String";
  public static final String VARIANT_XML_NAME = "Variant";
  public static final LVProperty<Boolean> IsIndicator = Cast("Indicator", castBoolean);
  public static final LVProperty<Integer> Style = Cast("Style", castInteger);
  public static final LVProperty<DataRepresentation> Representation =
      Cast("Rep", new LVPropertyCast<DataRepresentation>() {
        @Override
        public DataRepresentation get(Object value) {
          if (value == null) {
            return DataRepresentation.UNKNOWN;
          } else {
            String rep = ((ELType) value).getVal();
            return DataRepresentation.resolve(rep);
          }
        }
      });
  public static final LVProperty<Integer> ControlIndex = Cast("ControlIndex", castInteger);
  public static final LVProperty<String> Description = Cast("Desc", castString);

  public Control(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex,
      String description) {
    super(owner, owner.scriptingTools()
        .get(ControlCreate.class)
        .apply(owner.viPath(), owner.uid(), false, style, label, connPaneIndex, hasTerminal(owner),
            description));
  }

  public Control(Generic owner, ControlStyle style, Optional<String> label, int connPaneIndex) {
    this(owner, style, label, connPaneIndex, "");
  }

  public Control(Generic owner, ControlStyle style, Optional<String> label) {
    this(owner, style, label, ConnectorPanePattern.DO_NOT_CONNECT);
  }

  protected Control(Generic owner, Entry<UID, Optional<UID>> objectAndTerminal) {
    super(owner, objectAndTerminal);
  }

  protected static boolean hasTerminal(Generic owner) {
    return !(owner instanceof Control);
  }
}
