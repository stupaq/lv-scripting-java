package stupaq.labview.scripting.hierarchy;

import stupaq.labview.UID;

public abstract class Structure extends Node {
  protected Structure(Generic owner, UID uid) {
    super(owner, uid);
  }
}
