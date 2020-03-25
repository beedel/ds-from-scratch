package bst;

import java.util.Iterator;
import java.util.NoSuchElementException;

import dll.DLL;
import ds.DynamicSet;

public class BST<T extends Comparable<T>> implements DynamicSet<T> {
	private Node<T> root; 
	
	public BST() {
		root = null;
	}
	
	/**
	 * Add an element to a BST.
	 * @param z
	 */
	public void add(T key) {
		// if no nodes add as root
		if (root == null) {
			root = new Node<T>(key);
			return;
		}
		
		Node<T> z = new Node<T>(key);
		Node<T> y = null;
		Node<T> x = root;
		
		while (x != null) {
			y = x;
			// check for duplicates
			if (z.getElement().compareTo(x.getElement()) == 0) return;
			
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
		Node<T> n = search(key);
		if (n == null) return;
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
	public DynamicSet<T> union(DynamicSet<T> otherSet) {
		DynamicSet<T> setUnion = new BST<>();

		for(T val : this) {
			setUnion.add(val);
		}
			
		for(T val : otherSet) {
			setUnion.add(val);	
		}
				
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
		
		for(T val : this) {
			if (T.isElement(val)) {
				setIntersection.add(val);
			}

		}

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
		
		for(T val : this) {
			if (!T.isElement(val)) {
				setDifference.add(val);
			}
		}
		
		return setDifference;
	}
	
	public boolean subset(DynamicSet<T> otherSet) {
		// If S is larger than T, S cannot be a subset of T - return false
		if (this.setSize() > otherSet.setSize()) { return false; }
		
		for (T n : this) {
			if (!otherSet.isElement(n)) {
				return false;
			}
		}
		
		// If all elements are present, return true
		return true;
	}
		
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
		Node<T> cursor = root;

		while (cursor != null && !(cursor.key.compareTo(key) == 0)) {
			int val = cursor.key.compareTo(key);
			if (val < 0) {
				cursor = cursor.right;
			}
			if (val > 0) {
				cursor = cursor.left;
			}
		}
		return cursor;
	}
	
	@Override
	public Iterator<T> iterator() {

		return new MyIterator<T>(this);
	}

	/**
	 * An iterator that facilitates set operations follows a preorder
	 * prevents degenerate tree in union
	 * 
	 * https://javarevisited.blogspot.com/2016/07/binary-tree-preorder-traversal-in-java-using-recursion-iteration-example.html
	 * 
	 * @param <E> the type of the elements in the iterator
	 */
	private class MyIterator<E extends Comparable<E>> implements Iterator<E> {
		DLL<Node<E>> nodes = new DLL<>();
		
		@SuppressWarnings("unchecked")
		public MyIterator(BST<E> arg) {
			nodes.add((BST<T>.Node<E>) arg.root);
		}
		
		@Override
		public boolean hasNext() {
			return !nodes.isEmpty();
		}

		@Override
		public E next() {
			dll.DLL.Node<BST<T>.Node<E>> current = nodes.popHead();

			if (current.getElement().hasRight()) {
				nodes.add(current.getElement().getRight());
			}
			if (current.getElement().hasLeft()) {
				nodes.add(current.getElement().getLeft());
			}
			
			return current.getElement().getElement();
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
		
	    public T  getElement()			{ return key; }
	    public Node<T> getLeft()		{ return left; }
	    public Node<T> getRight()		{ return right; }
	    public Node<T> getParent()		{ return parent; }

	    public void setElement(T e) 			{ key = e; }
	    public void setLeft(Node<T> node) 		{ left = node; if (node != null) node.setParent(this); }
	    public void setRight(Node<T> node) 		{ right = node; if (node != null) node.setParent(this); }
	    public void setParent(Node<T> node) 	{ parent = node; }  
		
	    public boolean hasLeft() 		{ return left != null; }
	    public boolean hasRight() 		{ return right != null; }
	    public boolean hasParent() 		{ return parent != null; }
	}
}