@SET target_dir=..\examples\demo\

%JAVA_HOME%\bin\java.exe -cp target\classes;target\dependency\* -Dscripting.tools.path=lv-scripting demo %target_dir%

@PAUSE
