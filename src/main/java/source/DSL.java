package source;// Deterministic skip list class class
//
// CONSTRUCTION: with INFINITY object that is used as a sentinel.
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// bool contains( x )     --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a deterministic skip list.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DSL<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the DSL.
     * @param inf the largest Comparable.
     */
    public DSL( AnyType inf )
    {
        infinity = inf;
        bottom = new SkipNode<AnyType>( null );
        bottom.right = bottom.down = bottom;
        tail   = new SkipNode<AnyType>( infinity );
        tail.right = tail;
        header = new SkipNode<AnyType>( infinity, tail, bottom );
    }

    /**
     * Insert into the DSL.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        SkipNode<AnyType> current = header;

        bottom.element = x;
        while( current != bottom )
        {
            while( current.element.compareTo( x ) < 0 )
                current = current.right;

            // If gap size is 3 or at bottom level and
            // must insert, then promote middle element
            if( current.down.right.right.element.compareTo( current.element ) < 0 )
            {
                current.right = new SkipNode<AnyType>( current.element, current.right,
                                              current.down.right.right );
                current.element = current.down.right.element;
            }
            else
                current = current.down;
        }

        // Raise height of DSL if necessary
        if( header.right != tail )
            header = new SkipNode<AnyType>( infinity, tail, header );
    }

    /**
     * Remove from the DSL. Unimplemented.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        System.out.println( "Sorry, remove unimplemented" );
    }

    /**
     * Find the smallest item in the DSL.
     * @return smallest item, or throw UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        SkipNode<AnyType> current = header;
        while( current.down != bottom )
            current = current.down;

        return current.element;
    }

    /**
     * Find the largest item in the DSL.
     * @return the largest item, or throw UnderflowException if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        SkipNode<AnyType> current = header;
        for( ; ; )
             if( current.right.right != tail )
                 current = current.right;
             else if( current.down != bottom )
                 current = current.down;
             else
                 return current.element;
    }

    /**
     * Find an item in the DSL.
     * @param x the item to search for.
     * @return the true if not found.
     */
    public boolean contains( AnyType x )
    {
        SkipNode<AnyType> current = header;

        bottom.element = x;
        for( ; ; )
        {
            int compareResult = x.compareTo( current.element );
            if( compareResult < 0 )
                current = current.down;
            else if( compareResult > 0 )
                current = current.right;
            else
                return current != bottom;
        }    
    }

    /**
     * Make the DSL logically empty.
     */
    public void makeEmpty( )
    {
        header.right = tail;
        header.down = bottom;
    }

    /**
     * Test if the DSL is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return header.right == tail && header.down == bottom;
    }

    /**
     * Print the DSL.
     */
    private void printList( )
    {
        SkipNode<AnyType> current = header;

        while( current.down != bottom )
             ;

        while( current.right != tail )
        {
            System.out.println( current.element );
            current = current.right;
        }
    }

    private static class SkipNode<AnyType>
    {
            // Constructors
        SkipNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        SkipNode( AnyType theElement, SkipNode<AnyType> rt, SkipNode<AnyType> dt )
        {
            element  = theElement;
            right    = rt;
            down     = dt;
        }

        AnyType           element;  // The data in the node
        SkipNode<AnyType> right;    // Right link 
        SkipNode<AnyType> down;     // Down link
    }
    
      /** The DSL header. */
    private SkipNode<AnyType> header;
    private AnyType infinity;
    private SkipNode<AnyType> bottom = null;
    private SkipNode<AnyType> tail   = null;
   

        // Test program
    public static void main( String [ ] args )
    {
        DSL<Integer> t = new DSL<Integer>( 100000000 );
        final int NUMS = 400000;
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( i );

        if( NUMS < 40 )
            t.printList( );
        if( t.findMin( ) != 1 || t.findMax( ) != NUMS - 1 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 1; i < NUMS; i++ )
             if( !t.contains( i ) )
                 System.out.println( "Find error1!" );
        if( t.contains( 0 ) )
            System.out.println( "Find error2!" );
        if( t.contains( NUMS + 10 ) )
            System.out.println( "Find error2!" );
    }
}
