@SET target_dir=..\examples\demo\

%JAVA_HOME%\bin\java.exe ^
  -cp config;target\classes;target\dependency\* ^
  -Dscripting.tools.path=lv-scripting demo_write ^
  %target_dir%

@PAUSE