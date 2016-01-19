public class supresion_no_maximos {

    public double [][][] supresion (double [][][] mag, double [][][] dir ) {

        int rows = mag[0].length;
        int cols = mag[0][0].length;
        double  [][][] gn = new double [ rows ][ cols ][3];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {
                    int py1 = i + 1;
                    int py2 = i - 1;
                    int px1 = j + 1;
                    int px2 = j - 1;
                    //Borde en direccion Horizontal
                    if ( (dir[i][j][color] > 67.5 && dir[i][j][color] <= 112.5) || (dir[i][j][color] > -112.5 && dir[i][j][color] <= -67.5) ){
                        if( (px2 > 0 && px1 < cols) && (mag[i][j][color] < mag[i][j-1][color] || mag[i][j][color] < mag[i][j+1][color]) ){
                            gn[i][j][color]=0;          
                        }else{
                            gn[i][j][color]=mag[i][j][color];
                        }
                    }
                    //Borde en direccion Vertical
                    else if( (dir[i][j][color] > 157.5 && dir[i][j][color] <= 180) || (dir[i][j][color] > -22.5 && dir[i][j][color] <= -22.5) || (dir[i][j][color] >= -180 && dir[i][j][color] <= -157.5) ){
                        if( (py2 > 0 && py1 < rows) && (mag[i][j][color] < mag[i-1][j][color] || mag[i][j][color] < mag[i+1][j][color]) ){
                            gn[i][j][color]=0;          
                        }else{
                            gn[i][j][color]=mag[i][j][color];
                        }
                    }
                    //Borde en direccion de 45°
                    else if( (dir[i][j][color] > 22.5 && dir[i][j][color] <= 67.5) || (dir[i][j][color] > -157.5 && dir[i][j][color] <= -112.5) ){
                        if( (px2 > 0 && px1 < cols) && (py2 > 0 && py1 < rows) && (mag[i][j][color] < mag[i+1][j-1][color] || mag[i][j][color] < mag[i-1][j+1][color])){
                            gn[i][j][color]=0;          
                        }else{
                            gn[i][j][color]=mag[i][j][color];
                        }
                    }
                    //Borde en direccion de -45°
                    else if( (dir[i][j][color] > 112.5 && dir[i][j][color] <= 157.5) || (dir[i][j][color] > -67 && dir[i][j][color] <= -22.5) ){
                        if( (px2 > 0 && px1 < cols) && (py2 > 0 && py1 < rows) && (mag[i][j][color] < mag[i-1][j-1][color] || mag[i][j][color] < mag[i+1][j+1][color])){
                            gn[i][j][color]=0;          
                        }else{
                            gn[i][j][color]=mag[i][j][color];
                        }
                    }
              }
          }
      }

        return gn;
    }

}
