package dll;

import java.util.Iterator;

import main.DynamicSet;

public class DLL<T> implements DynamicSet<T> {
	private Node<T> head;
	private Node<T> tail;
	
	public DLL() {
		head = null;
		tail = null;
	}
	
	public void add (T key) {
		if (key == null || this.search(key) != null) return;
		
		Node<T> x = new Node<>(key);
		// if list is empty
		if (head == null) {
			head = x;
			tail = head;
		} else {
			// if list is not empty,
			// add element at the end
			x.setPrev(tail);
			tail.setNext(x);
			tail = x;
		}
	}
	
	public void remove(T key) {
		Node<T> x = search(key);
		
		if (x == null) return;
		// check if x is not head
		// if it is not, delete the value
		if (x.getPrev() != null) {
			x.getPrev().setNext(x.getNext());
		} else {
			// if it is head, change head to the getNext() value
			head = x.getNext();
		}
		// check if x.getNext() is present
		if (x.getNext() != null) {
			// if x.getNext() is present, update its getPrev() pointer to 
			// x.getPrev()
			x.getNext().setPrev(x.getPrev());
		}
	}
	
	public boolean isElement(T key) {
		return search(key) != null;
	}

	public boolean isEmpty() { 
		return setSize() == 0; 
	}
	
	public int setSize() {
		Node<T> cursor = head;
		int count = 0;
		while(cursor!=null) {
			count++;
			cursor = cursor.getNext();
		}
		return count;
	}
	
	@Override
	public DynamicSet<T> union(DynamicSet<T> otherSet) {
		DynamicSet<T> setUnion = new DLL<>();
		
		for(T val : this) {
			setUnion.add(val);
		}
			
		for(T val : otherSet) {
			setUnion.add(val);	
		}
				
		return setUnion;
	}
	
	public Node<T> popHead() {
		if(head == null) {
			return null;
		}
		Node<T> out = head;
		head = head.getNext();
		return out;
	}

	@Override
	public DynamicSet<T> intersection(DynamicSet<T> T) {
		DynamicSet<T> setIntersection = new DLL<>();
		
		for(T val : this) {
			if(T.isElement(val)) {
				setIntersection.add(val);
			}
		}
		
		return setIntersection;
	}

	@Override
	public DynamicSet<T> difference(DynamicSet<T> T) {
		DynamicSet<T> setDifference = new DLL<>();
		
		for(T val : this) {
			if(!T.isElement(val)) {
				setDifference.add(val);
			}
		}
		
		return setDifference;
	}
	
	public boolean subset(DynamicSet<T> otherSet) {
		// If S is larger than T, S cannot be a subset of T - return false
		if (this.setSize() > otherSet.setSize()) { return false; }
		
		for (T n : this) {
			// Check if an element of S is present in T
			if (!otherSet.isElement(n)) {
				// if not, return false
				return false;
			}
		}
		
		// If all elements are present, return true
		return true;
	}
	
	public Node<T> search(T t) {
		Node<T> n = head;
		while (n != null && n.getElement() != t) {
			n = n.getNext();
		}
		return n;
	}
	
	/**
	 * Iterator
	 * @author tomas
	 *
	 * @param <E>
	 */	
	public Iterator<T> iterator() {
		return new MyIterator<T>(this);
	}
	
	private class MyIterator<E> implements Iterator<E> {
		Node<E> cursor;
		
		public MyIterator(DLL<E> arg){
			cursor = arg.head;		
		}

		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public E next() {
			E data = cursor.getElement();
			cursor = cursor.getNext();
			return data;
		}
	}
	
	/**
	 * Node class
	 * @author tomas
	 *
	 * @param <T>
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
}
