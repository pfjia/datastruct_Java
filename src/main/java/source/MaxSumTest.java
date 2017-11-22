package source;

public final class MaxSumTest
{
    /**
     * Cubic maximum contiguous subsequence sum algorithm.
     */
    public static int maxSubSum1( int [ ] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
            for( int j = i; j < a.length; j++ )
            {
                int thisSum = 0;

                for( int k = i; k <= j; k++ )
                    thisSum += a[ k ];

                if( thisSum > maxSum )
                    maxSum   = thisSum;
            }

        return maxSum;
    }


    /**
     * Quadratic maximum contiguous subsequence sum algorithm.
     */
    public static int maxSubSum2( int [ ] a )
    {
        int maxSum = 0;

        for( int i = 0; i < a.length; i++ )
        {
            int thisSum = 0;
            for( int j = i; j < a.length; j++ )
            {
                thisSum += a[ j ];

                if( thisSum > maxSum )
                    maxSum = thisSum;
            }
        }

        return maxSum;
    }

    /**
     * Recursive maximum contiguous subsequence sum algorithm.
     * Finds maximum sum in subarray spanning a[left..right].
     * Does not attempt to maintain actual best sequence.
     */
    private static int maxSumRec( int [ ] a, int left, int right )
    {
        if( left == right )  // Base case
            if( a[ left ] > 0 )
                return a[ left ];
            else
                return 0;

        int center = ( left + right ) / 2;
        int maxLeftSum  = maxSumRec( a, left, center );
        int maxRightSum = maxSumRec( a, center + 1, right );

        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for( int i = center; i >= left; i-- )
        {
            leftBorderSum += a[ i ];
            if( leftBorderSum > maxLeftBorderSum )
                maxLeftBorderSum = leftBorderSum;
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for( int i = center + 1; i <= right; i++ )
        {
            rightBorderSum += a[ i ];
            if( rightBorderSum > maxRightBorderSum )
                maxRightBorderSum = rightBorderSum;
        }

        return max3( maxLeftSum, maxRightSum,
                     maxLeftBorderSum + maxRightBorderSum );
    }

    /**
     * Driver for divide-and-conquer maximum contiguous
     * subsequence sum algorithm.
     */
    public static int maxSubSum3( int [ ] a )
    {
        return maxSumRec( a, 0, a.length - 1 );
    }

    /**
     * Return maximum of three integers.
     */
    private static int max3( int a, int b, int c )
    {
        return a > b ? a > c ? a : c : b > c ? b : c;
    }

    /**
     * Linear-time maximum contiguous subsequence sum algorithm.
     */
    public static int maxSubSum4( int [ ] a )
    {
        int maxSum = 0, thisSum = 0;

        for( int j = 0; j < a.length; j++ )
        {
            thisSum += a[ j ];

            if( thisSum > maxSum )
                maxSum = thisSum;
            else if( thisSum < 0 )
                thisSum = 0;
        }

        return maxSum;
    }

    /**
     * Simple test program.
     */
    public static void main( String [ ] args )
    {
        int a[ ] = { 4, -3, 5, -2, -1, 2, 6, -2 };
        int maxSum;

        maxSum = maxSubSum1( a );
        System.out.println( "Max sum is " + maxSum );
        maxSum = maxSubSum2( a );
        System.out.println( "Max sum is " + maxSum );
        maxSum = maxSubSum3( a );
        System.out.println( "Max sum is " + maxSum );
        maxSum = maxSubSum4( a );
        System.out.println( "Max sum is " + maxSum );
    }
}

