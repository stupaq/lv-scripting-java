@SET target_dir=..\examples\demo\formula.vi

%JAVA_HOME%\bin\java.exe -cp config;target\classes;target\dependency\* -Dscripting.tools.path=lv-scripting demo_read %target_vi%

@PAUSE
