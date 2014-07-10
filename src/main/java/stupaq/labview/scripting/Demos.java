package stupaq.labview.scripting;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import stupaq.labview.VIPath;
import stupaq.labview.scripting.hierarchy.*;
import stupaq.labview.scripting.tools.ConnectorPanePattern;

import static com.google.common.base.Optional.of;
import static java.util.Collections.singletonMap;
import static stupaq.labview.scripting.tools.CompoundArithmeticCreate.ArithmeticMode.ADD;
import static stupaq.labview.scripting.tools.CompoundArithmeticCreate.ArithmeticMode.AND;
import static stupaq.labview.scripting.tools.CompoundArithmeticCreate.ArithmeticMode.MULTIPLY;
import static stupaq.labview.scripting.tools.CompoundArithmeticCreate.ArithmeticMode.OR;
import static stupaq.labview.scripting.tools.ControlCreate.DO_NOT_CONNECT;
import static stupaq.labview.scripting.tools.ControlStyle.BOOLEAN;
import static stupaq.labview.scripting.tools.ControlStyle.NUMERIC_DBL;
import static stupaq.labview.scripting.tools.ControlStyle.NUMERIC_I32;
import static stupaq.labview.scripting.tools.ControlStyle.STRING;
import static stupaq.labview.scripting.tools.DataRepresentation.DBL;
import static stupaq.labview.scripting.tools.DataRepresentation.I32;

public class Demos {
  private static final Optional<String> NO_LABEL = Optional.absent();
  private final Path dir;
  private final ScriptingTools tools;

  public Demos(Path dir) throws IOException {
    this.dir = dir;
    Files.createDirectories(dir);
    // Initialise scripting tools and COM bridge (LabVIEW must be available at this point).
    tools = new ScriptingTools();
  }

  private VIPath overwrite(String name) throws IOException {
    VIPath path = new VIPath(dir, name.endsWith(".vi") ? name : name + ".vi");
    Files.deleteIfExists(path.path());
    return path;
  }

  public void demoAll() throws IOException {
    demoFormula();
    demoControlAndIndicator();
    demoSubVI();
    demoControlAndIndicatorArray();
    demoControlAndIndicatorClusters();
    demoRingConstant();
    demoCompoundArithmetic();
    demoBundle();
    demoLoop();
  }

