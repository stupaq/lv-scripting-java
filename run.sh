#!/bin/bash

/c/Program\ Files/Java/jdk1.7.0_60/bin/java.exe -cp "target/lib/*;target/classes/" \
    -Dcom.jacob.debug=false -Djava.library.path="target/lib/" \
    stupaq.Main \

read
