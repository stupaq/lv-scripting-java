package stupaq;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import com.sun.jna.Memory;
import com.sun.jna.Native;

import stupaq.scripting.ScriptingLibrary;

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

  private static void sharedLibrary() throws Exception {
    ScriptingLibrary instance =
        (ScriptingLibrary) Native.loadLibrary("scripting", ScriptingLibrary.class);
    Memory path = stringPointer("target3.vi");
    int len = 2048;
    Memory err = new Memory(len + 1);
    err.setMemory(0, len, (byte) 0);
    instance.CreateFormula(path, err, len);
    System.out.println(err.getString(0));
  }

  private static Memory stringPointer(String str) {
    Memory ptr = new Memory(str.length() + 1);
    ptr.setString(0, str);
    return ptr;
  }
}
