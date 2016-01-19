import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        if( args.length > 2 ) {
        }

        Dicom image_dcm = new Dicom( "img/dicom.dcm" );
        //image_dcm.show_image();

        PGM image_pmg_p2 = new PGM( "img/img2_p2.pgm" );
        //image_pmg_p2.show_image();

        PGM image_pmg_p5 = new PGM( "img/img2_p5.pgm" );
        //image_pmg_p5.show_image();

        TIFF image_tiff = new TIFF( "img/img3.tif" );
        //image_tiff.show_image();

        SpaceFilters FiltroOtsu = new SpaceFilters( image_pmg_p5 );
        //FiltroOtsu.show_default_image();

        //Img.toNegative();
        //Img.gammaTransformation( 2 );
        //Img.histogramEqualization();
        //FiltroOtsu.otsu();
        //FiltroOtsu.show_image();
        //Img.gradientFilter();
        //Img.laplacianFilter( 1 );
        Detector_Bordes_Canny Img = new Detector_Bordes_Canny( image_dcm );
        Img.show_default_image();

        Img.D_B_Canny();
        //Img.show_image();
        
        //ImageDft imgDFT = new ImageDft( image_pmg_p5 );
        //imgDFT.Dft();

    }

}
