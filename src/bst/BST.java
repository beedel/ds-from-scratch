package bst;

import java.util.Iterator;
import java.util.NoSuchElementException;

import main.DynamicSet;

public class BST<T extends Comparable<T>> implements DynamicSet<T> {
	private Node<T> root; 
	
	public BST() {
		root = null;
	}
	
	/*
	 * add
	 * remove
	 * isElement
	 * isEmpty
	 * setSize
	 * union
	 * intersection
	 * difference
	 * subset
	 */
	
	/**
	 * Add an element to a BST.
	 * @param z
	 */
	public void add(T key) {
		Node<T> z = new Node<T>(key);
		Node<T> y = null;
		Node<T> x = root;
		
		while (x != null) {
			y = x;
			// the line that fixed duplicates
			if (z.getElement() == x.getElement()) return;
			
			if (z.getElement().compareTo(x.getElement()) < 0) { 
				x = x.getLeft();
			} else { 
				x = x.getRight();
			}
		}
		
		z.setParent(y);
		if (y == null) { 
			root = z;
		} else if (z.getElement().compareTo(y.getElement()) < 0) { 
			y.setLeft(z);
		} else { 
			y.setRight(z);
		}
	}
	
	/**
	 * Delete an element from a BST.
	 * @param z
	 */
	public void remove(T key) {
		// fix this, should find it
		Node<T> n = new Node<T>(key);
		if (!n.hasLeft()) {
			transplant(n, n.getRight());
		} else if (!n.hasRight()) {
			transplant(n, n.getLeft());
		} else {
			Node<T> y = min(n.getRight());
			if (y.getParent() != n) {
				transplant(y, y.getRight());
				y.setRight(n.getRight());
				y.getRight().setParent(y);
			}
			transplant(n, y);
			y.setLeft(n.getLeft());
			y.getLeft().setParent(y);
		}
	}
	
	/**
	 * Replace the subtree rooted at u with the 
	 * subtree rooted at v.
	 * @param u
	 * @param v
	 */
	public void transplant(Node<T> u, Node<T> v) {
		if (u.getParent() == null) {
			root = v;
		} else if (u == u.getParent().getLeft()) {
			u.getParent().setLeft(v);
		} else {
			u.getParent().setRight(v);
		}
		if (v != null) {
			v.setParent(u.getParent());
		}
	}

	/**
	 * Check if an element is present in a BST.
	 * @param key
	 * @return
	 */
	public boolean isElement(T key) {
		return search(key) != null;
	}
	
	/**
	 * Check if a BST is empty.
	 * @return
	 */
	public boolean isEmpty() { 
		return setSize() == 0; 
	}
	
	/**
	 * Get the size of a given BST.
	 * @return
	 */
	public int setSize() { 
		return setSize(root); 
	}
	
	private int setSize(Node<T> x) {
		if (x == null) {
			return 0;
		} 
		return 1 + setSize(x.getLeft()) + setSize(x.getRight());
	}
	


	
	/**
	 * Returns the union of two sets.
	 * 
	 * @param S
	 * @param T
	 * @return
	 */
	public DynamicSet<T> union(DynamicSet<T> T) {
		DynamicSet<T> setUnion = new BST<>();

		setUnion = union(setUnion, T);
				
		return setUnion;
	}

	/**
	 * Returns the intersection of two sets.
	 * @param S
	 * @param T
	 * @return
	 */
	public DynamicSet<T> intersection(DynamicSet<T> T) {
		DynamicSet<T> setIntersection = new BST<>();
		
		setIntersection = intersection(setIntersection, T);
		
		return setIntersection;
	}
		
	/**
	 * Returns the difference of two sets.
	 * @param S
	 * @param T
	 * @return
	 */
	public DynamicSet<T> difference(DynamicSet<T> T) {
		DynamicSet<T> setDifference = new BST<>();
		
		setDifference = difference(setDifference, T);
		
		return setDifference;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Return the height of a given BST.
	 * @return
	 */
	public int getHeight() {
		return getHeight(root);
	}
	
	private int getHeight(Node<T> x) {
		if (x == null) {
			return 0;
		} 
		if (!x.hasLeft() && !x.hasRight()) {
			return 0;
		}
		return 1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight()));
	}
	
	/**
	 * Find the minimum value.
	 * @param x
	 * @return
	 */
	public Node<T> min() {
		if (isEmpty()) throw new NoSuchElementException("Empty BST");
		return min(root);
	}
	
	private Node<T> min(Node<T> x) {
		while (x.hasLeft()) {
			x = x.getLeft();
		}
		return x;
	}

	/**
	 * Find the maximum value.
	 * @param x
	 * @return
	 */
	public Node<T> max() {
		if (isEmpty()) throw new NoSuchElementException("Empty BST");
		return max(root);
	}
	
	private Node<T> max(Node<T> x) {
		while (x.hasRight()) {
			x = x.getRight();
		}
		return x;
	}
	
	/**
	 * Find an element in a BST.
	 * @param key
	 * @return
	 */
	public Node<T> search(T key) {
		return search(root, key);
	}
	
	private Node<T> search(Node<T> x, T key) {
		if (x == null || x.getElement().compareTo(key) == 0) {
			return x;
		}
		if (x.getElement().compareTo(key) < 0) {
			return search(x.getLeft(), key);
		} else {
			return search(x.getRight(), key);
		}
	}

	
	/**
	 * Iterator class
	 */
	@Override
	public Iterator<T> iterator() {

		return new TreeIterator<T>(this);
	}

	private class TreeIterator<E extends Comparable<E>> implements Iterator<E> {
		Node<E> cursor;
		
		@SuppressWarnings("unchecked")
		public TreeIterator(BST<E> arg) {
			cursor = (BST<T>.Node<E>) arg.root;
		}
		
		@Override
		public boolean hasNext() {
			return cursor != null;
		}
		
		@Override
		public E next() {
			E data = cursor.key;
			
			if (cursor.left != null) {
				cursor = cursor.left;
			} else if (cursor.right != null) {
				cursor = cursor.right;
			} else {
				cursor = null;
			}
			
			return data;
		}

	}
	

	
	private class Node<T> {
		private T key;
		private Node<T> left, right, parent;
		
		public Node(T key) {
			this.key = key;
			this.left = null;
			this.right = null;
			this.parent = null;
		}
		
	    public T  getElement()	{ return key; }
	    public Node<T> getLeft()		{ return left; }
	    public Node<T> getRight()		{ return right; }
	    public Node<T> getParent()		{ return parent; }

	    public void setElement(T e) 		{ key = e; }
	    public void setLeft(Node<T> node) 		{ left = node; if (node != null) node.setParent(this); }
	    public void setRight(Node<T> node) 	{ right = node; if (node != null) node.setParent(this); }
	    public void setParent(Node<T> node) 	{ parent = node; }  
		
	    public boolean hasLeft() 		{ return left != null; }
	    public boolean hasRight() 		{ return right != null; }
	}

	
	/**
	 * prints the entire tree using an in order traversal
	 */
	public void printInOrder() {
		printInOrder(root);
	}

	/**
	 * prints the tree through an in order traversal beneath the node t
	 * prints an in order sequence of elements if the tree preserves the bst
	 *  property
	 * @param t the node to start from
	 */
	private void printInOrder(Node t) {
		if (t == null)
			return;
		printInOrder(t.getLeft());
		System.out.println(t.getElement());
		printInOrder(t.getRight());
	}
	
}
	
	
	
	
	
	
	
	
	

