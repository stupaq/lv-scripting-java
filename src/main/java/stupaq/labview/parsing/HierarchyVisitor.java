package stupaq.labview.parsing;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;

import stupaq.labview.UID;

public interface HierarchyVisitor {

  public void Wire(Optional<UID> ownerUID, UID uid, Optional<String> label);

  public void Terminal(Optional<UID> owner, UID uid, UID wire, boolean isSource, String name);

  public void InlineCNode(Optional<UID> ownerUID, UID uid, String expression,
      Optional<String> label, List<UID> terms);

  public void FormulaNode(Optional<UID> ownerUID, UID uid, String expression,
      Optional<String> label, List<UID> terms);

  public void CompoundArithmetic(Optional<UID> owner, UID uid, UID single, List<UID> terms);

  public void Bundler(Optional<UID> owner, UID uid, UID single, List<UID> terms);

  public void Unbundler(Optional<UID> owner, UID uid, UID single, List<UID> terms);

  public void RingConstant(Optional<UID> owner, UID uid, UID terminal,
      Map<String, Object> stringsAndValues);
}
