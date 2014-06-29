package stupaq.labview;

import com.google.common.collect.ForwardingObject;
import com.google.common.primitives.UnsignedInts;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class UID extends ForwardingObject implements ActiveXType, Comparable<UID> {
  private final int uid;

  public UID(Variant controlValue) {
    uid = controlValue.getInt();
  }

  @Override
  public Variant toVariant() {
    return new Variant(uid);
  }

  @Override
  protected Integer delegate() {
    return Integer.valueOf(uid);
  }

  @Override
  public String toString() {
    return "UID{" + UnsignedInts.toString(uid) + '}';
  }

  @Override
  public int compareTo(UID o) {
    return delegate().compareTo(o.delegate());
  }
}
