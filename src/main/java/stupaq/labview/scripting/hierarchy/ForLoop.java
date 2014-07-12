package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

public class ForLoop extends Loop {
  public ForLoop(Generic owner, Optional<String> label) {
    super(owner, stupaq.labview.scripting.tools.LoopCreate.FOR_LOOP, label);
  }
}
