package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import stupaq.labview.scripting.tools.FormulaCreate;

public class FormulaNode extends Formula {
  public FormulaNode(Generic owner, String content, Optional<String> label) {
    super(owner, FormulaCreate.FORMULA_NODE, content, label);
  }

  @Override
  protected int formulaType() {
    return FormulaCreate.FORMULA_NODE;
  }
}
