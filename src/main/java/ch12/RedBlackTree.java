package ch12;


import java.util.Arrays;
import java.util.List;


/**
 * @author pfjia
 * @since 2017/11/22 21:31
 */
public class RedBlackTree<E extends Comparable<? super E>> {
	/**
	 * 右链指向真正的根
	 */
	private RedBlackNode<E> head;
	/**
	 * null节点,否则许多操作都要判null
	 */
	private RedBlackNode<E> nullNode;


	public RedBlackTree() {
		nullNode = new RedBlackNode<>(null);
		nullNode.left = nullNode.right = nullNode;
		head = new RedBlackNode<E>(null);
		head.left = head.right = nullNode;
	}


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
	 * 1.遇到带有两个红儿子的节点
	 * 2.插入一片树叶
	 * Internal routine that is called during an insertion if a node has two red children.
	 * Performs flip(颜色翻转) and rotations(父子节点旋转).
	 * @param item the item being inserted.
	 */
	private void handleReorient(E item) {
		// Do the color flip
		// 不论情形1,2,current.color都设置为RED
		current.color = RedBlackNode.Color.RED;
		current.left.color = RedBlackNode.Color.BLACK;
		current.right.color = RedBlackNode.Color.BLACK;

		// Have to rotate
		// 若父节点为红节点,则旋转
		// 不论是"之字形"还是"一字形"旋转后,current=BLACK,parent=grand=RED
		// 以下代码修改了current和grand,而parent已经是RED,无需修改
		if (parent.color == RedBlackNode.Color.RED) {
			grand.color = RedBlackNode.Color.RED;
			// 若if成立,则为"之字形",current与parent旋转,传参为grand(祖父节点)
			if ((compare(item, grand) < 0) != (compare(item, parent) < 0)) {
				// start dbl rotate
				parent = rotate(item, grand);
			}
			// 不论"之字形"第二次旋转还是"一字形"第一次旋转,都是grand(祖父节点)与其子节点旋转,传参为great(曾祖节点)
			current = rotate(item, great);
			current.color = RedBlackNode.Color.BLACK;
		}
		// Make root black
		head.right.color = RedBlackNode.Color.BLACK;
	}


	/**
	 * Insert into the tree
	 * @param item the item to insert.
	 */
	public void insert(E item) {
		current = parent = grand = head;
		nullNode.element = item;

		// 循环退出条件:current=nullNode
		while (compare(item, current) != 0) {
			great = grand;
			grand = parent;
			parent = current;
			current = compare(item, current) < 0 ? current.left : current.right;
			// Check if two red children; fix if so
			if (current.left.color == RedBlackNode.Color.RED
					&& current.right.color == RedBlackNode.Color.RED) {
				handleReorient(item);
			}
		}

		// Insertion fails if already present
		if (current != nullNode) {
			return;
		}
		current = new RedBlackNode<E>(item, nullNode, nullNode);

		// Attach to parent
		if (compare(item, parent) < 0) {
			parent.left = current;
		} else {
			parent.right = current;
		}
		handleReorient(item);
	}


	private static class RedBlackNode<E> {

		private enum Color {
			/**
			 * 红黑树中红节点
			 */
			RED,
			/**
			 * 红黑树中黑节点
			 */
			BLACK;
		}


		private E element;
		private RedBlackNode<E> left;
		private RedBlackNode<E> right;
		private Color color;


		public RedBlackNode(E element) {
			this(element, null, null);
		}


		public RedBlackNode(E element, RedBlackNode<E> left,
				RedBlackNode<E> right) {
			this.element = element;
			this.left = left;
			this.right = right;
			color = Color.BLACK;
		}


		@Override
		public String toString() {
			return "RedBlackNode{" + "element=" + element + ", color="
					+ color.name().toLowerCase() + '}';
		}
	}


	public static void main(String[] args) {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		List<Integer> integerList = Arrays.asList(10, 85, 15, 70, 20, 60, 30,
				50, 65, 80, 90, 40, 5, 55);
		for (Integer integer : integerList) {
			tree.insert(integer);
		}
	}
}
