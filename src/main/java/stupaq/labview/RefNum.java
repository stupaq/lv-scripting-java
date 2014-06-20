package stupaq.labview;

import com.google.common.primitives.UnsignedInts;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class RefNum implements ActiveXType {
  private final int uuid;

  public RefNum(Variant controlValue) {
    uuid = controlValue.getInt();
  }

  @Override
  public Variant toVariant() {
    return new Variant(uuid);
  }

  @Override
  public String toString() {
    return "RefNum{" + "uuid=" + UnsignedInts.toString(uuid) + '}';
  }
}
