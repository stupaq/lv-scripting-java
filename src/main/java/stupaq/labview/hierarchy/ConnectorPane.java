package stupaq.labview.hierarchy;

import java.util.List;

import stupaq.labview.UID;
import stupaq.labview.parsing.LVProperty;

import static stupaq.labview.parsing.LVProperty.Cast;
import static stupaq.labview.parsing.LVPropertyCast.castListUID;

public abstract class ConnectorPane extends Generic {
  public static final String XML_NAME = "ConnectorPane";
  public static final LVProperty<List<UID>> Controls = Cast("Controls[]", castListUID);
}
