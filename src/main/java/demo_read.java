import com.google.common.base.Preconditions;
import com.google.common.base.StandardSystemProperty;

import stupaq.labview.VIPath;
import stupaq.labview.parsing.HierarchyParser;
import stupaq.labview.parsing.PrintingVisitor;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.activex.ActiveXScriptingTools;
import stupaq.labview.scripting.fake.FakeScriptingTools;

public class demo_read {
  public static void main(String[] args) {
    try {
      Preconditions.checkArgument(args.length == 1, "Missing argument: path-to-demo-vi");
      VIPath vi = new VIPath(args[0]);
      ScriptingTools tools;
      if (StandardSystemProperty.OS_NAME.value().toLowerCase().contains("windows")) {
        tools = new ActiveXScriptingTools();
      } else {
        tools = new FakeScriptingTools();
      }
      HierarchyParser.visitVI(tools, vi, PrintingVisitor.create());
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
