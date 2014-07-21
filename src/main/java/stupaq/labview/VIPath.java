package stupaq.labview;

import com.google.common.base.Preconditions;

import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VIPath implements ActiveXType {
  public static final String VI_EXTENSION = ".vi";
  private final Path path;


  public VIPath(Path dir, String name) {
    this(dir.resolve(name).toString());
  }

  public VIPath(String path) {
    Preconditions.checkNotNull(path);
    Preconditions.checkArgument(path.endsWith(VI_EXTENSION), "Bad extension: %s", path);
    this.path = Paths.get(FilenameUtils.separatorsToSystem(path));
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
    return FilenameUtils.getBaseName(path.toString());
  }

  public Path path() {
    return path;
  }

  public Path getFileName() {
    return path.getFileName();
  }
}
