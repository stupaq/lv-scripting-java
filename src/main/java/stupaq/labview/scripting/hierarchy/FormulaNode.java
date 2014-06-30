package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;
import stupaq.labview.scripting.tools.FormulaCreate;

public class FormulaNode extends Formula {
  public FormulaNode(Generic owner, UID uid) {
    super(owner, uid);
  }

  public FormulaNode(Generic owner, String content, String label) {
    super(owner, FormulaCreate.FORMULA_NODE, content, label);
  }

  @Override
  protected int formulaType() {
    return FormulaCreate.FORMULA_NODE;
  }
}
