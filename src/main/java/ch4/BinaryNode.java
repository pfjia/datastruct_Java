package ch4;

/**
 * @author pfjia
 * @since 2017/11/22 19:43
 */
public class BinaryNode<E> {
    public BinaryNode(E element) {
        this(element, null, null);
    }


    public BinaryNode(E element, BinaryNode<E> left, BinaryNode<E> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }


    public E element;
    public BinaryNode<E> left;
    public BinaryNode<E> right;
}