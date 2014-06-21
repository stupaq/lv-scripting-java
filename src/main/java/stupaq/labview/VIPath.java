package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;

import java.nio.file.Path;

import stupaq.activex.ActiveXType;

public class VIPath implements ActiveXType {
  private final String path;

  public VIPath(Path dir, String name) {
    Preconditions.checkNotNull(dir);
    Preconditions.checkNotNull(name);
    this.path = dir.resolve(name).toString();
  }

  @Override
  public String toString() {
    return path;
  }

  @Override
  public Variant toVariant() {
    return new Variant(path);
  }
}
