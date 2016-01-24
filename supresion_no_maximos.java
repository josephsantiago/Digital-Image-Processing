@@ -1,5 +1,55 @@
public class supresion_no_maximos {

    
    public double [][][] supresion (double [][][] mag, double [][][] dir1 ) {

        int rows = mag[0].length;
        int cols = mag[0][0].length;

        double [][][] dir= new double[3][rows][cols];

        for(int i=0; i< rows; i++){
            for (int j = 0; j < cols; j++) {
               for(int color = 0; color < 3; color++) {
                  if((dir1 [color][i][j]>=-22.5 && dir1[color][i][j]<22.5)|| (dir1 [color][i][j]>=157.5 && dir1 [color][i][j]<-157.5))
                      dir[color][i][j]=0;   //borde vertical

                  if((dir1 [color][i][j]>=22.5 && dir1[color][i][j]<67.5) || (dir1[color][i][j]>=-157.5 && dir1[color][i][j]< -112.5))
                      dir[color][i][j]= 45;

                  if((dir1[color][i][j]>=67.5 && dir1 [color][i][j]<112.5) || (dir1 [color][i][j]>= -112.5 && dir1[color][i][j]< -67.5))
                      dir[color][i][j]= 90;

                   if((dir1[color][i][j]>=112.5 && dir1[color][i][j]<157.5) || (dir1[color][i][j]>= -67.5 && dir1[color][i][j]< -22.5))
                      dir[color][i][j]= 135;
                  }
            }
        }


        double  [][][] gn = new double [3][ rows ][ cols ];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {

                  if(i-1>0 && j-1 >0 && i+1 < rows && j+1< cols){
                        if (dir[color][i][j] == 0 ){ // Se trata de un borde vertical
                           if( mag[color][ i ][ j ] < mag[color][ i ][ j -1 ] || mag[color][ i ][ j ] < mag[color][ i ][ j+1 ]){
                               gn[color][i][j]=0;
                           }
                           else {
                            gn[color][i][j]=mag[color][i][j] ;
                           }
                        }
                        else if (dir[color][i][j] == 45){ // Se trata de un borde a 45
                           if( mag[color][ i ][ j ] < mag[color][ i -1 ][ j +1 ] || mag[color][ i ][ j ] < mag[color][ i +1][ j-1 ]){
                               gn[color][i][j]=0;
                           }
                           else {
                            gn[color][i][j]=mag[color][i][j] ;
                           }
                        }
                        else if (dir[color][i][j] == 90) { // Se trata de un borde horizontal
                           if( mag[color][ i ][ j ] < mag[color][ i -1][ j  ] || mag[color][ i ][ j ] < mag[color][ i +1][ j ]){
                               gn[color][i][j]=0;
                           }
                           else {
                            gn[color][i][j]=mag[color][i][j] ;
                           }
                        }
                        else if(dir[color][i][j] == 135) { //Se trata de un borde a -45
                           if( mag[color][ i ][ j ] < mag[color][ i -1][ j -1 ] || mag[color][ i ][ j ] < mag[color][ i+1 ][ j+1 ]){
                               gn[color][i][j]=0;
                           }
                           else {
                            gn[color][i][j]=mag[color][i][j] ;
                           }
                        }
                    } 
                    else
                      gn[color][i][j]=mag[color][i][j] ;


                }
            }
        }

        return gn;
    }

}