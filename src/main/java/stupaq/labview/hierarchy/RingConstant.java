package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.Map;

import stupaq.labview.parsing.LVProperty;
import stupaq.labview.scripting.tools.DataRepresentation;
import stupaq.labview.scripting.tools.RingConstantCreate;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castMapFromStrings;

public class RingConstant extends Constant {
  public static final String XML_NAME = "RingConstant";
  public static final LVProperty<Map<String, Object>> StringsAndValues =
      Cast("Strings And Values[]", castMapFromStrings);

  public RingConstant(Generic owner, Map<String, ?> stringsAndValues,
      DataRepresentation representation, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .get(RingConstantCreate.class)
        .apply(owner.viPath(), owner.uid(), stringsAndValues, representation, label,
            hasTerminal(owner)));
  }
}
