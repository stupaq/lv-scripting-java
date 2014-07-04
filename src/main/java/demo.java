import com.google.common.base.Preconditions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.ScriptingTools;
import stupaq.labview.scripting.hierarchy.Control;
import stupaq.labview.scripting.hierarchy.ControlArray;
import stupaq.labview.scripting.hierarchy.ControlArray.ControlSupplier;
import stupaq.labview.scripting.hierarchy.Formula;
import stupaq.labview.scripting.hierarchy.FormulaNode;
import stupaq.labview.scripting.hierarchy.Indicator;
import stupaq.labview.scripting.hierarchy.IndicatorArray;
import stupaq.labview.scripting.hierarchy.IndicatorArray.IndicatorSupplier;
import stupaq.labview.scripting.hierarchy.InlineCNode;
import stupaq.labview.scripting.hierarchy.SubVI;
import stupaq.labview.scripting.hierarchy.Terminal;
import stupaq.labview.scripting.hierarchy.VI;
import stupaq.labview.scripting.hierarchy.Wire;
import stupaq.labview.scripting.tools.ControlCreate;

import static com.google.common.base.Optional.of;
import static stupaq.labview.scripting.tools.ControlCreate.ControlStyle.NUMERIC;

@SuppressWarnings("deprecation")
public class demo {

  public static void main(String[] args) {
    try {
      Preconditions.checkArgument(args.length == 1, "Missing argument: path-to-demo-dir");
      // Create demo directory.
      Path dir = Paths.get(args[0]);
      Files.createDirectories(dir);
      // Initialise scripting tools and COM bridge (LabVIEW must be available at this point).
      ScriptingTools tools = new ScriptingTools();
      // Create the main VI.
      VIPath path0 = new VIPath(dir, "example.vi");
      Files.deleteIfExists(path0.path());
      VI vi0 = new VI(tools, path0);
      vi0.create();
      // Fill with some formula nodes, ...
      Formula vi0b1 = new InlineCNode(vi0, "block 1", of("label 1"));
      // each having a few inputs and/or outputs.
      Terminal vi0t0 = vi0b1.addInput("in0");
      Terminal vi0t1 = vi0b1.addOutput("out1");
      Terminal vi0t2 = vi0b1.addOutput("this output will be removed");
      Formula vi0b2 = new FormulaNode(vi0, "block 2", of("label 2"));
      Terminal vi0t3 = vi0b2.addInput("in1");
      Formula vi0b3 = new FormulaNode(vi0, "this node will be removed", of("this label too"));
      // Connect formula nodes.
      Wire vi0w1 = new Wire(vi0, vi0t1, vi0t3, of("wire 1"));
      // Create some simple controls, ...
      Control vi0c0 = new Control(vi0, NUMERIC, of("control 0"), 0);
      Indicator vi0i0 =
          new Indicator(vi0, NUMERIC, of("indicator 0"), ControlCreate.DO_NOT_CONNECT);
      Indicator vi0i1 =
          new Indicator(vi0, NUMERIC, of("indicator 1"), ControlCreate.DO_NOT_CONNECT);
      Wire vi0w2 = new Wire(vi0, vi0c0.endpoint().get(), vi0i0.endpoint().get(), of("wire 2"));
      // and some more complicated ones.
      ControlArray vi0c1 =
          new ControlArray(vi0, of("control 1"), 1, 3, new ControlSupplier(NUMERIC));
      IndicatorArray vi0i2 =
          new IndicatorArray(vi0, of("indicator 2"), 1, 3, new IndicatorSupplier(NUMERIC));
      Wire vi0w3 = new Wire(vi0, vi0c1.endpoint().get(), vi0i2.endpoint().get(), of("wire 2"));
      // Create the VI that will be attached as a SubVI, ...
      VIPath path1 = new VIPath(Paths.get(args[0]), "example1.vi");
      Files.deleteIfExists(path1.path());
      VI vi1 = new VI(tools, path1);
      vi1.create();
      // fill it's interface, ...
      Indicator v1i1 = new Indicator(vi1, NUMERIC, of("indicator 0"), 0);
      // attach to the main VI, ...
      SubVI vi0sub0 = new SubVI(vi0, path1, "sub vi 0");
      // and wire to one of the outputs.
      Wire vi0w4 = new Wire(vi0, vi0sub0.terminals().get(0), vi0i1.endpoint().get(), of("wire 3"));
      // Distribute main VI elements.
      vi0.cleanUpDiagram();
      // Delete unnecessary elements and wires.
      vi0b3.delete();
      // TODO: t2.delete();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}