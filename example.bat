@SET java_exe=C:\Program Files\Java\jdk1.7.0_60\bin\java.exe
@SET target_dir=target
@SET scripting_dir=..\..\lv-scripting
@SET target_vi=..\..\testing\example.vi

"%java_exe%" -cp "%target_dir%\classes;%target_dir%\dependency\*" -Dscripting.tools.path="%scripting_dir%" example "%target_vi%"

@PAUSE
