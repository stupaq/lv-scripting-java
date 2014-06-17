package stupaq;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import scripting.ScriptingLibrary;

public class Main {

  public static void main(String[] args) {
    ScriptingLibrary instance =
        (ScriptingLibrary) Native.loadLibrary("lib\\scripting.dll", ScriptingLibrary.class);
    Pointer path = stringPointer("target3");
    instance.CreateFormula(path);
  }

  private static Pointer stringPointer(String str) {
    Pointer ptr = new Memory(str.length() + 1);
    ptr.setString(0, str);
    return ptr;
  }
}
