package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VIPath implements ActiveXType {
  public static final String VI_EXTENSION = ".vi";
  private final Path path;

  public VIPath(Path path) {
    Preconditions.checkNotNull(path);
    Preconditions.checkArgument(path.toString().endsWith(VI_EXTENSION), "Bad extension: %s", path);
    this.path = path;
  }

  public VIPath(Path dir, String name) {
    this(dir.resolve(name));
  }

  public VIPath(String path) {
    this(Paths.get(path));
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

  public String getBaseName() {
    Path fileName = getFileName();
    if (fileName == null) {
      return null;
    }
    String name = fileName.toString();
    return name.substring(0, name.length() - VI_EXTENSION.length());
  }

  public Path path() {
    return path;
  }

  public Path getFileName() {
    return path.getFileName();
  }
}
