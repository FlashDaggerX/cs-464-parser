#!/bin/bash

javac src/com/github/fdx/*.java
cd src/

java com/github/fdx/Main <<< "parse.txt"
