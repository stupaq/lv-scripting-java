package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class VIName implements ActiveXType {
  private final String name;

  public VIName(String name) {
    Preconditions.checkNotNull(name);
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public Variant toVariant() {
    return new Variant(name);
  }
}
