package stupaq.labview.parsing;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ControlStyle;

public interface VIElementsVisitor<E extends Exception> {

  public Iterable<String> parsersOrder();

  public void Diagram(Optional<UID> ownerUID, UID uid) throws E;

  public void Panel(Optional<UID> ownerUID, UID uid) throws E;

  public void Terminal(UID ownerUID, UID uid, UID wire, boolean isSource, String name) throws E;

  public void Tunnel(UID ownerUID, UID uid, List<UID> insideTermUIDs, UID outsideTermUID);

  public void Wire(UID ownerUID, UID uid, Optional<String> label) throws E;

  public void WhileLoop(UID owner, UID uid);

  public void ForLoop(UID owner, UID uid);

  public void InlineCNode(UID ownerUID, UID uid, String expression, Optional<String> label,
      List<UID> terms) throws E;

  public void FormulaNode(UID ownerUID, UID uid, String expression, Optional<String> label,
      List<UID> terms) throws E;

  public void CompoundArithmetic(UID ownerUID, UID uid, UID single, List<UID> multiple) throws E;

  public void Bundler(UID ownerUID, UID uid, UID single, List<UID> multiple) throws E;

  public void Unbundler(UID ownerUID, UID uid, UID single, List<UID> multiple) throws E;

  public void ConnectorPane(List<UID> controls) throws E;

  public void ControlCluster(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator, List<UID> controls) throws E;

  public void ControlArray(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator) throws E;

  public void Control(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator, ControlStyle style, String description) throws E;

  public void RingConstant(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      Map<String, Object> stringsAndValues) throws E;

  public void SubVI(UID ownerUID, UID uid, List<UID> terms, VIPath viPath, String description) throws E;
}
