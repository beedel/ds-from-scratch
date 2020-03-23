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
	
	public void add (T t) {
		if (t == null || this.search(t) != null) return;
		
		Node<T> x = new Node<>(t);
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
		Node<T> x = this.search(key);
		
		if (search(x.getElement()) == null) return;
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
			cursor = cursor.next;
		}
		
		return count;
	}
	
	@Override
	public DynamicSet<T> union(DynamicSet<T> T) {
		DynamicSet<T> setUnion = new DLL<>();
		
		setUnion = union(setUnion, T);
				
		return setUnion;
	}

	@Override
	public DynamicSet<T> intersection(DynamicSet<T> T) {
		DynamicSet<T> setIntersection = new DLL<>();
		
		setIntersection = intersection(setIntersection, T);
		
		return setIntersection;
	}

	@Override
	public DynamicSet<T> difference(DynamicSet<T> T) {
		DynamicSet<T> setDifference = new DLL<>();
		
		setDifference = difference(setDifference, T);
		
		return setDifference;
		
	}

	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Node<T> search(T t) {
		Node<T> n = head;
		while (n != null && !n.getElement().equals(t)) {
			n = n.getNext();
		}
		return n;
	}

	
	/**
	 * removes and returns the first element of the linked list
	 * returns null if empty
	 * 
	 * @return the first key in the linked list
	 */
	public T popHead() {
		if(head == null)
			return null;
		T out = head.key;
		head = head.next;
		return out;
	}
	
	/**
	 * Iterator
	 * @author tomas
	 *
	 * @param <E>
	 */
	
	
	public Iterator<T> iterator() {
		return new DLLIterator<T>(this);
	}
	
	private class DLLIterator<E> implements Iterator<E> {
		Node<E> cursor;
		
		public DLLIterator(DLL<E> arg){
			cursor = arg.head;		
		}

		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public E next() {
			E data = cursor.key;
			cursor = cursor.next;
			return data;
		}
	}
	
	/**
	 * Node class
	 * @author tomas
	 *
	 * @param <T>
	 */
	private static class Node<T> {
		private T key;
		private Node<T> prev, next;
		
		public Node(T key) {
			this.key = key;
			this.prev = null;
			this.next = null;
		}
		
	    public T  getElement() 	{ return key; }
	    public Node<T> getPrev() 		{ return prev; }
	    public Node<T> getNext() 		{ return next; }
		
	    public void setElement (T e) 		{ key = e; }
	    public void setPrev (Node<T> node) 	{ prev = node; }
	    public void setNext (Node<T> node) 	{ next = node; }
	}
}
