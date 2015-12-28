/*
 * Fast Fourier transform
 * Base: http://rosettacode.org/wiki/Fast_Fourier_transform#C
 * @autor Esau Peralta
 * @email esau.opr@gmail.com
 */

 import java.awt.*;
 import java.awt.image.*;

class  fft2 {

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

    /* Set image to compute dft */
    public void setImage( BufferedImage image ) {
        setValues( image );
    }

    /* Return the real part of FDT */
    public double [][][] getReal() {
        return this.Real;
    }

    /* Return the imaginare part of FDT */
    public double [][][] getImag() {
        return this.Imag;
    }

    public void fft2() {
        for( int i = 0; i < this.Width ){
            
        }
    }

    public void ifft2() {

    }
}
