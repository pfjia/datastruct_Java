package ch4;


import java.util.LinkedList;
import java.util.Queue;


/**
 * @author pfjia
 * @since 2017/11/17 20:59
 */
public class AvlTree<E extends Comparable<? super E>> {
	private static final int ALLOWED_IMBALANCE = 1;
	private AvlNode<E> head;


	public AvlTree() {

	}


	private static class AvlNode<E> {
		private E data;
		private AvlNode<E> left;
		private AvlNode<E> right;
		/**子树高度,若this==null,height=-1;若left==null&&right==null,height=0(规定)*/
		private int height;


		public AvlNode(E data, AvlNode<E> left, AvlNode<E> right) {
			this.data = data;
			this.left = left;
			this.right = right;
			this.height = 0;
		}


		private AvlNode(E data) {
			this(data, null, null);
		}


		public int refreshHeight() {
			int leftHeight = left == null ? -1 : left.height;
			int rightHeight = right == null ? -1 : right.height;
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}


	public boolean insert(E x) {
		head = insert(x, head);
		return true;
	}


	public void print() {
		if (head == null) {
			System.out.println("null");
		} else {
			int headHeight = head.height;
			int totalSpace = (int) (Math.pow(2, headHeight + 1) - 1);
			Queue<AvlNode<E>> queue = new LinkedList<>();
			queue.add(head);
			queue.add(null);
			while (!queue.isEmpty()) {
				AvlNode<E> node = queue.remove();
				if (node == null) {
					System.out.println();
					continue;
				}
				int height = node.height;
				printSpace((int) Math.pow(2, height + 1));
				System.out.print(node.data);
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
				// 使用null作为层的分界符
				queue.add(null);
			}
		}
	}


	public void printNotProper() {
		if (head == null) {
			System.out.println("null");
		} else {
			int headHeight = head.height;
			Queue<AvlNode<E>> queue = new LinkedList<>();
			queue.add(head);
			queue.add(null);
			while (!queue.isEmpty()) {
				AvlNode<E> node = queue.remove();
				if (node == null) {
					System.out.println();
					continue;
				}
				System.out.printf("%-5d", node.data);
				if (node.left != null) {
					queue.add(node.left);
				}
				if (node.right != null) {
					queue.add(node.right);
				}
				// 使用null作为层的分界符
				queue.add(null);
			}
		}
	}


	public void printSpace(int num) {
		for (int i = 0; i < num; i++) {
			System.out.print(" ");
		}
	}


	/**
	 * 
	 * @param t 
	 * @return the height of node t ,or -1, if null
	 */
	private int height(AvlNode<E> t) {
		return t == null ? -1 : t.height;
	}


	/**
	 * Internal method to insert a subtree
	 * @param x the item to insert
	 * @param t the node that roots the subtree
	 * @return the new root of the subtree
	 */
	private AvlNode<E> insert(E x, AvlNode<E> t) {
		// 递归出口
		if (t == null) {
			return new AvlNode<>(x);
		}
		int compareResult = x.compareTo(t.data);
		if (compareResult == 0) {
			return t;
		} else if (compareResult < 0) {
			t.left = insert(x, t.left);
		} else {
			t.right = insert(x, t.right);
		}
		return balance(t);
	}


	/**
	 * Internal method to remove from a subtree
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<E> remove(E x, AvlNode<E> t) {
		// Item not found; do nothing
		if (t == null) {
			return t;
		}

		int compareResult = x.compareTo(t.data);
		if (compareResult < 0) {
			t.left = remove(x, t.left);
		} else if (compareResult == 0) {
            // Two children
			if (t.left != null && t.right != null) {
				AvlNode<E> min = findMin(t.right);
				t.data = min.data;
				t.right = remove(t.data, t.right);
			} else {
				t = t.left != null ? t.left : t.right;
			}
		} else {
			t.right = remove(x, t.right);
		}
		return balance(t);
	}


	private AvlNode<E> findMin(AvlNode<E> t) {
		if (t == null) {
			return null;
		}
		while (t.left != null) {
			t = t.left;
		}
		return t;
	}


	/**
	 * Assume t is either balanced or within one of being balanced.
	 * @param t
	 */
	private AvlNode<E> balance(AvlNode<E> t) {
		if (t == null) {
			return t;
		}
		// 判断是否需要平衡
		int leftHeight = height(t.left);
		int rightHeight = height(t.right);

		if (leftHeight - rightHeight > ALLOWED_IMBALANCE) {
			// 判断是左-左旋转还是左-右旋转
			int leftLeftHeight = height(t.left.left);
			int leftRightHeight = height(t.left.right);
			// 删除时可能出现==的情况,此时需要单旋转,双旋转反而不能平衡
			// 左-左旋转
			if (leftLeftHeight >= leftRightHeight) {
				t = rotateWithLeftChild(t);
			}
			// 左-右旋转
			else if (leftRightHeight > leftLeftHeight) {
				t = doubleWithLeftChild(t);
			}
		}

		else if (rightHeight - leftHeight > ALLOWED_IMBALANCE) {
			// 判断是右-右还是右-左
			int rightRightHeight = height(t.right.right);
			int rightLeftHeight = height(t.right.left);
			// 右-右旋转
			if (rightRightHeight >= rightLeftHeight) {
				t = rotateWithRightChild(t);
			}
			// 右-左旋转
			else if (rightLeftHeight > rightRightHeight) {
				t = doubleWithRightChild(t);
			}
		}
		t.height = Math.max(leftHeight, rightHeight) + 1;
		return t;
	}


	/**
	 * Rotate binary tree node with left child.
	 * For AVL trees,this is a single rotation for case 1.
	 * Update heights,then return new root;
	 * @param k2
	 * @return
	 */
	private AvlNode<E> rotateWithLeftChild(AvlNode<E> k2) {
		AvlNode<E> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		// 修改height
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		return k1;
	}


	private AvlNode<E> rotateWithRightChild(AvlNode<E> t) {
		AvlNode<E> right = t.right;
		t.right = right.left;
		right.left = t;
		// 修改height
		// 1.若t.left.height与t.right.height相差2(对应右-右旋转),此时t的高度改变,t.right高度未变
		// 2.若t.left.height与t.right.height相差1(对应左-右旋转的第一次旋转),此时t的高度改变,t.right高度也变化
		// 为了适应上述两种情况,同时修改t.height和t.right.height
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		right.height = Math.max(height(right.left), height(right.right)) + 1;
		return right;
	}


	/**
	 * Double rotate binary tree node:first left child with its right child;
	 * then node k3 with new left child.
	 * For AVL trees , this is a double rotation for case 2.
	 * Update heights ,the return new root.
	 * @param k3 
	 * @return the new root of the subtree.
	 */
	private AvlNode<E> doubleWithLeftChild(AvlNode<E> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}


	private AvlNode<E> doubleWithRightChild(AvlNode<E> t) {
		t.right = rotateWithLeftChild(t.right);
		return rotateWithRightChild(t);
	}


	public static void main(String[] args) {
		AvlTree<Integer> avlTree = new AvlTree<>();
		avlTree.insert(3);
		avlTree.insert(2);
		avlTree.insert(1);
		avlTree.insert(4);
		avlTree.insert(5);
		avlTree.printNotProper();
	}

}
