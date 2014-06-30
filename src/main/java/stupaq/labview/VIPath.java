package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.activex.ActiveXType;

public class VIPath implements ActiveXType {
  private final Path path;

  public VIPath(Path path) {
    Preconditions.checkNotNull(path);
    this.path = path;
  }

  public VIPath(Path dir, String name) {
    this(dir.resolve(name));
  }

  public VIPath(String path) {
    Preconditions.checkNotNull(path);
    this.path = Paths.get(path);
  }

  public Path path() {
    return path;
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return this == o ||
        !(o == null || getClass() != o.getClass()) && path.equals(((VIPath) o).path);
  }

  @Override
  public String toString() {
    return path.toString();
  }

  @Override
  public Variant toVariant() {
    return new Variant(path.toAbsolutePath().toString());
  }
}
