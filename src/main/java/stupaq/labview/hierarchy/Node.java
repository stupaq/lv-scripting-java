package stupaq.labview.hierarchy;

import stupaq.labview.UID;

public abstract class Node extends ConcreteGObject {
  protected Node(Generic owner, UID uid) {
    super(owner, uid);
  }
}
