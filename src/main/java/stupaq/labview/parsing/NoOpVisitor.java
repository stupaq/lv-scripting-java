package stupaq.labview.parsing;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ControlStyle;

public abstract class NoOpVisitor<E extends Exception> implements VIElementsVisitor<E> {
  protected NoOpVisitor() {
  }

  @Override
  public void Diagram(Optional<UID> owner, UID uid) throws E {
  }

  @Override
  public void Panel(Optional<UID> owner, UID uid) throws E {
  }

  @Override
  public void Terminal(UID owner, UID uid, UID wire, boolean isSource, String name) throws E {
  }

  @Override
  public void Wire(UID owner, UID uid, Optional<String> label) throws E {
  }

  @Override
  public void InlineCNode(UID owner, UID uid, String expression, Optional<String> label,
      List<UID> terms) throws E {
  }

  @Override
  public void FormulaNode(UID owner, UID uid, String expression, Optional<String> label,
      List<UID> terms) throws E {
  }

  @Override
  public void CompoundArithmetic(UID owner, UID uid, List<UID> terms) throws E {
  }

  @Override
  public void Bundler(UID owner, UID uid, List<UID> terms) throws E {
  }

  @Override
  public void Unbundler(UID owner, UID uid, List<UID> terms) throws E {
  }

  @Override
  public void ControlCluster(UID owner, UID uid, Optional<String> label, UID terminal,
      boolean isIndicator, int controlIndex, List<UID> controls) {
  }

  @Override
  public void ControlArray(UID owner, UID uid, Optional<String> label, UID terminal,
      boolean isIndicator, int controlIndex) {
  }

  @Override
  public void Control(UID owner, UID uid, Optional<String> label, UID terminal, boolean isIndicator,
      ControlStyle style, int controlIndex, String description) throws E {
  }

  @Override
  public void RingConstant(UID owner, UID uid, Optional<String> label, UID terminal,
      Map<String, Object> stringsAndValues) throws E {
  }

  @Override
  public void SubVI(UID owner, UID uid, List<UID> terms, VIPath viPath) throws E {
  }
}
