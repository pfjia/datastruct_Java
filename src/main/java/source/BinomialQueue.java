package source;// BinomialQueue class
//
// CONSTRUCTION: with no parameters or a single item
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// vod merge( rhs )       --> Absord rhs into this heap
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a binomial queue.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinomialQueue<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the binomial queue.
     */
    @SuppressWarnings("unchecked")
    public BinomialQueue( )
    {
        theTrees = new BinomialNode[ DEFAULT_TREES ];
        makeEmpty( );
    }

    /**
     * Construct with a single item.
     */
    @SuppressWarnings("unchecked")
    public BinomialQueue( AnyType item ) 
    {
        currentSize = 1;
        theTrees = new BinomialNode[ 1 ];
        theTrees[ 0 ] = new BinomialNode<AnyType>( item, null, null );
    }


    @SuppressWarnings("unchecked")
    private void expandTheTrees( int newNumTrees )
    {
        BinomialNode<AnyType> [ ] old = theTrees;
        int oldNumTrees = theTrees.length;
        
        theTrees = new BinomialNode[ newNumTrees ];
        for( int i = 0; i < oldNumTrees; i++ )
            theTrees[ i ] = old[ i ];
        for( int i = oldNumTrees; i < newNumTrees; i++ )
            theTrees[ i ] = null;
    }
    
    /**
     * Merge rhs into the priority queue.
     * rhs becomes empty. rhs must be different from this.
     * @param rhs the other binomial queue.
     */
    public void merge( BinomialQueue<AnyType> rhs )
    {
        if( this == rhs )    // Avoid aliasing problems
            return;

        currentSize += rhs.currentSize;
        
        if( currentSize > capacity( ) )
        {
            int newNumTrees = Math.max( theTrees.length, rhs.theTrees.length ) + 1;
            expandTheTrees( newNumTrees );
        }

        BinomialNode<AnyType> carry = null;
        for( int i = 0, j = 1; j <= currentSize; i++, j *= 2 )
        {
            BinomialNode<AnyType> t1 = theTrees[ i ];
            BinomialNode<AnyType> t2 = i < rhs.theTrees.length ? rhs.theTrees[ i ] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch( whichCase )
            {
              case 0: /* No trees */
              case 1: /* Only this */
                break;
              case 2: /* Only rhs */
                theTrees[ i ] = t2;
                rhs.theTrees[ i ] = null;
                break;
              case 4: /* Only carry */
                theTrees[ i ] = carry;
                carry = null;
                break;
              case 3: /* this and rhs */
                carry = combineTrees( t1, t2 );
                theTrees[ i ] = rhs.theTrees[ i ] = null;
                break;
              case 5: /* this and carry */
                carry = combineTrees( t1, carry );
                theTrees[ i ] = null;
                break;
              case 6: /* rhs and carry */
                carry = combineTrees( t2, carry );
                rhs.theTrees[ i ] = null;
                break;
              case 7: /* All three */
                theTrees[ i ] = carry;
                carry = combineTrees( t1, t2 );
                rhs.theTrees[ i ] = null;
                break;
            }
        }

        for( int k = 0; k < rhs.theTrees.length; k++ )
            rhs.theTrees[ k ] = null;
        rhs.currentSize = 0;
    }        

    /**
     * Return the result of merging equal-sized t1 and t2.
     */
    private BinomialNode<AnyType> combineTrees( BinomialNode<AnyType> t1, BinomialNode<AnyType> t2 )
    {
        if( t1.element.compareTo( t2.element ) > 0 )
            return combineTrees( t2, t1 );
        t2.nextSibling = t1.leftChild;
        t1.leftChild = t2;
        return t1;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * This implementation is not optimized for O(1) performance.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        merge( new BinomialQueue<AnyType>( x ) );
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or null, if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        return theTrees[ findMinIndex( ) ].element;
    }


    /**
     * Find index of tree containing the smallest item in the priority queue.
     * The priority queue must not be empty.
     * @return the index of tree containing the smallest item.
     */
    private int findMinIndex( )
    {
        int i;
        int minIndex;

        for( i = 0; theTrees[ i ] == null; i++ )
            ;

        for( minIndex = i; i < theTrees.length; i++ )
            if( theTrees[ i ] != null &&
                theTrees[ i ].element.compareTo( theTrees[ minIndex ].element ) < 0 )
                minIndex = i;

        return minIndex;
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or null, if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        int minIndex = findMinIndex( );
        AnyType minItem = theTrees[ minIndex ].element;

        BinomialNode<AnyType> deletedTree = theTrees[ minIndex ].leftChild;

        // Construct H''
        BinomialQueue<AnyType> deletedQueue = new BinomialQueue<AnyType>( );
        deletedQueue.expandTheTrees( minIndex + 1 );
        
        deletedQueue.currentSize = ( 1 << minIndex ) - 1;
        for( int j = minIndex - 1; j >= 0; j-- )
        {
            deletedQueue.theTrees[ j ] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[ j ].nextSibling = null;
        }

        // Construct H'
        theTrees[ minIndex ] = null;
        currentSize -= deletedQueue.currentSize + 1;

        merge( deletedQueue );
        
        return minItem;
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
        for( int i = 0; i < theTrees.length; i++ )
            theTrees[ i ] = null;
    }

    private static class BinomialNode<AnyType>
    {
            // Constructors
        BinomialNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinomialNode( AnyType theElement, BinomialNode<AnyType> lt, BinomialNode<AnyType> nt )
        {
            element     = theElement;
            leftChild   = lt;
            nextSibling = nt;
        }

        AnyType               element;     // The data in the node
        BinomialNode<AnyType> leftChild;   // Left child
        BinomialNode<AnyType> nextSibling; // Right child
    }

    private static final int DEFAULT_TREES = 1;

    private int currentSize;            // # items in priority queue
    private BinomialNode<AnyType> [ ] theTrees;  // An array of tree roots


    /**
     * Return the capacity.
     */
    private int capacity( )
    {
        return ( 1 << theTrees.length ) - 1;
    }

    public static void main( String [ ] args )
    {
        int numItems = 10000;
        BinomialQueue<Integer> h  = new BinomialQueue<Integer>( );
        BinomialQueue<Integer> h1 = new BinomialQueue<Integer>( );
        int i = 37;

        System.out.println( "Starting check." );

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            if( i % 2 == 0 )
                h1.insert( i );
            else
                h.insert( i );

        h.merge( h1 );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
                    
        System.out.println( "Check done." );
    }
}
