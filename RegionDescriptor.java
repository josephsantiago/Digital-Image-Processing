public class RegionDescriptor {

    /* First index is for the channel RGB */
    public double [][] DRN;

    /* Return the arithmetic mean from DRN for each color channel */
    public double[] mean() {
        double []sum = {0, 0, 0};

        for (int i = 0; i < this.DRN.length; i++) {
            for (int color = 0; color < 3; color ++) {
                sum[ color ] += this.DRN[ color ][ i ];
            }
        }

        for (int color = 0; color < 3; color ++) {
            sum[ color ] /= this.DRN.length;
        }

        return sum;
    }

    /* Return the standard deviation from DRN for each color channel */
    public double[] standardDeviation() {
        double []mean = mean();
        double []sum = {0, 0, 0};

        for (int i = 0; i < this.DRN.length; i++) {
            for (int color = 0; color < 3; color ++) {
                sum[ color ] += Math.pow( this.DRN[ color ][ i ] - mean[ color ], 2 );
            }
        }

        for (int color = 0; color < 3; color ++) {
            sum[ color ] = Math.sqrt( sum[ color] / this.DRN.length );
        }

        return sum;
    }

    /* Return the number of zero Crossings from DRN for each color channel */
    public int[] zeroCrossings() {
        double []mean = mean();
        int []cont = {0, 0, 0};

        for (int i = 0; i < this.DRN.length; i++) {
            for (int color = 0; color < 3; color ++) {
                if ( this.DRN[ color ][ i ] > mean[ color ] ) {
                    cont[ color ]++;
                }
            }
        }

        return cont;
    }

    /* Return the area index from DRN for each color channel */
    public double[] areaIndex() {
        double []mean = mean();
        double []sum = {0, 0, 0};

        for (int i = 0; i < this.DRN.length; i++) {
            for (int color = 0; color < 3; color ++) {
                if ( this.DRN[ color ][ i ] - mean[ color ] > 0 ) {
                    sum[ color ] += this.DRN[ color ][ i ] - mean[ color ];
                }
            }
        }

        for (int color = 0; color < 3; color ++) {
            sum[ color ] /= this.DRN.length * mean[ color ];
        }

        return sum;
    }

    /* Return the Roughness index from DRN for each color channel */
    public double[] RoughnessIndex() {
        double []mean = mean();
        double []sum = {0, 0, 0};

        for (int i = 0; i < this.DRN.length - 1; i++) {
            for (int color = 0; color < 3; color ++) {
                    sum[ color ] += this.DRN[ color ][ i ] - this.DRN[ color ][ i+1 ];
            }
        }

        for (int color = 0; color < 3; color ++) {
            sum[ color ] /= this.DRN.length * mean[ color ];
        }

        return sum;
    }
    
}
