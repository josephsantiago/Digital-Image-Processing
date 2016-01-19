public class CannyEdge {

    /*
     *  Hysteresis
     *  @param double [][][]gn, double th, double tl
     *  @return int [][][]
    */
    public int[][][] hysteresis(double [][][] gn, double th, double tl ) {
        int rows = gn[0].length;
        int cols = gn[0][0].length;

        int [][][]edges = new int[ rows ][ cols ][3];

        int []posX = {1,  1, 1, 0,  0, -1, -1, -1};
        int []posY = {1, -1, 0, 1, -1, -1,  1,  0};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for(int color = 0; color < 3; color++) {
                    /* If gn has a hight value is edge */
                    if( gn[i][j][color] >= th ) {
                        edges[i][j][color] = 255;
                        /* Looking for neighbors candidares to be edge */
                        for (int k = 0; k < 8; k++) {
                            try{
                                if( gn[ i+posX[k] ][ j+posY[k] ][color] >= tl ) {
                                    edges[ i+posX[k] ][ j+posY[k] ][color] = 255;
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
