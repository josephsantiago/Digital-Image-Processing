#!/bin/bash
javac -classpath classes:lib/* Dicom.java PGM.java TIFF.java ImageOperations.java Main.java
java -cp lib/jai_codec-1.1.3-alpha.jar:lib/jai-core-1.1.3-alpha.jar:lib/dicom.jar:. Main $1 $2
