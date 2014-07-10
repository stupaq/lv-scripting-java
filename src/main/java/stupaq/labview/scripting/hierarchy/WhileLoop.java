package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.LoopCreate;

public class WhileLoop extends Loop {
  public WhileLoop(Generic owner, Optional<String> label) {
    super(owner, LoopCreate.WHILE_LOOP, label);
  }
}
