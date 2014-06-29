package stupaq.labview;

import com.google.common.primitives.UnsignedInts;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class UID implements ActiveXType, Comparable<UID> {
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

  @Override
  public int compareTo(UID o) {
    return Integer.valueOf(uid).compareTo(o.uid);
  }

  @Override
  public boolean equals(Object o) {
    return this == o || !(o == null || getClass() != o.getClass()) && uid == ((UID) o).uid;
  }

  @Override
  public int hashCode() {
    return uid;
  }
}
