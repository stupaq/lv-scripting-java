package com.jacob.extensions;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import com.jacob.com.LibraryLoader;

import java.io.File;
import java.io.IOException;

public class DLLFromJARClassLoader {
  public static void loadLibrary() {
    try {
      String dllName = LibraryLoader.getPreferredDLLName();
      File temporaryDll = File.createTempFile(dllName, ".dll");
      temporaryDll.deleteOnExit();
      Resources.asByteSource(DLLFromJARClassLoader.class.getResource("/" + dllName + ".dll"))
          .copyTo(Files.asByteSink(temporaryDll));
      System.setProperty(LibraryLoader.JACOB_DLL_PATH, temporaryDll.getPath());
      LibraryLoader.loadJacobLibrary();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
