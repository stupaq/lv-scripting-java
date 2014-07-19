package stupaq.labview.parsing;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ControlStyle;

public interface VIElementsVisitor<E extends Exception> {

  public void Diagram(Optional<UID> owner, UID uid) throws E;

  public void Panel(Optional<UID> owner, UID uid) throws E;

  public void Terminal(UID owner, UID uid, UID wire, boolean isSource, String name) throws E;

  public void Wire(UID owner, UID uid, Optional<String> label) throws E;

  public void InlineCNode(UID owner, UID uid, String expression, Optional<String> label,
      List<UID> terms) throws E;

  public void FormulaNode(UID owner, UID uid, String expression, Optional<String> label,
      List<UID> terms) throws E;

  public void CompoundArithmetic(UID owner, UID uid, List<UID> terms) throws E;

  public void Bundler(UID owner, UID uid, List<UID> terms) throws E;

  public void Unbundler(UID owner, UID uid, List<UID> terms) throws E;

  public void ControlCluster(UID owner, UID uid, Optional<String> label, UID terminal,
      boolean isIndicator, int controlIndex, List<UID> controls);

  public void ControlArray(UID owner, UID uid, Optional<String> label, UID terminal,
      boolean isIndicator, int controlIndex);

  public void Control(UID owner, UID uid, Optional<String> label, UID terminal, boolean isIndicator,
      ControlStyle style, int controlIndex) throws E;

  public void RingConstant(UID owner, UID uid, Optional<String> label, UID terminal,
      Map<String, Object> stringsAndValues) throws E;

  public void SubVI(UID owner, UID uid, List<UID> terms, VIPath viPath) throws E;
}
