@SET target_vi=..\..\testing\example.vi

"C:\Program Files\Java\jdk1.7.0_60\bin\java.exe" -cp "target\classes;target\dependency\*" -Dscripting.tools.path="..\lv-scripting" example "%target_vi%"

@PAUSE
