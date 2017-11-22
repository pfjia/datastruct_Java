package source;// AATree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is found
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AA-tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AATree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AATree( )
    {
        nullNode = new AANode<AnyType>( null, null, null );
        nullNode.left = nullNode.right = nullNode;
        nullNode.level = 0;
        root = nullNode;
    }

    /**
     * Insert into the tree.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        deletedNode = nullNode;
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return the smallest item or throw UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AANode<AnyType> ptr = root;

        while( ptr.left != nullNode )
            ptr = ptr.left;

        return ptr.element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item or throw UnderflowException if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AANode<AnyType> ptr = root;

        while( ptr.right != nullNode )
            ptr = ptr.right;

        return ptr.element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType x )
    {
        AANode<AnyType> current = root;
        nullNode.element = x;

        for( ; ; )
        {
        int compareResult = x.compareTo( current.element );
            
            if( compareResult < 0 )
                current = current.left;
            else if( compareResult > 0 ) 
                current = current.right;
            else if( current != nullNode )
                return true;
            else
                return false;
        }
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = nullNode;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == nullNode;
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AANode<AnyType> insert( AnyType x, AANode<AnyType> t )
    {
        if( t == nullNode )
            return new AANode<AnyType>( x, nullNode, nullNode );
            
    int compareResult = x.compareTo( t.element );
        
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            return t;

        t = skew( t );
        t = split( t );
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AANode<AnyType> remove( AnyType x, AANode<AnyType> t )
    {
        if( t != nullNode )
        {
            // Step 1: Search down the tree and set lastNode and deletedNode
            lastNode = t;
            if( x.compareTo( t.element ) < 0 )
                t.left = remove( x, t.left );
            else
            {
                deletedNode = t;
                t.right = remove( x, t.right );
            }

            // Step 2: If at the bottom of the tree and
            //         x is present, we remove it
            if( t == lastNode )
            {
                if( deletedNode == nullNode || x.compareTo( deletedNode.element ) != 0 )
                    return t;   // Item not found; do nothing
                deletedNode.element = t.element;
                t = t.right;
            }

            // Step 3: Otherwise, we are not at the bottom; rebalance
            else if( t.left.level < t.level - 1 || t.right.level < t.level - 1 )
            {
                if( t.right.level > --t.level )
                    t.right.level = t.level;
                t = skew( t );
                t.right = skew( t.right );
                t.right.right = skew( t.right.right );
                t = split( t );
                t.right = split( t.right );
            }
        }
        return t;
    }

    /**
     * Skew primitive for AA-trees.
     * @param t the node that roots the tree.
     * @return the new root after the rotation.
     */
    private AANode<AnyType> skew( AANode<AnyType> t )
    {
        if( t.left.level == t.level )
            t = rotateWithLeftChild( t );
        return t;
    }

    /**
     * Split primitive for AA-trees.
     * @param t the node that roots the tree.
     * @return the new root after the rotation.
     */
    private AANode<AnyType> split( AANode<AnyType> t )
    {
        if( t.right.right.level == t.level )
        {
            t = rotateWithRightChild( t );
            t.level++;
        }
        return t;
    }

    /**
     * Rotate binary tree node with left child.
     */
    private AANode<AnyType> rotateWithLeftChild( AANode<AnyType> k2 )
    {
        AANode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     */
    private AANode<AnyType> rotateWithRightChild( AANode<AnyType> k1 )
    {
        AANode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    private static class AANode<AnyType>
    {
            // Constructors
        AANode( AnyType theElement, AANode<AnyType> lt, AANode<AnyType> rt )
        {
            element = theElement;
            left    = lt;
            right   = rt;
            level   = 1;
        }

        AnyType         element;      // The data in the node
        AANode<AnyType> left;         // Left child
        AANode<AnyType> right;        // Right child
        int             level;        // Level
    }
    
    private AANode<AnyType> root;
    private AANode<AnyType> nullNode;
    
    private AANode<AnyType> deletedNode;
    private AANode<AnyType> lastNode;

        // Test program; should print min and max and nothing else
    public static void main( String [ ] args )
    {
        AATree<Integer> t = new AATree<Integer>( );
        final int NUMS = 40000;
        final int GAP  =   307;

        System.out.println( "Checking... (no bad output means success)" );

        t.insert( NUMS * 2 );
        t.insert( NUMS * 3 );
        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( i );
        System.out.println( "Inserts complete" );

        t.remove( t.findMax( ) );
        for( int i = 1; i < NUMS; i+= 2 )
            t.remove( i );
        t.remove( t.findMax( ) );
        System.out.println( "Removes complete" );


        if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 2; i < NUMS; i+=2 )
            if( !t.contains( i ) )
                System.out.println( "Error: find fails for " + i );

        for( int i = 1; i < NUMS; i+=2 )
            if( t.contains( i ) )
                System.out.println( "Error: Found deleted item " + i );
    }
}
