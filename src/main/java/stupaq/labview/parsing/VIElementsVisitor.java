package stupaq.labview.parsing;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;

import stupaq.labview.UID;
import stupaq.labview.VIPath;

public interface VIElementsVisitor {

  public void Diagram(Optional<UID> owner, UID uid);

  public void Panel(Optional<UID> owner, UID uid);

  public void Terminal(UID owner, UID uid, UID wire, boolean isSource, String name);

  public void Wire(UID ownerUID, UID uid, Optional<String> label);

  public void InlineCNode(UID ownerUID, UID uid, String expression,
      Optional<String> label, List<UID> terms);

  public void FormulaNode(UID ownerUID, UID uid, String expression,
      Optional<String> label, List<UID> terms);

  public void CompoundArithmetic(UID owner, UID uid, List<UID> terms);

  public void Bundler(UID owner, UID uid, List<UID> terms);

  public void Unbundler(UID owner, UID uid, List<UID> terms);

  public void Control(UID owner, UID uid, Optional<String> label, UID terminal, boolean isIndicator,
      int style, Optional<Integer> representation, int controlIndex);

  public void RingConstant(UID owner, UID uid, UID terminal,
      Map<String, Object> stringsAndValues);

  public void SubVI(UID owner, UID uid, List<UID> terms, VIPath viPath);
}
