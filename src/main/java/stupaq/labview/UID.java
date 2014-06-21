package stupaq.labview;

import com.google.common.primitives.UnsignedInts;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class UID implements ActiveXType {
  private final int uid;

  public UID(Variant controlValue) {
    uid = controlValue.getInt();
  }

  @Override
  public Variant toVariant() {
    return new Variant(uid);
  }

  @Override
  public String toString() {
    return "UID{" + UnsignedInts.toString(uid) + '}';
  }
}
