package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

public class WhileLoop extends Loop {
  public static final String XML_NAME = "WhileLoop";

  public WhileLoop(Generic owner, Optional<String> label) {
    super(owner, stupaq.labview.scripting.tools.LoopCreate.WHILE_LOOP, label);
  }
}
