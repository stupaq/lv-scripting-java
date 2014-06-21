package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;

import java.nio.file.Path;

import stupaq.activex.ActiveXType;

public class VIPath implements ActiveXType {
  private final String path;

  public VIPath(String path) {
    this.path = path;
  }

  public VIPath(Path dir, String path) {
    Preconditions.checkNotNull(dir);
    Preconditions.checkNotNull(path);
    this.path = dir.resolve(path).toString();
  }

  public VIPath(Path dir, VIPath viPath) {
    this(dir, viPath.toString());
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
