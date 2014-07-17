package stupaq.labview.hierarchy;

import stupaq.labview.UID;

public class Panel extends ConcreteGObject {
  public static final String XML_NAME = "Panel";

  protected Panel(Generic owner, UID uid) {
    super(owner, uid);
  }
}
