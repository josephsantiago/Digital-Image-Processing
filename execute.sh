#!/bin/bash
javac -classpath classes:lib/* Dicom.java PGM.java TIFF.java SpaceFilters.java ImageDft.java Main.java FFT.java FFT2.java
#java -cp lib/jai_codec-1.1.3-alpha.jar:lib/jai-core-1.1.3-alpha.jar:lib/dicom.jar:. Main
