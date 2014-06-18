package stupaq;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

@SuppressWarnings("deprecation")
public class Main {

  public static void main(String[] args) {
    try {
      activeX();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  private static void activeX() throws Exception {
    ActiveXComponent lvApp = new ActiveXComponent("LabVIEW.Application");
    Variant viRef = lvApp.invoke("GetVIReference",
        new Variant("C:\\Documents and Settings\\user\\Pulpit\\lv-scripting\\create formula.vi"),
        new Variant(""), new Variant(false), new Variant(0));
    ActiveXComponent vi = new ActiveXComponent(viRef.toDispatch());
    vi.invoke("OpenFrontPanel", new Variant(true), new Variant(1));
    vi.invoke("SetControlValue", new Variant("path"),
        new Variant("C:\\Documents and Settings\\user\\Pulpit\\lv-scripting\\target3.vi"));
    Variant res = vi.invoke("Run");
    System.out.println(res.toString());
    // TODO
  }
}
