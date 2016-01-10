public class supresion_no_maximos {

    
    public double [][][] supresion (double [][][] mag, double dir ) {

        int rows = mag[0].length;
        int cols = mag[0][0].length;

        double  [][][] gn = new double [3][ rows ][ cols ];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {

                    if (dir == 0 ){ // Se trata de un borde vertical
                       if( mag[color][ i ][ j ] < mag[color][ i ][ j -1 ] || mag[color][ i ][ j ] < mag[color][ i ][ j+1 ]){
                           gn[color][i][j]=0;
                       }
                       else {
                        gn[color][i][j]=mag[color][i][j] ;
                       }
                    }
                    else if (dir == 45){ // Se trata de un borde a 45
                       if( mag[color][ i ][ j ] < mag[color][ i -1 ][ j +1 ] || mag[color][ i ][ j ] < mag[color][ i +1][ j-1 ]){
                           gn[color][i][j]=0;
                       }
                       else {
                        gn[color][i][j]=mag[color][i][j] ;
                       }
                    }
                    else if (dir == 90) { // Se trata de un borde horizontal
                       if( mag[color][ i ][ j ] < mag[color][ i -1][ j  ] || mag[color][ i ][ j ] < mag[color][ i +1][ j ]){
                           gn[color][i][j]=0;
                       }
                       else {
                        gn[color][i][j]=mag[color][i][j] ;
                       }
                    }
                    else if(dir == 135) { //Se trata de un borde a -45
                       if( mag[color][ i ][ j ] < mag[color][ i -1][ j -1 ] || mag[color][ i ][ j ] < mag[color][ i+1 ][ j+1 ]){
                           gn[color][i][j]=0;
                       }
                       else {
                        gn[color][i][j]=mag[color][i][j] ;
                       }
                    }

                }
            }
        }

        return gn;
    }

}
