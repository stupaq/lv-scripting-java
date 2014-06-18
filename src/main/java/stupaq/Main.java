package stupaq;

import com.sun.jna.Memory;
import com.sun.jna.Native;

import stupaq.scripting.ScriptingLibrary;

public class Main {

  @SuppressWarnings("deprecation")
  public static void main(String[] args) {
    try {
      ScriptingLibrary instance =
          (ScriptingLibrary) Native.loadLibrary("scripting", ScriptingLibrary.class);
      Memory path = stringPointer("target3.vi");
      int len = 2048;
      Memory err = new Memory(len + 1);
      err.setMemory(0, len, (byte) 0);
      instance.CreateFormula(path, err, len);
      System.out.println(err.getString(0));
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  private static Memory stringPointer(String str) {
    Memory ptr = new Memory(str.length() + 1);
    ptr.setString(0, str);
    return ptr;
  }
}
