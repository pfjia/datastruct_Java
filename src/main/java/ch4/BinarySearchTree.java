package ch4;


/**
 * @author pfjia
 * @version v4
 * @since 2017/11/16 8:44
 */
public class BinarySearchTree<E extends Comparable<? super E>> {
	private static class BinaryNode<E> {
		public BinaryNode(E element) {
			this(element, null, null);
		}


		public BinaryNode(E element, BinaryNode<E> left, BinaryNode<E> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}


		private E element;
		private BinaryNode<E> left;
		private BinaryNode<E> right;
	}


	private BinaryNode<E> root;


	public BinarySearchTree() {
		root = null;
	}


	public void makeEmpty() {

	}


	public boolean isEmpty() {
		return root == null;
	}


	public boolean contains(E x) {
		return contains(x, root);
	}


	public E findMin() {
		if (isEmpty()) {
			return null;
		}
		return findMin(root).element;
	}


	public E findMax() {
		return null;
	}


	public void insert(E x) {
		root = insert(x, root);
	}


	public void remove(E x) {
		root = remove(x, root);
	}


    /**
     * Print the tree contents in sorted order.
     */
	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty tree");
		} else {
			printTree(root);
		}
	}


	/**
	 * Internal method to find an item in a subtree.
	 * @param x is item to search for.
	 * @param root the node that roots the subtree
	 * @return true if the item is found;false otherwise
	 */
	private boolean contains(E x, BinaryNode<E> root) {
		if (x == null) {
			return false;
		}
		if (root == null) {
			return false;
		}
		int compareResult = root.element.compareTo(x);
		if (compareResult < 0) {
			return contains(x, root.left);
		} else if (compareResult == 0) {
			return true;
		} else {
			return contains(x, root.right);
		}
	}


	/**
	 * Internal method to find the smallest item in a subtree
	 * @param root the node that roots the subtree
	 * @return node containing the smallest item.
	 */
	private BinaryNode<E> findMin(BinaryNode<E> root) {
		if (root == null) {
			return null;
		} else if (root.left == null) {
			return root;
		}
		return findMax(root.left);
	}


	/**
	 * Internal method to find the largest item in a subtree
	 * @param root the node that roots the subtree
	 * @return node containing the largest item.
	 */
	private BinaryNode<E> findMax(BinaryNode<E> root) {
		if (root == null) {
			return null;
		}
		while (root.right != null) {
			root = root.right;
		}
		return root;
	}


	/**
	 * Internal method to insert into a subtree
	 * @param x the item to insert
	 * @param root the node that roots the subtree 
	 * @return the new root of the subtree
	 */
	private BinaryNode<E> insert(E x, BinaryNode<E> root) {
		// x==null,什么都不做
		if (x == null) {
			return root;
		}
		if (root == null) {
			return new BinaryNode<E>(x, null, null);
		}

		int compareResult = x.compareTo(root.element);
		if (compareResult == 0) {
			return root;
		} else if (compareResult < 0) {
			root.left = insert(x, root.left);
		} else {
			root.right = insert(x, root.right);
		}
		return root;
	}


	private BinaryNode<E> remove(E x, BinaryNode<E> root) {
		if (x == null) {
			return null;
		}
		if (root == null) {
			return root;
		}
		int compareResult = x.compareTo(root.element);
		if (compareResult == 0) {
			// two children
			if (root.left != null && root.right != null) {
				// 寻找右子树的最小数据结点
				BinaryNode<E> minInRight = findMin(root.right);
				// 使用右子树的最小数据结点的数据替换子树根的数据
				root.element = minInRight.element;
				// 递归删除该右子树的最小数据结点
				root.right = remove(minInRight.element, root.right);
			} else {
				root = root.left != null ? root.left : root.right;
			}
		} else if (compareResult < 0) {
			root.left = remove(x, root.left);
		} else {
			root.right = remove(x, root.right);
		}
		return root;
	}


    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
	private void printTree(BinaryNode<E> t) {
	    if (t!=null){
	        printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
	}

    /**
     * Interanl method to compute height of a subtree.
     * @param t the node taht roots the subtree.
     * @return
     */
	private int height(BinaryNode<E> t){
	    if (t==null){
	        return -1;
        }
        return Math.max(height(t.left),height(t.right))+1;
    }

}
