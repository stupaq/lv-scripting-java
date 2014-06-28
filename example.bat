@SET scripting_dir=..\..\lv-scripting
@SET target_vi=..\..\testing\example.vi

"C:\Program Files\Java\jdk1.7.0_60\bin\java.exe" -cp "target\classes;target\dependency\*" -Dscripting.tools.path="%scripting_dir%" example "%target_vi%"

@PAUSE
