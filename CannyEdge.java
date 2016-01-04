public class CannyEdge {

    /*
     *  Hysteresis
     *  @param double [][][]gn, double th, double tl
     *  @return int [][][]
    */
    public int[][][] hysteresis(double [][][] gn, double th, double tl ) {
        int rows = gn[0].length;
        int cols = gn[0][0].length;

        int [][][]edges = new int[3][ rows ][ cols ];

        int []posX = {1,  1, 1, 0,  0, -1, -1, -1};
        int []posY = {1, -1, 0, 1, -1, -1,  1,  0};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {
                    /* If gn has a hight value is edge */
                    if( gn[color][i][j] >= th ) {
                        edges[color][i][j] = 255;
                        /* Looking for neighbors candidares to be edge */
                        for (int k = 0; k < 8; k++) {
                            try{
                                if( gn[color][ i+posX[k] ][ j+posY[k] ] >= tl ) {
                                    edges[color][ i+posX[k] ][ j+posY[k] ] = 255;
                                }
                            }catch( Exception e ){
                            }
                        }
                    }
                }
            }
        }

        return edges;
    }

}
