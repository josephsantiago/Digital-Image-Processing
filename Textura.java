import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.lang.Math;

public class Textura{

    private BufferedImage DefaultImage;
    private BufferedImage Image;
    private int Width, Height;
    private int MaxRGB, MinRGB;


    Textura( BufferedImage image ) {
        this.Width = image.getWidth();
        this.Height = image.getHeight();

        this.DefaultImage = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB );
        setAsDefault( image );
        this.Image = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB );
    }

    Textura( PGM image ) {
        this( image.get_BufferedImage() );
    }

    Textura( TIFF image ) {
        this( image.get_BufferedImage() );
    }

    Textura( Dicom image ) {
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
    private void show_image( final BufferedImage image ) {
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

    public double uniformidad(){
        Color color;
        int R,G,B;
        double[] histogram = new double[256];

        /* Getting histogram */
        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                    color = new Color( this.DefaultImage.getRGB(i,j) );
                    R = color.getRed();
                    G = color.getGreen();
                    B = color.getBlue();
                    histogram[R]++;
                    histogram[G]++;
                    histogram[B]++;
                }catch( Exception e ) {
                }
            }
        }
        int sumatoria=0;

        for( int i = 0 ; i < 256 ; i++ ){
          //  System.out.print("NI: "+ i +"\t"+ "NP: "+ histogram[i]+"\t");
            histogram[i]=histogram[i]/((double)(this.DefaultImage.getHeight() * this.DefaultImage.getWidth()));
           // System.out.println("PN: "+ histogram[i]);
        }
        for( int i = 0 ; i < 256 ; i++ ) {
            double p= histogram[i]*histogram[i];
            sumatoria+=p;
        }

        return sumatoria;

    }


    /* Return the standard deviation from DRN for each color channel */
    public double[] standardDeviation(  ) {
        double []mean = {0, 0, 0};
        //mean = mean();

        double []sum = {0, 0, 0};

        /* Getting histogram */
        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                    Color color = new Color( this.DefaultImage.getRGB(i,j) );
                    int R = color.getRed();
                    int G = color.getGreen();
                    int B = color.getBlue();
                    sum[0] = Math.pow( R - mean[0], 2 );
                    sum[1] = Math.pow( G - mean[1], 2 );
                    sum[2] = Math.pow( B - mean[2], 2 );
                }catch( Exception e ) {
                }
            }
        }

        for (int color = 0; color < 3; color ++) {
            sum[ color ] = 1.0 - 1.0 /( 1.0 - sum[ color ]);
        }

        return sum;
    }

}
