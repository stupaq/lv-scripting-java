package stupaq.labview.parsing;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.UID;

public interface HierarchyVisitor {

  void Wire(Optional<UID> ownerUID, UID uid, Optional<String> label);

  void InlineCNode(Optional<UID> ownerUID, UID uid, String expression, Optional<String> label,
      List<UID> terms);

  void FormulaNode(Optional<UID> ownerUID, UID uid, String expression, Optional<String> label,
      List<UID> terms);
}
