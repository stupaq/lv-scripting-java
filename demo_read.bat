@SET target_dir=..\examples\demo\

@%JAVA_HOME%\bin\java.exe ^
  -cp config;target\classes;target\dependency\* ^
  -Dscripting.tools.path=lv-scripting ^
  -Dscripting.parser.cache=false ^
  demo_read ^
  %target_dir%

@PAUSE