  public void demoFormula() throws IOException {
    VI vi = new VI(tools, overwrite("formula"), ConnectorPanePattern.P4800);
    // Create a few formula nodes.
    Formula f1 = new InlineCNode(vi, "inline C node", of("label of inline c node"));
    Formula f2 = new FormulaNode(vi, "formula node", of("label of formula node"));
    Formula f3 = new InlineCNode(vi, "this one will have no label", NO_LABEL);
    // Add some inputs/outputs.
    Terminal f1o1 = f1.addOutput("out1");
    Terminal f1o2 = f1.addOutput("out2");
    Terminal f2i1 = f2.addInput("in1");
    Terminal f2i2 = f2.addInput("in2");
    Terminal f2i3 = f2.addInput("in3");
    Terminal f2o1 = f2.addOutput("out1");
    Terminal f3i1 = f3.addInput("in1");
    // Connect them.
    new Wire(vi, f1o1, f2i1, of("wire 1"));
    new Wire(vi, f1o1, f2i2, of("wire 2"));
    new Wire(vi, f1o2, f2i3, of("wire 3"));
    new Wire(vi, f2o1, f3i1, of("wire 4"));
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoControlAndIndicator() throws IOException {
    VI vi = new VI(tools, overwrite("control_and_indicator"), ConnectorPanePattern.P4835);
    // Create some controls.
    Control c0 = new Control(vi, BOOLEAN, of("boolean control -> 0"), 0);
    Control c1 = new Control(vi, NUMERIC_DBL, of("double control -> 1"), 1);
    Control c2 = new Control(vi, NUMERIC_I32, of("int control -> x"), DO_NOT_CONNECT);
    Indicator i0 = new Indicator(vi, BOOLEAN, of("boolean indicator -> 3"), 3);
    Indicator i1 = new Indicator(vi, NUMERIC_DBL, of("double indicator -> 4"), 4);
    Indicator i2 = new Indicator(vi, NUMERIC_I32, of("int indicator -> x"), DO_NOT_CONNECT);
    // Connect them.
    new Wire(vi, c0.endpoint().get(), i0.endpoint().get());
    new Wire(vi, c1.endpoint().get(), i1.endpoint().get());
    new Wire(vi, c2.endpoint().get(), i2.endpoint().get());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoSubVI() throws IOException {
    // Create sub VI.
    VIPath subViPath = overwrite("sub_vi_other_vi");
    VI sub = new VI(tools, subViPath, ConnectorPanePattern.P4801);
    new Control(sub, BOOLEAN, of("sub in"), 0);
    new Indicator(sub, BOOLEAN, of("sub out"), 1);
    // Create main VI.
    VI vi = new VI(tools, overwrite("sub_vi_the_vi"), ConnectorPanePattern.P4801);
    Control c0 = new Control(vi, BOOLEAN, of("in"), 0);
    Indicator c1 = new Indicator(vi, BOOLEAN, of("out"), 1);
    // Attach to the main VI.
    SubVI sv1 = new SubVI(vi, subViPath, of("sub vi 1"));
    SubVI sv2 = new SubVI(vi, subViPath, of("sub vi 2"));
    // And wire.
    new Wire(vi, c0.endpoint().get(), sv1.terminal(0));
    new Wire(vi, sv1.terminal(1), sv2.terminal(0), of("wire connecting sub VIs"));
    new Wire(vi, sv2.terminal(1), c1.endpoint().get());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoControlAndIndicatorArray() throws IOException {
    VI vi = new VI(tools, overwrite("control_and_indicator_arrays"), ConnectorPanePattern.P4800);
    /// Create some controls.
    ControlArray c0 = new ControlArray(vi, 1, BOOLEAN, of("boolean control 1"), DO_NOT_CONNECT);
    ControlArray c1 = new ControlArray(vi, 2, NUMERIC_DBL, of("double control 2"), DO_NOT_CONNECT);
    ControlArray c2 = new ControlArray(vi, 3, NUMERIC_I32, of("int control 3"), DO_NOT_CONNECT);
    IndicatorArray i0 =
        new IndicatorArray(vi, 1, BOOLEAN, of("boolean indicator 1"), DO_NOT_CONNECT);
    IndicatorArray i1 =
        new IndicatorArray(vi, 2, NUMERIC_DBL, of("double indicator 2"), DO_NOT_CONNECT);
    IndicatorArray i2 =
        new IndicatorArray(vi, 3, NUMERIC_I32, of("int indicator 3"), DO_NOT_CONNECT);
    // Connect them.
    new Wire(vi, c0.endpoint().get(), i0.endpoint().get());
    new Wire(vi, c1.endpoint().get(), i1.endpoint().get());
    new Wire(vi, c2.endpoint().get(), i2.endpoint().get());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoControlAndIndicatorClusters() throws IOException {
    VI vi = new VI(tools, overwrite("control_and_indicator_clusters"), ConnectorPanePattern.P4800);
    /// Create some controls.
    ControlCluster c1 = new ControlCluster(vi, of("control 2"), DO_NOT_CONNECT);
    new Control(c1, NUMERIC_I32, of("control 2 element 0"), DO_NOT_CONNECT);
    new Control(c1, STRING, of("control 2 element 1"), DO_NOT_CONNECT);
    IndicatorCluster i1 = new IndicatorCluster(vi, of("indicator 3"), DO_NOT_CONNECT);
    new Indicator(i1, NUMERIC_I32, of("indicator 2 element 0"), DO_NOT_CONNECT);
    new Indicator(i1, STRING, of("indicator 2 element 1"), DO_NOT_CONNECT);
    // Connect them.
    new Wire(vi, c1.endpoint().get(), i1.endpoint().get());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoRingConstant() throws IOException {
    VI vi = new VI(tools, overwrite("ring_constant"), ConnectorPanePattern.P4800);
    /// Create some constants.
    new RingConstant(vi, singletonMap("string for 0", 0), I32, of("ring constant"));
    Map<String, Double> map1 = Maps.newHashMap();
    map1.put("string for 0", 0.0);
    map1.put("string for 1", 1.0);
    new RingConstant(vi, map1, DBL, of("control 2"));
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoCompoundArithmetic() throws IOException {
    VI vi = new VI(tools, overwrite("compound_arithmetic"), ConnectorPanePattern.P4800);
    // This is necessary to establish type.
    RingConstant r1 = new RingConstant(vi, singletonMap("value", 0), I32, NO_LABEL);
    /// Create some nodes.
    CompoundArithmetic a1 = new CompoundArithmetic(vi, ADD, 1, of("compound add"));
    CompoundArithmetic a2 = new CompoundArithmetic(vi, MULTIPLY, 1, of("compound mul"));
    CompoundArithmetic a3 = new CompoundArithmetic(vi, OR, 2, of("compound or"));
    CompoundArithmetic a4 = new CompoundArithmetic(vi, AND, 2, of("compound and"));
    // Connect them.
    new Wire(vi, r1.endpoint().get(), a1.inputs().get(0));
    new Wire(vi, r1.endpoint().get(), a2.inputs().get(0));
    new Wire(vi, a1.output(), a3.inputs().get(0));
    new Wire(vi, a2.output(), a3.inputs().get(1));
    new Wire(vi, a1.output(), a4.inputs().get(0));
    new Wire(vi, a2.output(), a4.inputs().get(1));
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoBundle() throws IOException {
    VI vi = new VI(tools, overwrite("bundle"), ConnectorPanePattern.P4800);
    // This is necessary to establish type.
    RingConstant r1 = new RingConstant(vi, singletonMap("value", 0), I32, NO_LABEL);
    // Create some nodes.
    Bundler b1 = new Bundler(vi, 1, NO_LABEL);
    Bundler b2 = new Bundler(vi, 1, NO_LABEL);
    Bundler b3 = new Bundler(vi, 2, NO_LABEL);
    Unbundler u1 = new Unbundler(vi, 2, NO_LABEL);
    Unbundler u2 = new Unbundler(vi, 1, NO_LABEL);
    Unbundler u3 = new Unbundler(vi, 1, NO_LABEL);
    // Connect them in a nice bidirectional tree.
    new Wire(vi, r1.endpoint().get(), b1.inputs().get(0));
    new Wire(vi, r1.endpoint().get(), b2.inputs().get(0));
    new Wire(vi, b1.output(), b3.inputs().get(0));
    new Wire(vi, b2.output(), b3.inputs().get(1));
    new Wire(vi, b3.output(), u1.input());
    new Wire(vi, u1.outputs().get(0), u2.input());
    new Wire(vi, u1.outputs().get(1), u3.input());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void demoLoop() throws IOException {
    VI vi = new VI(tools, overwrite("loop"), ConnectorPanePattern.P4800);
    // Create loops.
    WhileLoop l1 = new WhileLoop(vi, of("while loop"));
    ForLoop l2 = new ForLoop(vi, of("for loop"));
    // Some controls inside...
    Bundler b1 = new Bundler(l1.diagram(), 1, NO_LABEL);
    Unbundler u1 = new Unbundler(l1.diagram(), 1, NO_LABEL);
    Bundler b2 = new Bundler(l2.diagram(), 1, NO_LABEL);
    Unbundler u2 = new Unbundler(l2.diagram(), 1, NO_LABEL);
    // ...and outside.
    Control c1 = new Control(vi, NUMERIC_I32, NO_LABEL, DO_NOT_CONNECT);
    Indicator i1 = new Indicator(vi, NUMERIC_I32, NO_LABEL, DO_NOT_CONNECT);
    Indicator i2 = new Indicator(vi, NUMERIC_I32, NO_LABEL, DO_NOT_CONNECT);
    // Create a few tunnels...
    // ...and shift registers.
    RightShiftRegister r1 = l1.addShiftRegister();
    RightShiftRegister r2 = l2.addShiftRegister(2);
    // And wire together...
    new Wire(vi, c1.endpoint().get(), r1.left(0).outer());
    new Wire(l1.diagram(), r1.left(0).inner(), b1.inputs().get(0));
    new Wire(l1.diagram(), b1.output(), u1.input());
    new Wire(l1.diagram(), u1.outputs().get(0), r1.inner());
    new Wire(vi, r1.outer(), i1.endpoint().get());
    // ...the second one too...
    new Wire(vi, c1.endpoint().get(), r2.left(0).outer());
    new Wire(l2.diagram(), r2.left(1).inner(), b2.inputs().get(0));
    new Wire(l2.diagram(), b2.output(), u2.input());
    new Wire(l2.diagram(), u2.outputs().get(0), r2.inner());
    new Wire(vi, r2.outer(), i2.endpoint().get());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }
}
