
public class supresion_no_maximos {

    
    public double [][][] supresion (double [][][] mag, double [][][] dir1 ) {

        int rows = mag[0].length;
        int cols = mag.length;
        System.out.println(rows);
        System.out.println(cols);

        double [][][] dir= new double[rows][cols][3];

        /*for(int i=0; i< rows; i++){
            for (int j = 0; j < cols; j++) {
               for(int color = 0; color < 3; color++) {

                  if((dir1[i][j][color]>=-22.5 && dir1[i][j][color]<22.5)|| (dir1[i][j][color]>=157.5 && dir1[i][j][color]<-157.5))
                      dir[i][j][color]=0;   //borde vertical

                  if((dir1 [i][j][color]>=22.5 && dir1[i][j][color]<67.5) || (dir1[i][j][color]>=-157.5 && dir1[i][j][color]< -112.5))
                      dir[i][j][color]= 45;

                  if((dir1[i][j][color]>=67.5 && dir1 [i][j][color]<112.5) || (dir1 [i][j][color]>= -112.5 && dir1[i][j][color]< -67.5))
                      dir[i][j][color]= 90;

                  if((dir1[i][j][color]>=112.5 && dir1[i][j][color]<157.5) || (dir1[i][j][color]>= -67.5 && dir1[i][j][color]< -22.5))
                      dir[i][j][color]= 135;
                  System.out.println(dir1[i][j][color]);
                  }
                  
            }
        }*/

        for(int i=0; i< rows; i++){
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {
                    if((dir1[i][j][color]>-22.5 && dir1[i][j][color]<=22.5)|| (dir1[i][j][color]>157.5 && dir1[i][j][color]<=180)  || (dir1[i][j][color]>=-180 && dir1[i][j][color]<=-157)){
                        dir[i][j][color]=0;
                    } else if((dir1 [i][j][color]>22.5 && dir1[i][j][color]<=67.5) || (dir1[i][j][color]>-157.5 && dir1[i][j][color]<= -112.5)){
                        dir[i][j][color]= 45;
                    } else if((dir1[i][j][color]>67.5 && dir1 [i][j][color]<=112.5) || (dir1 [i][j][color]> -112.5 && dir1[i][j][color]<= -67.5)){
                        dir[i][j][color]= 90;
                    } else if((dir1[i][j][color]>112.5 && dir1[i][j][color]<=157.5) || (dir1[i][j][color]> -67.5 && dir1[i][j][color]<= -22.5)){
                        dir[i][j][color]= 135;
                    }
                }                  
            }
        }

        double  [][][] gn = new double [ rows ][ cols ][3];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {

                  if(i-1>0 && j-1 >0 && i+1 < rows && j+1< cols){
                        if (dir[i][j][color] == 0 ){ // Se trata de un borde vertical
                           if( mag[ i ][ j ][color] < mag[ i ][ j -1 ][color] || mag[ i ][ j ][color] < mag[ i ][ j+1 ][color]){
                               gn[i][j][color]=0;
                           }
                           else {
                            gn[i][j][color]=mag[i][j][color] ;
                           }
                        }
                        else if (dir[i][j][color] == 45){ // Se trata de un borde a 45
                           if( mag[ i ][ j ][color] < mag[ i -1 ][ j +1 ][color] || mag[ i ][ j ][color] < mag[ i +1][ j-1 ][color]){
                               gn[i][j][color]=0;
                           }
                           else {
                            gn[i][j][color]=mag[i][j][color] ;
                           }
                        }
                        else if (dir[i][j][color] == 90) { // Se trata de un borde horizontal
                           if( mag[ i ][ j ][color] < mag[ i -1][ j  ][color] || mag[ i ][ j ][color] < mag[ i +1][ j ][color]){
                               gn[i][j][color]=0;
                           }
                           else {
                            gn[i][j][color]=mag[i][j][color] ;
                           }
                        }
                        else if(dir[i][j][color] == 135) { //Se trata de un borde a -45
                           if( mag[ i ][ j ][color] < mag[ i -1][ j -1 ][color] || mag[ i ][ j ][color] < mag[ i+1 ][ j+1 ][color]){
                               gn[i][j][color]=0;
                           }
                           else {
                            gn[i][j][color]=mag[i][j][color] ;
                           }
                        }
                    } 
                    else gn[i][j][color]=mag[i][j][color];


                }
            }
        }
        return gn;
    }

}