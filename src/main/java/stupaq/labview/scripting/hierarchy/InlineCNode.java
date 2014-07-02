package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.FormulaCreate;

public final class InlineCNode extends Formula {
  public InlineCNode(Generic owner, UID uid) {
    super(owner, uid);
  }

  public InlineCNode(Generic owner, String content, Optional<String> label) {
    super(owner, FormulaCreate.INLINE_C_NODE, content, label);
  }

  @Override
  protected int formulaType() {
    return FormulaCreate.INLINE_C_NODE;
  }
}
