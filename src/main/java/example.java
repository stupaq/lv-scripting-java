import java.nio.file.Files;
import java.nio.file.Paths;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.hierarchy.Control;
import stupaq.labview.scripting.hierarchy.Formula;
import stupaq.labview.scripting.hierarchy.FormulaNode;
import stupaq.labview.scripting.hierarchy.Indicator;
import stupaq.labview.scripting.hierarchy.InlineCNode;
import stupaq.labview.scripting.hierarchy.SubVI;
import stupaq.labview.scripting.hierarchy.Terminal;
import stupaq.labview.scripting.hierarchy.VI;
import stupaq.labview.scripting.hierarchy.Wire;
import stupaq.labview.scripting.tools.ControlCreate;

import static com.google.common.base.Optional.of;

@SuppressWarnings("deprecation")
public class example {

  public static void main(String[] args) {
    try {
      run(args);
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  private static void run(String[] args) throws Exception {
    if (args.length == 1) {
      ScriptingTools tools = new ScriptingTools();
      VIPath path0 = new VIPath(Paths.get(args[0]), "example.vi");
      Files.deleteIfExists(path0.path());
      VI vi0 = new VI(tools, path0);
      vi0.create();
      Formula vi0b1 = new InlineCNode(vi0, "block 1", of("label 1"));
      Terminal vi0t0 = vi0b1.addInput("in 0");
      Terminal vi0t1 = vi0b1.addOutput("out 1");
      Terminal vi0t2 = vi0b1.addOutput("this output will be removed");
      Formula vi0b2 = new FormulaNode(vi0, "block 2", of("label 2"));
      Terminal vi0t3 = vi0b2.addInput("in 1");
      Wire vi0w1 = new Wire(vi0t1, vi0t3, of("wire 1"));
      Formula vi0b3 = new FormulaNode(vi0, "this node will be removed", of("this label too"));
      Control vi0c0 = new Control(vi0, ControlCreate.NUMERIC, "control 0", 0);
      Indicator vi0i0 =
          new Indicator(vi0, ControlCreate.NUMERIC, "indicator 0", ControlCreate.DO_NOT_CONNECT);
      Indicator vi0i1 =
          new Indicator(vi0, ControlCreate.NUMERIC, "indicator 1", ControlCreate.DO_NOT_CONNECT);
      Wire vi0w2 = new Wire(vi0c0.terminal(), vi0i0.terminal(), of("wire 2"));
      VIPath path1 = new VIPath(Paths.get(args[0]), "example1.vi");
      Files.deleteIfExists(path1.path());
      VI vi1 = new VI(tools, path1);
      vi1.create();
      Indicator v1i1 = new Indicator(vi1, ControlCreate.NUMERIC, "indicator 0", 0);
      SubVI vi0sub0 = new SubVI(vi0, path1, "sub vi 0");
      Wire vi0w3 = new Wire(vi0sub0.terminals().get(0), vi0i1.terminal(), of("wire 3"));
      vi0.cleanUpDiagram();
      vi0b3.delete();
      //t2.delete();
    } else {
      throw new IllegalArgumentException("Arguments: path-to-a-VI");
    }
  }
}
