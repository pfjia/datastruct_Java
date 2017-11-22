package ch12;


import ch4.BinaryNode;

import java.util.Arrays;
import java.util.List;


/**
 * @author pfjia
 * @since 2017/11/22 19:42
 */
public class SplayTree<E extends Comparable<? super E>> {
	private BinaryNode<E> root;
	private BinaryNode<E> nullNode;
	/**For splay*/
	private BinaryNode<E> header = new BinaryNode<E>(null);
	/**Used between different inserts*/
	private BinaryNode<E> newNode = null;


	public SplayTree() {
		nullNode = new BinaryNode<E>(null);
		nullNode.left = nullNode.right = nullNode;
		root = nullNode;
	}


	/**
	 * Internal method to perform a top-down splay.
	 * The last accessed node becomes the new root.
	 * @param x the target item to splay around
	 * @param t the root of the subtree to splay.
	 * @return the subtee after the splay
	 */
	private BinaryNode<E> splay(E x, BinaryNode<E> t) {
		BinaryNode<E> leftTreeMax;
		BinaryNode<E> rightTreeMin;
		header.left = header.right = nullNode;
		leftTreeMax = rightTreeMin = header;

		// Guarantee a match
		nullNode.element = x;

		for (;;) {
			if (x.compareTo(t.element) < 0) {
				if (x.compareTo(t.left.element) < 0) {
					t = rotateWithLeftChild(t);
				}
				if (t.left == nullNode) {
					break;
				}
				// Link right
				rightTreeMin.left = t;
				rightTreeMin = t;
				t = t.left;
			} else if (x.compareTo(t.element) > 0) {
				// 右式-一字形
				if (x.compareTo(t.right.element) > 0) {
					t = rotateWithRightChild(t);
				}
				if (t.right == nullNode) {
					break;
				}
				leftTreeMax.right = t;
				leftTreeMax = t;
				t = t.right;
			} else {
				break;
			}
		}
		leftTreeMax.right = t.left;
		rightTreeMin.left = t.right;
		t.left = header.right;
		t.right = header.left;
		return t;
	}


    /**
     * Insert into the tree.
     * @param x the item to insert.
     */
	public void insert(E x) {
	    if (newNode==null){
	        newNode= new BinaryNode<>(null);
        }
        newNode.element=x;
	    if (isEmpty()){
	        newNode.left=newNode.right=nullNode;
	        root=newNode;
        }else {
	        root=splay(x,root);
	        if (x.compareTo(root.element)<0){
	            newNode.left=root.left;
	            newNode.right=root;
	            root.left=nullNode;
	            root=newNode;
            }
            else if (x.compareTo(root.element)>0){
	            newNode.right=root.right;
	            newNode.left=root;
	            root.right=nullNode;
	            root=newNode;
            }
            else {
                return;
            }
        }
        newNode=nullNode;
	}


    /**
     * Remove from the tree.
     * @param x the item to remove
     */
	public void remove(E x) {
	    BinaryNode<E> newTree;
	    //if x is found,it will be at the root
        if (!contains(x)){
            //item not found;do nothing
            return;
        }
        if (root.left==nullNode){
            newTree=root.right;
        }else {
            //Find the maximum in the left subtree
            //Splay it to the root;and then attach right child
            newTree=root.left;
            newTree=splay(x,newTree);
            newTree.right=root.right;
        }
        root=newTree;
	}


	public E findMin() {
		return null;
	}


	public E findMax() {
		return null;
	}


	public boolean contains(E x) {
		return false;
	}


	public void makeEmpty() {
		root = nullNode;
	}


	public boolean isEmpty() {
		return root == nullNode;
	}


	private BinaryNode<E> rotateWithLeftChild(BinaryNode<E> k2) {
		BinaryNode<E> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}


	private BinaryNode<E> rotateWithRightChild(BinaryNode<E> k1) {
		BinaryNode<E> right = k1.right;
		k1.right = right.left;
		right.left = k1;
		return k1.right.right;
	}


	public static void main(String[] args) {
		SplayTree<Integer> t = new SplayTree<>();
		// final int nums = 40000;
		// final int gap = 307;
		// System.out.println("Checking... (no bad output means success)");
		// for (int i = gap; i != 0; i = (i + gap) % nums) {
		// t.insert(i);
		// }
		// System.out.println("Inserts complete");

        List<Integer> integerList= Arrays.asList(5,12,13,15 ,16,18,20,24,25,30);
        for (Integer integer : integerList) {
            t.insert(integer);
        }
	}
}
