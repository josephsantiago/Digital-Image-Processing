import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.imageio.*;
import javax.imageio.stream.*;


public class Detector_Bordes_Canny {

    private BufferedImage DefaultImage;
    private BufferedImage Image;
    private int Width, Height;
    private int MaxRGB, MinRGB;


    Detector_Bordes_Canny( BufferedImage image ) {
        this.Width = image.getWidth();
        this.Height = image.getHeight();

        this.DefaultImage = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB );
        setAsDefault( image );
        this.Image = new BufferedImage( image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB );
    }

    Detector_Bordes_Canny( PGM image ) {
        this( image.get_BufferedImage() );
    }

    Detector_Bordes_Canny( TIFF image ) {
        this( image.get_BufferedImage() );
    }

    Detector_Bordes_Canny( Dicom image ) {
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

    public void D_B_Canny(){

        //Aplicando suavizado Gaussiano
        SpaceFilters ImgA = new SpaceFilters( this.DefaultImage );
        double [][][] ImgSuavizada = ImgA.weightyFilter( );
        
        //Seteando la nueva imagen
        int R, G, B;
        Color color;
        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                   //color = new Color( ImgA.getImage().getRGB(i,j) );
                    R = (int)ImgSuavizada[ i ][ j ][ 0 ];
                    G = (int)ImgSuavizada[ i ][ j ][ 1 ];
                    B = (int)ImgSuavizada[ i ][ j ][ 2 ];
                    color = new Color(R, G, B);
                    this.DefaultImage.setRGB(i, j, color.getRGB() );
                }catch( Exception e ) {

                }
            }
        }
        this.show_default_image();

        //Obteniendo magnitud de gradiente
        SpaceFilters ImgB = new SpaceFilters( this.DefaultImage );
        double [][][] ImgGradMag = ImgB.gradientFilter( );

        //Obteniendo direccion de gradiente
        SpaceFilters ImgC = new SpaceFilters( this.DefaultImage );
        double [][][] ImgGradDir = ImgC.gradientDirection( );
               
        //Obteniendo suprecion de no maximos
        supresion_no_maximos ImgD = new supresion_no_maximos( );
        double [][][] ImgSuprecion = ImgD.supresion( ImgGradMag, ImgGradDir );
                
        /*for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                   //color = new Color( ImgA.getImage().getRGB(i,j) );
                    R = (int)ImgSuprecion[ i ][ j ][ 0 ];
                    G = (int)ImgSuprecion[ i ][ j ][ 1 ];
                    B = (int)ImgSuprecion[ i ][ j ][ 2 ];
                    color = new Color(R, G, B);
                    this.Image.setRGB(i, j, color.getRGB() );
                }catch( Exception e ) {

                }
            }
        }
        this.show_image();*/
        double Th=100;
        double Tl=50;

        CannyEdge ImgEdge = new CannyEdge( );
        int [][][] ImgHysteresis = ImgEdge.hysteresis( ImgSuprecion, Th, Tl );

        for( int i = 0; i < this.DefaultImage.getHeight(); i++ ) {
            for( int j = 0; j < this.DefaultImage.getWidth(); j++ ) {
                try{
                   //color = new Color( ImgA.getImage().getRGB(i,j) );
                    R = (int)ImgHysteresis[ i ][ j ][ 0 ];
                    G = (int)ImgHysteresis[ i ][ j ][ 1 ];
                    B = (int)ImgHysteresis[ i ][ j ][ 2 ];
                    color = new Color(R, G, B);
                    this.Image.setRGB(i, j, color.getRGB() );
                }catch( Exception e ) {

                }
            }
        }
        this.show_image();
        
    }

    /* Save image as png in te current directory */
    public void saveImage( String name ){
        try {
            File outputfile = new File( name + "png");
            ImageIO.write(this.Image, "png", outputfile);
        } catch (IOException e) {

        }
    }
}
