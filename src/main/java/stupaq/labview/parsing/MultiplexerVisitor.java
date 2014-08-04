package stupaq.labview.parsing;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import stupaq.labview.UID;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.ControlStyle;

import static java.util.Collections.unmodifiableList;

public final class MultiplexerVisitor<E extends Exception> implements VIElementsVisitor<E> {
  private final Iterable<String> negotiatedOrder;
  private final List<VIElementsVisitor<? extends E>> visitors = Lists.newArrayList();

  public MultiplexerVisitor(Iterable<String> negotiatedOrder) {
    this.negotiatedOrder = negotiatedOrder;
  }

  public MultiplexerVisitor(String... negotiatedOrder) {
    this(Arrays.asList(negotiatedOrder));
  }

  public <T extends E> void addVisitor(VIElementsVisitor<T> visitor) {
    visitors.add(visitor);
  }

  @Override
  public Iterable<String> parsersOrder() {
    return negotiatedOrder;
  }

  @Override
  public void Diagram(Optional<UID> ownerUID, UID uid) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Diagram(ownerUID, uid);
    }
  }

  @Override
  public void Panel(Optional<UID> ownerUID, UID uid) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Panel(ownerUID, uid);
    }
  }

  @Override
  public void Terminal(UID ownerUID, UID uid, UID wireUID, boolean isSource, String name) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Terminal(ownerUID, uid, wireUID, isSource, name);
    }
  }

  @Override
  public void Tunnel(UID ownerUID, UID uid, List<UID> insideTermUIDs, UID outsideTermUID) {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Tunnel(ownerUID, uid, unmodifiableList(insideTermUIDs), outsideTermUID);
    }
  }

  @Override
  public void Wire(UID ownerUID, UID uid, Optional<String> label) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Wire(ownerUID, uid, label);
    }
  }

  @Override
  public void WhileLoop(UID owner, UID uid) {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.WhileLoop(owner, uid);
    }
  }

  @Override
  public void ForLoop(UID owner, UID uid) {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.ForLoop(owner, uid);
    }
  }

  @Override
  public void InlineCNode(UID owner, UID uid, String expression, Optional<String> label,
      List<UID> termUIDs) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.InlineCNode(owner, uid, expression, label, unmodifiableList(termUIDs));
    }
  }

  @Override
  public void FormulaNode(UID ownerUID, UID uid, String expression, Optional<String> label,
      List<UID> termUIDs) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.FormulaNode(ownerUID, uid, expression, label, unmodifiableList(termUIDs));
    }
  }

  @Override
  public void CompoundArithmetic(UID ownerUID, UID uid, UID outputUID, List<UID> inputUIDs)
      throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.CompoundArithmetic(ownerUID, uid, outputUID, unmodifiableList(inputUIDs));
    }
  }

  @Override
  public void Bundler(UID ownerUID, UID uid, UID outputUIDs, List<UID> inputUIDs) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Bundler(ownerUID, uid, outputUIDs, unmodifiableList(inputUIDs));
    }
  }

  @Override
  public void Unbundler(UID ownerUID, UID uid, UID inputUID, List<UID> outputUIDs) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Unbundler(ownerUID, uid, inputUID, unmodifiableList(outputUIDs));
    }
  }

  @Override
  public void ConnectorPane(List<UID> controls) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.ConnectorPane(unmodifiableList(controls));
    }
  }

  @Override
  public void ControlCluster(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator, List<UID> controlUIDs) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.ControlCluster(ownerUID, uid, label, terminalUID, isIndicator,
          unmodifiableList(controlUIDs));
    }
  }

  @Override
  public void ControlArray(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.ControlArray(ownerUID, uid, label, terminalUID, isIndicator);
    }
  }

  @Override
  public void Control(UID ownerUID, UID uid, Optional<String> label, UID terminalUID,
      boolean isIndicator, ControlStyle style, String description) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.Control(ownerUID, uid, label, terminalUID, isIndicator, style, description);
    }
  }

  @Override
  public void RingConstant(UID owner, UID uid, Optional<String> label, UID terminalUID,
      Map<String, Object> stringsAndValues) throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.RingConstant(owner, uid, label, terminalUID, stringsAndValues);
    }
  }

  @Override
  public void SubVI(UID owner, UID uid, List<UID> termUIDs, VIPath viPath, String description)
      throws E {
    for (VIElementsVisitor<? extends E> visitor : visitors) {
      visitor.SubVI(owner, uid, unmodifiableList(termUIDs), viPath, description);
    }
  }
}
