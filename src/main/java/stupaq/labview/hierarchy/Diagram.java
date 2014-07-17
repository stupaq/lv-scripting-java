package stupaq.labview.hierarchy;

import stupaq.labview.UID;

public class Diagram extends ConcreteGObject {
  public static final String XML_NAME = "Diagram";

  Diagram(Generic owner, UID uid) {
    super(owner, uid);
  }
}
