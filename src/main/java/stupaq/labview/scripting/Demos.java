package stupaq.labview.scripting;

import com.google.common.base.Optional;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.Maps;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import javax.xml.bind.JAXBException;

import stupaq.labview.VIPath;
import stupaq.labview.hierarchy.Bundler;
import stupaq.labview.hierarchy.CompoundArithmetic;
import stupaq.labview.hierarchy.Control;
import stupaq.labview.hierarchy.ControlArray;
import stupaq.labview.hierarchy.ControlCluster;
import stupaq.labview.hierarchy.ForLoop;
import stupaq.labview.hierarchy.Formula;
import stupaq.labview.hierarchy.FormulaNode;
import stupaq.labview.hierarchy.Indicator;
import stupaq.labview.hierarchy.IndicatorArray;
import stupaq.labview.hierarchy.IndicatorCluster;
import stupaq.labview.hierarchy.InlineCNode;
import stupaq.labview.hierarchy.RightShiftRegister;
import stupaq.labview.hierarchy.RingConstant;
import stupaq.labview.hierarchy.SubVI;
import stupaq.labview.hierarchy.Terminal;
import stupaq.labview.hierarchy.Unbundler;
import stupaq.labview.hierarchy.VI;
import stupaq.labview.hierarchy.WhileLoop;
import stupaq.labview.parsing.PrintingVisitor;
import stupaq.labview.scripting.activex.ActiveXScriptingTools;
import stupaq.labview.scripting.fake.FakeScriptingTools;
import stupaq.labview.scripting.tools.ConnectorPanePattern;

