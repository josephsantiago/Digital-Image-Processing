/*
 * Discrete Fourier transform
 * Base: http://www.nayuki.io/page/how-to-implement-the-discrete-fourier-transform
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.imageio.*;
import javax.imageio.stream.*;

public class ImageDft {

    private double [][][]Real;
    private double [][][]Imag;
    private int Width;
    private int Height;

    private static final int R = 0;
    private static final int G = 1;
    private static final int B = 2;

    /* Constructors */
    ImageDft( BufferedImage image ) {
        setValues( image );
    }

    ImageDft( PGM image ) {
        this( image.get_BufferedImage() );
    }

    ImageDft( TIFF image ) {
        this( image.get_BufferedImage() );
    }

    ImageDft( Dicom image ) {
        this( image.get_BufferedImage() );
    }

    /* Initial Real and imaginare values from image */
    private void setValues( BufferedImage image ) {
        this.Width = image.getWidth();
        this.Height = image.getHeight();
        Color color;

        this.Real = new double[ this.Height ][ this.Width ][3];
        this.Imag = new double[ this.Height ][ this.Width ][3];

        for (int i=0; i < this.Width; i++) {
            for(int j=0; j < this.Height; j++) {
                try{
                    /* BufferedImage contains image in RGB we will work with their components*/
                    color = new Color( image.getRGB(i,j) );
                    this.Real[i][j][R] = color.getRed();
                    this.Real[i][j][G] = color.getGreen();
                    this.Real[i][j][B] = color.getBlue();
                } catch (Exception e) {
                }
            }
        }
    }

    public double [][][] getReal() {
        return this.Real;
    }

    public double [][][] getImag() {
        return this.Imag;
    }

    public void computeDft() {

        /* BufferedImage contains image in RGB we will work with their components*/
        double [][][]tmpReal = new double[ this.Height ][ this.Width ][3];
        double [][][]tmpImag = new double[ this.Height ][ this.Width ][3];

        double []sumReal = new double[3];
        double []sumImag = new double[3];

        /* Horizontal */
        for (int i = 0; i < this.Height; i++) { // For each row
            for (int k = 0; k < this.Width; k++) {  // For each element in the row
    			sumReal[R] = sumReal[G] = sumReal[B] = 0;
    			sumImag[R] = sumImag[G] = sumImag[B] = 0;

                for (int t = 0; t < this.Width; t++) {  // For each element in the row
                    double angle = 2 * Math.PI * t * k / this.Width;

                    sumReal[R] +=  this.Real[i][t][R] * Math.cos(angle) + this.Imag[i][t][R] * Math.sin(angle);
    				sumImag[R] += -this.Real[i][t][R] * Math.sin(angle) + this.Imag[i][t][R] * Math.cos(angle);

                    sumReal[G] +=  this.Real[i][t][G] * Math.cos(angle) + this.Imag[i][t][G] * Math.sin(angle);
    				sumImag[G] += -this.Real[i][t][G] * Math.sin(angle) + this.Imag[i][t][G] * Math.cos(angle);

                    sumReal[B] +=  this.Real[i][t][B] * Math.cos(angle) + this.Imag[i][t][B] * Math.sin(angle);
    				sumImag[B] += -this.Real[i][t][B] * Math.sin(angle) + this.Imag[i][t][B] * Math.cos(angle);
    			}
    			tmpReal[i][k][R] = sumReal[R];
    			tmpImag[i][k][R] = sumImag[R];
                tmpReal[i][k][G] = sumReal[G];
    			tmpImag[i][k][G] = sumImag[G];
                tmpReal[i][k][B] = sumReal[B];
    			tmpImag[i][k][B] = sumImag[B];
    		}
        }

        /* Vertical */
        for (int i = 0; i < this.Width; i++) { // For each row
            for (int k = 0; k < this.Height; k++) {  // For each element in the row
                sumReal[R] = sumReal[G] = sumReal[B] = 0;
                sumImag[R] = sumImag[G] = sumImag[B] = 0;

                for (int t = 0; t < this.Height; t++) {  // For each element in the row
                    double angle = 2 * Math.PI * t * k / this.Height;

                    sumReal[R] +=  tmpReal[i][t][R] * Math.cos(angle) + tmpImag[i][t][R] * Math.sin(angle);
                    sumImag[R] += -tmpReal[i][t][R] * Math.sin(angle) + tmpImag[i][t][R] * Math.cos(angle);

                    sumReal[G] +=  tmpReal[i][t][G] * Math.cos(angle) + tmpImag[i][t][G] * Math.sin(angle);
                    sumImag[G] += -tmpReal[i][t][G] * Math.sin(angle) + tmpImag[i][t][G] * Math.cos(angle);

                    sumReal[B] +=  tmpReal[i][t][B] * Math.cos(angle) + tmpImag[i][t][B] * Math.sin(angle);
                    sumImag[B] += -tmpReal[i][t][B] * Math.sin(angle) + tmpImag[i][t][B] * Math.cos(angle);
                }
                this.Real[i][k][R] = sumReal[R];
                this.Imag[i][k][R] = sumImag[R];
                this.Real[i][k][G] = sumReal[G];
                this.Imag[i][k][G] = sumImag[G];
                this.Real[i][k][B] = sumReal[B];
                this.Imag[i][k][B] = sumImag[B];
            }
        }

	}

}
