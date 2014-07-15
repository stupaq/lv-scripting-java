import com.google.common.base.Preconditions;

import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.retrieval.VIParser;
import stupaq.labview.scripting.fake.FakeScriptingTools;

public class demo_read {
  public static void main(String[] args) {
    try {
      Preconditions.checkArgument(args.length == 1, "Missing argument: path-to-demo-dir");
      Path dir = Paths.get(args[0]);
      VIPath vi = new VIPath(dir, "formula.vi");
      ScriptingTools tools = new FakeScriptingTools();
      new VIParser(tools, vi);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
