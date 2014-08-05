import com.google.common.base.Preconditions;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.scripting.Demos;

public final class demo_highlight {

  public static void main(String[] args) {
    try {
      Preconditions.checkArgument(args.length == 1, "Missing argument: path-to-the-vi");
      Path dir = Paths.get(args[0]);
      new Demos(dir).highlightFormula();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
