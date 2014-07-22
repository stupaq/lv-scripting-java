package stupaq.labview.hierarchy;

import java.util.List;

import stupaq.labview.parsing.LVProperty;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castListUID;
import static stupaq.labview.parsing.LVPropertyCast.castUID;

public abstract class Tunnel extends ConcreteGObject {
  public static final String XML_NAME = "Tunnel";
  public static final LVProperty<List<stupaq.labview.UID>> InsideTerminals =
      Cast("InsideTerms[]", castListUID);
  public static final LVProperty<stupaq.labview.UID> OutsideTerminal = Cast("Outer Term", castUID);

  protected Tunnel(Generic owner, stupaq.labview.UID uid) {
    super(owner, uid);
  }
}
