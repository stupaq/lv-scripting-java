package com.jacob.samples;

import com.jacob.com.LibraryLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DLLFromJARClassLoader {
  public static void loadLibrary() {
    try {
      InputStream inputStream = DLLFromJARClassLoader.class.getResource(
          "/" + LibraryLoader.getPreferredDLLName() + ".dll").openStream();
      File temporaryDll = File.createTempFile(LibraryLoader.getPreferredDLLName(), ".dll");
      FileOutputStream outputStream = new FileOutputStream(temporaryDll);
      byte[] array = new byte[8192];
      for (int i = inputStream.read(array); i != -1; i = inputStream.read(array)) {
        outputStream.write(array, 0, i);
      }
      outputStream.close();
      temporaryDll.deleteOnExit();
      System.setProperty(LibraryLoader.JACOB_DLL_PATH, temporaryDll.getPath());
      LibraryLoader.loadJacobLibrary();
    } catch (Throwable e) {
      e.printStackTrace(System.err);
      throw new RuntimeException(e);
    }
  }
}
