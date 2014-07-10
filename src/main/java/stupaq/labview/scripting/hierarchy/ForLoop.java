package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.LoopCreate;

public class ForLoop extends Loop {
  public ForLoop(Generic owner, Optional<String> label) {
    super(owner, LoopCreate.FOR_LOOP, label);
  }
}
