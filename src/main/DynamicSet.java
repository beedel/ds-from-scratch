package main;

public interface DynamicSet<T> extends Iterable<T> {

	/**
	 * adds the element t to the dynamic set
	 * 
	 * @param t the element to add to the set
	 */
	public void add(T t);
	
	
	/**
	 * removes the given element from the set if it exists
	 * 
	 * @param t the element to remove 
	 */
	public void remove(T t);
	
	/**
	 * returns whether the element is present in the set
	 * 
	 * @param t the element to test presence
	 * @return whether the element is present in the set
	 */
	public boolean isElement(T t);
	
	
	/**
	 * returns whether there are no elements present in the set
	 * 
	 * @return whether there are no elements present
	 */
	public boolean isEmpty();
	
	/**
	 * returns the number of elements in the set
	 * 
	 * @return the number of elements in the adt
	 */
	public int setSize();
	
	//set theoretical operations
	
	/**
	 * returns the union of the two sets, no changes are made to the underlying
	 * dynamic sets using the underlying type
	 * of the callee method
	 * 
	 * @param arg the dynamic set to be unionised
	 * @return the union of the two sets using the concrete implementation of
	 * the object it was called on
	 */
	public DynamicSet<T> union(DynamicSet<T> otherSet);
	
	public default DynamicSet<T> union(DynamicSet<T> setUnion, DynamicSet<T> otherSet) {
		for(T val : this) {
			setUnion.add(val);
		}
			
		for(T val : otherSet) {
			setUnion.add(val);	
		}
				
		return setUnion;
	}
	
	
	/**
	 * returns the intersection of two dynamic sets using the underlying type
	 * of the callee method
	 * 
	 * @param arg the dynamic set to be intersected with
	 * @return the intersection of the called upon set and its argument
	 */
	public DynamicSet<T> intersection(DynamicSet<T> otherSet);
	
	public default DynamicSet<T> intersection(DynamicSet<T> setIntersection, DynamicSet<T> otherSet) {
		for(T val : this) {
			if(otherSet.isElement(val)) {
				setIntersection.add(val);
			}
		}
		
		return setIntersection;
	}
	
	
	/**retruns the set difference of this object and its arguement
	 * (in this set or the arguement, not both)
	 * 
	 * 
	 * @param arg the set to compared for set difference
	 * @return the difference of the two sets
	 */
	public DynamicSet<T> difference(DynamicSet<T> otherSet);
	
	
	public default DynamicSet<T> difference(DynamicSet<T> setDifference, DynamicSet<T> otherSet) {
		for(T val : this) {
			if(!otherSet.isElement(val)) {
				setDifference.add(val);
			}
		}
		
		return setDifference;
	}
	
	/**
	 * returns whether the argument is a subset of this object 
	 * (in implementation) using the underlying type
	 * of the callee method
	 * 
	 * @param arg the test to be a subset 
	 * @return whether arg is a subset of this
	 */
	public default boolean subset(DynamicSet<T> otherSet) {
		// If S is larger than T, S cannot be a subset of T - return false
		if (this.setSize() > otherSet.setSize()) { return false; }
		
		for (T n : otherSet) {
			// Check if an element of S is present in T
			if (!this.isElement(n)) {
				// if not, return false
				return false;
			}
		}
		
		// If all elements are present, return true
		return true;
	}
}
