package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.Map.Entry;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.LoopCreate;

public abstract class Loop extends Structure {
  private final Diagram diagram;

  protected Loop(Generic owner, int loopType, Optional<String> label) {
    this(owner, owner.scriptingTools()
        .getTool(LoopCreate.class)
        .apply(owner.viPath(), owner.uid(), loopType, label));
  }

  protected Loop(Generic owner, Entry<UID, UID> loopAndDiagram) {
    super(owner, loopAndDiagram.getKey());
    diagram = new Diagram(this, loopAndDiagram.getValue());
  }

  public Diagram diagram() {
    return diagram;
  }

  public RightShiftRegister addShiftRegister(int stackLevel) {
    return new RightShiftRegister(this, stackLevel);
  }

  public RightShiftRegister addShiftRegister() {
    return addShiftRegister(1);
  }
}
