package ch12;


/**
 * @author pfjia
 * @since 2017/11/22 21:31
 */
public class RedBlackTree<E extends Comparable<? super E>> {
	private static final int BLACk = 1;
	private static final int RED = 0;
	/**
	 * 存储关键字-∞和一个指向真正的根的右链
	 */
	private RedBlackNode<E> head;
	private RedBlackNode<E> nullNode;


	/**
	 * Internal routine that performs a single or double rotation.
	 * Because the result is attached to the parent, there are four cases.
	 * Called by handleReorient.
	 * @param item the item in handleReorient.
	 * @param parent the parent of the root of the rotated subtree.
	 * @return the root of the rotated subtree.
	 */
	private RedBlackNode<E> rotate(E item, RedBlackNode<E> parent) {
		if (compare(item, parent) < 0) {
			return parent.left = compare(item, parent.left) < 0
					? rotateWithLeftChild(parent.left)
					: rotateWithRightChild(parent.left);
		} else {
			return parent.right = compare(item, parent.right) < 0
					? rotateWithLeftChild(parent.right)
					: rotateWithRightChild(parent.right);
		}
	}


	/**
	 * Rotate binary tree node with left child.
	 * @param t
	 * @return 
	 */
	private RedBlackNode<E> rotateWithLeftChild(RedBlackNode<E> t) {
		RedBlackNode<E> left = t.left;
		t.left = left.right;
		left.right = t;
		return left;
	}


	/**
	 * Rotate binary tree node with right child.
	 * @param t
	 * @return
	 */
	private RedBlackNode<E> rotateWithRightChild(RedBlackNode<E> t) {
		RedBlackNode<E> right = t.right;
		t.right = right.left;
		right.left = t;
		return right;
	}


	/**
	 * Compare item and t.element,using compareTo,with caveat that if t is header, then item is always larger.
	 * This routine is called if it is possible that t is header.
	 * If it is not possible for t to be header,use compareTo directly.
	 * @param item
	 * @param t
	 * @return
	 */
	private final int compare(E item, RedBlackNode<E> t) {
		if (t == head) {
			return 1;
		} else {
			return item.compareTo(t.element);
		}
	}


	// used in insert routine and its helpers
	/**
	 * 当前节点
	 */
	private RedBlackNode<E> current;
	/**
	 * 父节点
	 */
	private RedBlackNode<E> parent;
	/**
	 * 祖父节点
	 */
	private RedBlackNode<E> grand;
	/**
	 * 曾祖父节点
	 */
	private RedBlackNode<E> great;


	/**
	 * Internal routine that is called during an insertion if a node has two red children.
	 * Performs flip(颜色翻转) and rotations(父子节点旋转).
	 * @param item the item being inserted.
	 */
	private void handleReorient(E item) {
		// Do the color flip
		current.color = RED;
		current.left.color = BLACk;
		current.right.color = BLACk;

		// Have to rotate
		if (parent.color == RED) {
			grand.color = RED;
			if ((compare(item, grand) < 0) != (compare(item, parent) > 0)) {
				// start dbl rotate
				parent = rotate(item, grand);
			}
			current = rotate(item, great);
			current.color = BLACk;
		}
		// Make root black
		head.right.color = BLACk;
	}


	/**
	 * Insert into the tree
	 * @param item the item to insert.
	 */
	public void insert(E item) {
		current = parent = grand = head;
		nullNode.element = item;

		while (compare(item, current) != 0) {
			grand = grand;
			grand = parent;
			parent = current;
			current=compare(item,current)<0?current.left:current.right;
			//Check  if two red children; fix if so
            if (current.left.color==RED&&current.right.color==RED){
                handleReorient(item);
            }
		}

		//Insertion fails if already present
        if (current!=nullNode){
		    return;
        }
        current=new RedBlackNode<E>(item,nullNode,nullNode);
        // Attach to parent
        if (compare(item,parent)<0){
            parent.left=current;
        }else {
            parent.right=current;
        }
        handleReorient(item);
	}


	private static class RedBlackNode<E> {
		private E element;
		private RedBlackNode<E> left;
		private RedBlackNode<E> right;
		private int color;


		public RedBlackNode(E element) {
			this(element, null, null);
		}


		public RedBlackNode(E element, RedBlackNode<E> left,
				RedBlackNode<E> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}
	}
}
