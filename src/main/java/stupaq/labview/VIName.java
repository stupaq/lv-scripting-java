package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;

import java.nio.file.Path;

import stupaq.activex.ActiveXType;

public class VIName implements ActiveXType {
  private final String name;

  public VIName(Path dir, String name) {
    Preconditions.checkNotNull(dir);
    Preconditions.checkNotNull(name);
    this.name = dir.resolve(name).toString();
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
