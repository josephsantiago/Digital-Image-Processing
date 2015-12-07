import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        if( args.length > 2 ) {
            /*
                -f  [archivo de imagen médica]
                -fi [archivo con nombres de imágenes]
            */
            /*
            FileReader f = new FileReader(archivo);
            BufferedReader b = new BufferedReader(f);
            while((cadena = b.readLine())!=null) {
                System.out.println(cadena);
            }
            b.close();
            */
        }

        Dicom image_dcm = new Dicom( "img/dicom.dcm" );
        //image_dcm.show_image();

        PGM image_pmg_p2 = new PGM( "img/img2_p2.pgm" );
        //image_pmg_p2.show_image();

        PGM image_pmg_p5 = new PGM( "img/img2_p5.pgm" );
        //image_pmg_p5.show_image();

        TIFF image_tiff = new TIFF( "img/img3.tif" );
        //image_tiff.show_image();

        ImageOperations Img = new ImageOperations( image_pmg_p5 );
        Img.show_default_image();

        //Img.toNegative();
        Img.gammaTransformation( 2 );
        //Img.histogramEqualization();
        //Img.medianFilter( 3, 3 );

        Img.show_image();

        /**/

        ImageDft imgDFT = new ImageDft( image_pmg_p5 );
        //imgDFT.computeDft();

    }

}
