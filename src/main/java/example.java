import java.nio.file.Files;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.hierarchy.Control;
import stupaq.labview.scripting.hierarchy.Formula;
import stupaq.labview.scripting.hierarchy.FormulaNode;
import stupaq.labview.scripting.hierarchy.Indicator;
import stupaq.labview.scripting.hierarchy.InlineCNode;
import stupaq.labview.scripting.hierarchy.Terminal;
import stupaq.labview.scripting.hierarchy.VI;
import stupaq.labview.scripting.hierarchy.Wire;
import stupaq.labview.scripting.tools.ControlCreate;

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
      VIPath path = new VIPath(args[0]);
      Files.deleteIfExists(path.path());
      VI vi = new VI(tools, path);
      vi.create();
      Formula b1 = new InlineCNode(vi.generic(), "block 1", "label 1");
      Terminal t0 = b1.addInput("in 0");
      Terminal t1 = b1.addOutput("out 1");
      Terminal t2 = b1.addOutput("this output will be removed");
      Formula b2 = new FormulaNode(vi.generic(), "block 2", "label 2");
      Terminal t3 = b2.addInput("in 1");
      Wire w1 = new Wire(t1, t3, "wire 1");
      Formula b3 = new FormulaNode(vi.generic(), "this node will be removed", "this label too");
      Control c0 = new Control(vi.generic(), ControlCreate.NUMERIC, "control 0");
      Indicator i0 = new Indicator(vi.generic(), ControlCreate.NUMERIC, "indicator 0");
      Wire w2 = new Wire(c0.terminal(), i0.terminal(), "wire 2");
      vi.cleanUpDiagram();
      b3.delete();
      //t2.delete();
    } else {
      throw new IllegalArgumentException("Arguments: path-to-a-VI");
    }
  }
}
