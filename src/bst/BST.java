package bst;

import java.util.Iterator;
import java.util.NoSuchElementException;

import dll.DLL;
import ds.DynamicSet;

/**
 * A dynamic set implementation using a binary search tree.
 * 
 * @author Tomas Mikus, 2473026m
 *
 * @param <T> generic type to be stored in a node
 */
public class BST<T extends Comparable<T>> implements DynamicSet<T> {
	private Node<T> root; 
	
	public BST() {
		root = null;
	}
	
	/**
	 * Add an element to a BST.
	 */
	public void add(T key) {
		Node<T> z = new Node<T>(key);
		Node<T> y = null;
		Node<T> x = root;
		
		while (x != null) {
			y = x;
			// If such an element already exists, do nothing
			if (z.getElement().compareTo(x.getElement()) == 0) { return; }
			
			// Get left or right child depending on the value of the element
			if (z.getElement().compareTo(x.getElement()) < 0) { 
				x = x.getLeft();
			} else { 
				x = x.getRight();
			}
		}
		
		z.setParent(y);
		// Update pointers of the node next to the received element
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
	 */
	public void remove(T key) {
		Node<T> n = search(key);
		// If such a node does not exists, do nothing
		if (n == null) { return; }
		
		// Transplant the trees depending on whether the node has nodes to its left/right 
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
	private void transplant(Node<T> u, Node<T> v) {
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
	 */
	public boolean isElement(T key) {
		return search(key) != null;
	}
	
	/**
	 * Find an element in a BST.
	 * @param key
	 * @return
	 */
	
	public Node<T> search(T key) {
		Node<T> n = root;

		// While there are still nodes and while the element is not found
		while (n != null && !(n.getElement().compareTo(key) == 0)) {
			int check = n.getElement().compareTo(key);
			// If the element is larger than the current node element, go right
			if (check < 0) {
				n = n.getRight();
			}
			// If the element is smaller than the current node element, go left
			if (check > 0) {
				n = n.getLeft();
			}
		}
		return n;
	}
	
	/**
	 * Check if a BST is empty.
	 */
	public boolean isEmpty() { 
		return setSize() == 0; 
	}
	
	/**
	 * Get the size of a given BST.
	 */
	public int setSize() { 
		return setSize(root); 
	}
	
	/**
	 * Helper method for setSize().
	 */
	private int setSize(Node<T> x) {
		if (x == null) { return 0; } 
		return 1 + setSize(x.getLeft()) + setSize(x.getRight());
	}
	
	/**
	 * Find the union of the two sets.
	 */
	public DynamicSet<T> union(DynamicSet<T> otherSet) {
		DynamicSet<T> setUnion = new BST<>();

		setUnion = union(otherSet, setUnion);
				
		return setUnion;
	}

	/**
	 * Find the intersection of the two sets.
	 */
	public DynamicSet<T> intersection(DynamicSet<T> otherSet) {
		DynamicSet<T> setIntersection = new BST<>();
		
		setIntersection = intersection(otherSet, setIntersection);
		
		return setIntersection;
	}
		
	/**
	 * Find the difference of the two sets.
	 */
	public DynamicSet<T> difference(DynamicSet<T> otherSet) {
		DynamicSet<T> setDifference = new BST<>();
		
		setDifference = difference(otherSet, setDifference);
		
		return setDifference;
	}
		
	/**
	 * Get the height of a given BST.
	 */
	public int getHeight() {
		return getHeight(root);
	}
	
	/**
	 * Helper method for getHeight().
	 */
	private int getHeight(Node<T> x) {
		// If the node is empty, return 0
		if (x == null) { return 0; } 
		// If the root node has no left and right nodes, return 0
		if (!x.hasLeft() && !x.hasRight()) { return 0; }
		return 1 + Math.max(getHeight(x.getLeft()), getHeight(x.getRight()));
	}
	
	/**
	 * Find the minimum value of a BST.
	 */
	public Node<T> min() {
		if (isEmpty()) throw new NoSuchElementException("Empty BST");
		return min(root);
	}
	
	/**
	 * Helper method for min().
	 */
	private Node<T> min(Node<T> x) {
		while (x.hasLeft()) {
			x = x.getLeft();
		}
		return x;
	}

	/**
	 * Iterator class for looping through BST values.
	 * 
	 * Uses DLL to loop through the values.
	 * 
	 * Inspiration taken from the iterative example for preorder traversal:
	 * 
	 * https://javarevisited.blogspot.com/2016/07/binary-tree-preorder-traversal-in-java-using-recursion-iteration-example.html
	 * 
	 * @author Tomas Mikus, 2473026m
	 *
	 * @param <E> generic type to be stored in a node
	 */	
	@Override
	public Iterator<T> iterator() {
		return new MyIterator<T>(this);
	}

	private class MyIterator<E extends Comparable<E>> implements Iterator<E> {
		DLL<Node<E>> nodes = new DLL<>();
		
		public MyIterator(BST<E> bst) {
			// Start from root
			nodes.add(bst.root);
		}
		
		public boolean hasNext() {
			return !nodes.isEmpty();
		}
		
		public E next() {
			// Get the next node
			dll.DLL.Node<Node<E>> current = nodes.pop();

			// Add right subtree
			if (current.getElement().hasRight()) {
				nodes.add(current.getElement().getRight());
			}
			// Add left subtree
			if (current.getElement().hasLeft()) {
				nodes.add(current.getElement().getLeft());
			}
			
			// Return the next element
			return current.getElement().getElement();
		}
	}
	
	/**
	 * Node class to be used by the BST.
	 * 
	 * @author Tomas Mikus, 2473026m
	 *
	 * @param <T> generic type to be stored in a node
	 */
	private static class Node<T> {
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

	    public void setLeft(Node<T> node) 		{ left = node; if (node != null) node.setParent(this); }
	    public void setRight(Node<T> node) 		{ right = node; if (node != null) node.setParent(this); }
	    public void setParent(Node<T> node) 	{ parent = node; }  
		
	    public boolean hasLeft() 		{ return left != null; }
	    public boolean hasRight() 		{ return right != null; }
	}
}