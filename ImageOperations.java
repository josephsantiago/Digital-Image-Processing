import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.imageio.*;
import javax.imageio.stream.*;


public class ImageOperations {

    private BufferedImage DefaultImage;
    private BufferedImage Image;
    private int Width, Height;
    private int MaxRGB, MinRGB;


    ImageOperations( BufferedImage image ) {
            this.DefaultImage = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB );
            setAsDefault( image );
            this.Image = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB );
    }

    ImageOperations( PGM image ) {
        this( image.get_BufferedImage() );
    }

    ImageOperations( TIFF image ) {
        this( image.get_BufferedImage() );
    }

    ImageOperations( Dicom image ) {
        this( image.get_BufferedImage() );
    }

    /* Copy a BufferedImage to DefaultImage variable */
    private void setAsDefault( BufferedImage image ){
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i=0; i < height; i++) {
            for(int j=0; j < width; j++) {
                try{
                    this.DefaultImage.setRGB(i, j, image.getRGB(i,j) );
                } catch (Exception e) {

                }

            }
        }
    }

    /* Set as default the transformed image */
    public void setAsDefault( ){
        setAsDefault( this.Image );
    }

    /* Show a BufferedImage image */
    private void show_image( BufferedImage image ) {
        JFrame jf = new JFrame();

        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Rectangle bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());

        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                Rectangle r = g.getClipBounds();
                ((Graphics2D)g).fill(r);

                if (bounds.intersects(r))
                    try {
                        g.drawImage(image, 0, 0, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        };

        jf.getContentPane().add(panel);
        panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        jf.pack();
        jf.setVisible(true);
    }

    /* Show the transformed image */
    public void show_image() {
        show_image( this.Image );
    }

    /* Show the default image */
    public void show_default_image() {
        show_image( this.DefaultImage );
    }

    /* Return the default image */
    public BufferedImage getDefaultImage() {
        return this.DefaultImage;
    }

    /* Return the transformed image */
    public BufferedImage getImage() {
        return this.Image;
    }

    /* Set the default image to negative image */
    public void toNegative() {
        int R, G, B;
        Color color;

        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                    color = new Color( this.DefaultImage.getRGB(i,j) );
                    R = 255 - color.getRed();
                    G = 255 - color.getGreen();
                    B = 255 - color.getBlue();
                    color = new Color(R, G, B);
                    this.Image.setRGB(i, j, color.getRGB() );
                }catch( Exception e ) {

                }
            }
        }
    }

    /* Gamma transformation */
    public void gammaTransformation( double gamma ){
        int R, G, B;
        int maxR, minR, maxG, minG, maxB, minB;
        Color color;
        double scale;

        scale = Math.pow( 255.0, gamma ) / 256;

        /* Scaling image */
        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                    color = new Color( this.DefaultImage.getRGB(i,j) );

                    R = (int)(Math.pow( (double)(color.getRed()), gamma ) / scale);
                    G = (int)(Math.pow( (double)(color.getGreen()), gamma ) / scale);
                    B = (int)(Math.pow( (double)(color.getBlue()), gamma ) / scale);

                    color = new Color( R, G, B);
                    this.Image.setRGB(i, j, color.getRGB() );
                }catch( Exception e ) {

                }
            }
        }

    }

    /* Histogram Equalization */
    public void histogramEqualization() {
        Color color;
        int R,G,B;
        int[][] histogram = new int[256][3];

        /* Getting histogram */
        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                    color = new Color( this.DefaultImage.getRGB(i,j) );
                    R = color.getRed();
                    G = color.getGreen();
                    B = color.getBlue();
                    histogram[R][0] ++;
                    histogram[G][1] ++;
                    histogram[B][2] ++;
                }catch( Exception e ) {

                }
            }
        }

        /* Histogram equalization */
        for( int i = 0, sumR = 0, sumG = 0, sumB = 0; i < 256; i++ ) {
            sumR += histogram[i][0];
            histogram[i][0] = (int)Math.ceil(( 255.0 * sumR / (double)(this.DefaultImage.getHeight() * this.DefaultImage.getWidth())) - 0.5);

            sumG += histogram[i][1];
            histogram[i][1] = (int)Math.ceil(( 255.0 * sumG / (double)(this.DefaultImage.getHeight() * this.DefaultImage.getWidth())) - 0.5);

            sumB += histogram[i][2];
            histogram[i][2] = (int)Math.ceil(( 255.0 * sumB / (double)(this.DefaultImage.getHeight() * this.DefaultImage.getWidth())) - 0.5);
        }

        /* Setting Histogram to image */
        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                    color = new Color( this.DefaultImage.getRGB(i,j) );
                    R = color.getRed();
                    G = color.getGreen();
                    B = color.getBlue();
                    color = new Color( histogram[R][0], histogram[G][1], histogram[B][2] );
                    this.Image.setRGB( i, j, color.getRGB() );
                }catch( Exception e ) {

                }
            }
        }
    }

    /* Verifica Dimensiones de la máscara */
    private boolean checkMaskDimension( int width, int height ) {
        if( width % 2 != 1 || height %2 != 1 || width < 0 || height < 0 )
            return false;
        return true;
    }

    /* Median filter, input => mask dimensions ( width, height ) */
    public void medianFilter( int maskWidth, int maskHeight ) {
        if( !checkMaskDimension( maskWidth, maskHeight ) ) {
            System.out.println( "Invalid mask dimensions" );
            return;
        }

        int[] valuesRGB = new int[ maskWidth*maskHeight ];

        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                int cont = 0;
                for( int offsetY = -maskHeight; offsetY < maskHeight; offsetY++ ) {
                    for( int offsetX = -maskWidth; offsetX < maskWidth; offsetX++ ) {
                        try{
                            valuesRGB[ cont ] = this.DefaultImage.getRGB( i + offsetY, j + offsetX );
                            cont++;
                        }catch( Exception e ) {
                        }
                    }
                }
                Arrays.sort( valuesRGB, 0, cont );
                try{
                    this.Image.setRGB( i, j, valuesRGB[ cont / 2 ] );
                }catch( Exception e ) {

                }
            }
        }
    }

    public void saveImage( String name ){
        try {
            File outputfile = new File( name + "png");
            ImageIO.write(this.Image, "png", outputfile);
        } catch (IOException e) {

        }
    }
}