import static com.google.common.base.Optional.of;
import static java.util.Collections.singletonMap;
import static stupaq.labview.parsing.VIParser.visitVI;
import static stupaq.labview.scripting.tools.ArithmeticMode.ADD;
import static stupaq.labview.scripting.tools.ArithmeticMode.AND;
import static stupaq.labview.scripting.tools.ArithmeticMode.MULTIPLY;
import static stupaq.labview.scripting.tools.ArithmeticMode.OR;
import static stupaq.labview.scripting.tools.ConnectorPanePattern.DO_NOT_CONNECT;
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
    if (!Files.exists(dir)) {
      Files.createDirectories(dir);
    }
    // Initialise scripting tools and COM bridge (LabVIEW must be available at this point).
    if (StandardSystemProperty.OS_NAME.value().toLowerCase().contains("windows")) {
      tools = new ActiveXScriptingTools();
    } else {
      tools = new FakeScriptingTools();
    }
  }

  private VIPath overwrite(String name) throws IOException {
    VIPath path = resolve(name);
    Files.deleteIfExists(path.path());
    return path;
  }

  private VIPath resolve(String name) {
    return new VIPath(dir, name.endsWith(".vi") ? name : name + ".vi");
  }

  public void writeAll() throws IOException {
    writeFormula();
    writeControlAndIndicator();
    writeSubVI();
    writeControlAndIndicatorArray();
    writeControlAndIndicatorClusters();
    writeRingConstant();
    writeCompoundArithmetic();
    writeBundle();
    writeLoop();
  }

  public void readAll() throws IOException, JAXBException, SAXException {
    readFormula();
    readControlAndIndicator();
    readSubVI();
    readControlAndIndicatorArray();
    readControlAndIndicatorClusters();
    readRingConstant();
    readCompoundArithmetic();
    readBundle();
    readLoop();
  }

  public void readControlAndIndicatorClusters() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("control_and_indicator_clusters"), PrintingVisitor.create());
  }

  public void readControlAndIndicatorArray() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("control_and_indicator_arrays"), PrintingVisitor.create());
  }

  public void readSubVI() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("sub_vi_other_vi"), PrintingVisitor.create());
    visitVI(tools, resolve("sub_vi_the_vi"), PrintingVisitor.create());
  }

  public void readControlAndIndicator() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("control_and_indicator"), PrintingVisitor.create());
  }

  public void writeFormula() throws IOException {
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
    f1o1.connectTo(f2i1, of("wire 1"));
    f1o1.connectTo(f2i2, of("wire 2"));
    f1o2.connectTo(f2i3, of("wire 3"));
    f2o1.connectTo(f3i1, of("wire 4"));
    // Cleanup formula and diagram for inspection.
    f1.cleanupFormula();
    f2.cleanupFormula();
    f3.cleanupFormula();
    vi.cleanUpDiagram();
  }

  public void readFormula() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("formula"), PrintingVisitor.create());
  }

  public void writeControlAndIndicator() throws IOException {
    VI vi = new VI(tools, overwrite("control_and_indicator"), ConnectorPanePattern.P4835);
    // Create some controls.
    Control c0 =
        new Control(vi, BOOLEAN, of("boolean control -> 0"), 0, "with some non-empty description");
    Control c1 = new Control(vi, NUMERIC_DBL, of("double control -> 1"), 1);
    Control c2 = new Control(vi, NUMERIC_I32, of("int control -> x"));
    Indicator i0 = new Indicator(vi, BOOLEAN, of("boolean indicator -> 3"), 3);
    Indicator i1 =
        new Indicator(vi, NUMERIC_DBL, of("double indicator -> 4"), 4, "they keep the word");
    Indicator i2 = new Indicator(vi, NUMERIC_I32, of("int indicator -> x"));
    // Connect them.
    c0.terminal().connectTo(i0.terminal());
    c1.terminal().connectTo(i1.terminal());
    c2.terminal().connectTo(i2.terminal());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void writeSubVI() throws IOException {
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
    c0.terminal().connectTo(sv1.terminal(0));
    sv1.terminal(1).connectTo(sv2.terminal(0), of("wire connecting sub VIs"));
    sv2.terminal(1).connectTo(c1.terminal());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void writeControlAndIndicatorArray() throws IOException {
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
    c0.terminal().connectTo(i0.terminal());
    c1.terminal().connectTo(i1.terminal());
    c2.terminal().connectTo(i2.terminal());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void writeControlAndIndicatorClusters() throws IOException {
    VI vi = new VI(tools, overwrite("control_and_indicator_clusters"), ConnectorPanePattern.P4800);
    /// Create some controls.
    ControlCluster c1 = new ControlCluster(vi, of("control 2"), DO_NOT_CONNECT);
    new Control(c1, NUMERIC_I32, of("control 2 element 0"));
    new Control(c1, STRING, of("control 2 element 1"));
    IndicatorCluster i1 = new IndicatorCluster(vi, of("indicator 3"), DO_NOT_CONNECT);
    new Indicator(i1, NUMERIC_I32, of("indicator 2 element 0"));
    new Indicator(i1, STRING, of("indicator 2 element 1"));
    // Connect them.
    c1.terminal().connectTo(i1.terminal());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void writeRingConstant() throws IOException {
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

  public void readRingConstant() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("ring_constant"), PrintingVisitor.create());
  }

  public void writeCompoundArithmetic() throws IOException {
    VI vi = new VI(tools, overwrite("compound_arithmetic"), ConnectorPanePattern.P4800);
    // This is necessary to establish type.
    RingConstant r1 = new RingConstant(vi, singletonMap("value", 0), I32, NO_LABEL);
    /// Create some nodes.
    CompoundArithmetic a1 = new CompoundArithmetic(vi, ADD, 1, of("compound add"));
    CompoundArithmetic a2 = new CompoundArithmetic(vi, MULTIPLY, 1, of("compound mul"));
    CompoundArithmetic a3 = new CompoundArithmetic(vi, OR, 2, of("compound or"));
    CompoundArithmetic a4 = new CompoundArithmetic(vi, AND, 2, of("compound and"));
    // Connect them.
    r1.terminal().connectTo(a1.inputs().get(0));
    r1.terminal().connectTo(a2.inputs().get(0));
    a1.output().connectTo(a3.inputs().get(0));
    a2.output().connectTo(a3.inputs().get(1));
    a1.output().connectTo(a4.inputs().get(0));
    a2.output().connectTo(a4.inputs().get(1));
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void readCompoundArithmetic() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("compound_arithmetic"), PrintingVisitor.create());
  }

  public void writeBundle() throws IOException {
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
    r1.terminal().connectTo(b1.inputs().get(0));
    r1.terminal().connectTo(b2.inputs().get(0));
    b1.output().connectTo(b3.inputs().get(0));
    b2.output().connectTo(b3.inputs().get(1));
    b3.output().connectTo(u1.input());
    u1.outputs().get(0).connectTo(u2.input());
    u1.outputs().get(1).connectTo(u3.input());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void readBundle() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("bundle"), PrintingVisitor.create());
  }

  public void writeLoop() throws IOException {
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
    Control c1 = new Control(vi, NUMERIC_I32, NO_LABEL);
    Indicator i1 = new Indicator(vi, NUMERIC_I32, NO_LABEL);
    Indicator i2 = new Indicator(vi, NUMERIC_I32, NO_LABEL);
    Indicator i3 = new Indicator(vi, NUMERIC_I32, NO_LABEL);
    Indicator i4 = new Indicator(vi, NUMERIC_I32, NO_LABEL);
    // Create a few tunnels...
    u1.outputs().get(0).connectTo(i3.terminal());
    u2.outputs().get(0).connectTo(i4.terminal());
    // ...and shift registers.
    RightShiftRegister r1 = l1.addShiftRegister();
    RightShiftRegister r2 = l2.addShiftRegister(2);
    // And wire together...
    c1.terminal().connectTo(r1.left(0).outer());
    r1.left(0).inner().connectTo(b1.inputs().get(0));
    b1.output().connectTo(u1.input());
    u1.outputs().get(0).connectTo(r1.inner());
    r1.outer().connectTo(i1.terminal());
    // ...the second one too...
    c1.terminal().connectTo(r2.left(0).outer());
    r2.left(1).inner().connectTo(b2.inputs().get(0));
    b2.output().connectTo(u2.input());
    u2.outputs().get(0).connectTo(r2.inner());
    r2.outer().connectTo(i2.terminal());
    // Cleanup diagram for inspection.
    vi.cleanUpDiagram();
  }

  public void readLoop() throws IOException, JAXBException, SAXException {
    visitVI(tools, resolve("loop"), PrintingVisitor.create());
  }
}
