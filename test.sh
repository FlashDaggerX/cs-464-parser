#!/bin/bash

javac src/com/github/fdx/*.java
cd src/
echo -e "test.txt\r\n" | java com/github/fdx/Main
