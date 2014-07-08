package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.Map;

import stupaq.labview.scripting.tools.DataRepresentation;
import stupaq.labview.scripting.tools.RingConstantCreate;

public class RingConstant extends Constant {
  public RingConstant(Generic owner, Map<String, ?> stringsAndValues,
      DataRepresentation representation, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .getTool(RingConstantCreate.class)
        .apply(owner.viPath(), owner.uid(), stringsAndValues, representation, label,
            hasTerminal(owner)));
  }
}
