package dll;

import java.util.Iterator;

import ds.DynamicSet;

/**
 * A dynamic set implementation using a doubly linked list.
 * 
 * @author Tomas Mikus, 2473026m
 *
 * @param <T> generic type to be stored in a node
 */
public class DLL<T> implements DynamicSet<T> {
	private Node<T> head;
	private Node<T> tail;
	
	public DLL() {
		head = null;
		tail = null;
	}
	
	/**
	 * Add an element at the end of a doubly linked list.
	 */
	public void add (T key) {
		// If no element has been passed or if the element already exists, do nothing
		if (key == null || this.search(key) != null) return;
		
		Node<T> x = new Node<>(key);
		
		// If list is empty, add element, make it the head and tail of the DLL
		if (head == null) {
			head = x;
			tail = head;
		} else {
			// Add element at the end of the DLL
			x.setPrev(tail);
			tail.setNext(x);
			tail = x;
		}
	}
	
	/**
	 * Remove an element from a doubly linked list.
	 */
	public void remove(T key) {
		// See if such element exists in the DLL
		Node<T> x = search(key);
		
		// If not, do nothing
		if (x == null) return;
		
		// If x is not the head, update the pointer to the next value for the element before x
		if (x.getPrev() != null) {
			x.getPrev().setNext(x.getNext());
		} else {
			// If x is the head, change head to be the next value
			head = x.getNext();
		}
		
		// If there is a next element
		if (x.getNext() != null) {
			// Update its prev pointer to the element before x
			x.getNext().setPrev(x.getPrev());
		}
	}
	
	/**
	 * Check if an element is present in the DLL.
	 */
	public boolean isElement(T key) {
		return search(key) != null;
	}
	
	/**
	 * Helper method for isElement.
	 * 
	 * @param t		Element searched for
	 * @return		Node with found element, or null
	 */
	private Node<T> search(T key) {
		// Start with the head node
		Node<T> n = head;
		
		// While there are still nodes and while the node with given element is not found
		while (n != null && n.getElement() != key) {
			// Get the next node
			n = n.getNext();
		}
		return n;
	}

	/**
	 * Check if a DLL is empty.
	 */
	public boolean isEmpty() { 
		return setSize() == 0; 
	}
	
	/**
	 * Find the size of the DLL.
	 */
	public int setSize() {
		// Start with the head node
		Node<T> n = head;
		int count = 0;
		
		// While there are still nodes 
		while(n != null) {
			count++;
			// Get the next node
			n = n.getNext();
		}
		return count;
	}
	
	/**
	 * Find the union of the two sets.
	 */
	@Override
	public DynamicSet<T> union(DynamicSet<T> otherSet) {
		DynamicSet<T> setUnion = new DLL<>();
		
		// Add all elements in the caller set.
		for(T val : this) {
			setUnion.add(val);
		}
			
		// Add all elements in the given set (duplicates are not added).
		for(T val : otherSet) {
			setUnion.add(val);	
		}
				
		return setUnion;
	}
	
	/**
	 * Find the intersection of the two sets.
	 */
	@Override
	public DynamicSet<T> intersection(DynamicSet<T> otherSet) {
		DynamicSet<T> setIntersection = new DLL<>();
		
		// For all elements in the caller set
		for(T val : this) {
			// If an element is present in the given set
			if(otherSet.isElement(val)) {
				setIntersection.add(val);
			}
		}
		
		return setIntersection;
	}

	/**
	 * Find the difference of the two sets.
	 */
	@Override
	public DynamicSet<T> difference(DynamicSet<T> otherSet) {
		DynamicSet<T> setDifference = new DLL<>();
		
		// For all elements in the caller set
		for(T val : this) {
			// If an element is not present in the given set
			if(!otherSet.isElement(val)) {
				setDifference.add(val);
			}
		}
		
		return setDifference;
	}
	
	/**
	 * Check if this set is a subset of a given set.
	 */
	public boolean subset(DynamicSet<T> otherSet) {
		// If the caller set is larger than otherSet, it cannot be a subset of otherSet - return false
		if (this.setSize() > otherSet.setSize()) { return false; }
		
		// For all elements in the caller set
		for (T n : this) {
			// If an element is not present in the other set, return false
			if (!otherSet.isElement(n)) { return false; }
		}
		
		// If all elements are present, return true
		return true;
	}
	
	/**
	 * Iterator class for looping through DLL values.
	 * 
	 * @author Tomas Mikus, 2473026m
	 *
	 * @param <E> generic type to be stored in a node
	 */	
	public Iterator<T> iterator() {
		return new MyIterator<T>(this);
	}
	
	private class MyIterator<E> implements Iterator<E> {
		Node<E> n;
		
		public MyIterator(DLL<E> arg){
			// Start with the head of a DLL
			n = arg.head;		
		}

		@Override
		public boolean hasNext() {
			// If n is null, that means we reached the end of the DLL
			return n != null;
		}

		@Override
		public E next() {
			// Get the next element
			E data = n.getElement();
			// Update the node to be the next node
			n = n.getNext();
			return data;
		}
	}
	
	/**
	 * Node class to be used by the DLL.
	 * 
	 * @author Tomas Mikus, 2473026m
	 *
	 * @param <T> generic type to be stored in a node
	 */
	public static class Node<T> {
		private T key;
		private Node<T> prev, next;
		
		public Node(T key) {
			this.key = key;
			this.prev = null;
			this.next = null;
		}
		
	    public T  getElement() 			{ return key; }
	    public Node<T> getPrev() 		{ return prev; }
	    public Node<T> getNext() 		{ return next; }
		
	    public void setElement (T e) 		{ key = e; }
	    public void setPrev (Node<T> node) 	{ prev = node; }
	    public void setNext (Node<T> node) 	{ next = node; }
	}
	
	/**
	 * Helper method for the BST iterator implementation.
	 * Helps use a DLL as a stack.
	 * 
	 * @return		The next node
	 */
	public Node<T> popHead() {
		if(head == null) {
			return null;
		}
		Node<T> out = head;
		head = head.getNext();
		return out;
	}
}
