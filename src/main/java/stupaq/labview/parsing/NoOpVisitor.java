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
  public void Diagram(Optional<UID> ownerUID, UID uid) throws E {
  }

  @Override
  public void Panel(Optional<UID> ownerUID, UID uid) throws E {
  }

  @Override
  public void Terminal(UID ownerUID, UID uid, UID wireUID, boolean isSource, String name) throws E {
  }

  @Override
  public void Wire(UID ownerUID, UID uid, Optional<String> label) throws E {
  }

  @Override
  public void InlineCNode(UID owner, UID uid, String expression, Optional<String> label,
      List<UID> termUIDs) throws E {
  }

  @Override
  public void FormulaNode(UID ownerUID, UID uid, String expression, Optional<String> label,
      List<UID> termUIDs) throws E {
  }

  @Override
  public void CompoundArithmetic(UID ownerUID, UID uid, UID outputUID, List<UID> inputUIDs)
      throws E {
  }

  @Override
  public void Bundler(UID ownerUID, UID uid, UID outputUIDs, List<UID> inputUIDs) throws E {
  }

  @Override
  public void Unbundler(UID ownerUID, UID uid, UID inputUID, List<UID> outputUIDs) throws E {
  }

  @Override
  public void ConnectorPane(List<UID> controls) {
  }

  @Override
  public void ControlCluster(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator, List<UID> controlUIDs) {
  }

  @Override
  public void ControlArray(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator) {
  }

  @Override
  public void Control(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator, ControlStyle style, String description) throws E {
  }

  @Override
  public void RingConstant(UID owner, UID uid, Optional<String> label, UID terminalUID,
      Map<String, Object> stringsAndValues) throws E {
  }

  @Override
  public void SubVI(UID owner, UID uid, List<UID> termUIDs, VIPath viPath, String description) throws E {
  }
}
