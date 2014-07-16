package stupaq.labview.hierarchy;

import java.util.List;

import stupaq.labview.UID;
import stupaq.labview.parsing.LVProperty;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castListUID;
import static stupaq.labview.parsing.LVPropertyCast.castUID;

public abstract class Node extends ConcreteGObject {
  public static final LVProperty<stupaq.labview.UID> Terminal = Cast("Terminal", castUID);
  public static final LVProperty<List<stupaq.labview.UID>> Terminals = Cast("Terms[]", castListUID);

  protected Node(Generic owner, UID uid) {
    super(owner, uid);
  }
}
