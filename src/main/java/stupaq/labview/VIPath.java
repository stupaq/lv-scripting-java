package stupaq.labview;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;

import com.jacob.com.Variant;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.activex.ActiveXType;

public class VIPath extends ForwardingObject implements ActiveXType {
  private final Path path;

  public VIPath(Path path) {
    Preconditions.checkNotNull(path);
    this.path = path;
  }

  public VIPath(String path) {
    Preconditions.checkNotNull(path);
    this.path = Paths.get(path);
  }

  public Path path() {
    return path;
  }

  @Override
  protected Object delegate() {
    return path;
  }

  @Override
  public Variant toVariant() {
    return new Variant(path.toAbsolutePath().toString());
  }
}
