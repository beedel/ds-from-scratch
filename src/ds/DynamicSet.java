package ds;

/**
 * A dynamic set interface to be implemented by DLL and BST.
 * Provides the methods to be implemented by these classes.
 * 
 * @author Tomas Mikus, 2473026m
 *
 * @param <T> generic type to be stored in a node
 */
public interface DynamicSet<T> extends Iterable<T> {
	/**
	 * Add an element to a set.
	 * 
	 * @param key		Element to be added
	 */
	public void add(T key);
	
	/**
	 * Remove an element from a set.
	 * 
	 * @param key		Element to be removed
	 */
	public void remove(T key);
	
	/**
	 * Check if an element is present in a set.
	 * @param key 		Element to be searched for
	 * @return			True if element is present, false if not
	 */
	public boolean isElement(T key);
	
	/**
	 * Check if the set is empty.
	 * @return			True if the set is empty, false if not
	 */
	public boolean isEmpty();
	
	/** 
	 * Get the size of a set.
	 * 
	 * @return 			The size of a set
	 */
	public int setSize();
	
	/**
	 * Return the union of the caller set and the given set.
	 * 
	 * @param otherSet		Dynamic set
	 * @return				Union of the two sets
	 */
	public DynamicSet<T> union(DynamicSet<T> otherSet);
	
	/**
	 * Helper method to provide further code abstraction.			
	 */
	public default DynamicSet<T> union(DynamicSet<T> otherSet, DynamicSet<T> setUnion) {
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
	 * Return the intersection of the caller set and the given set.
	 * 
	 * @param otherSet		Dynamic set
	 * @return				intersection of the two sets
	 */
	public DynamicSet<T> intersection(DynamicSet<T> otherSet);
	
	/**
	 * Helper method to provide further code abstraction.			
	 */
	public default DynamicSet<T> intersection(DynamicSet<T> otherSet, DynamicSet<T> setIntersection) {
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
	 * Return the difference of the caller set and the given set.
	 * 
	 * @param otherSet		Dynamic set
	 * @return				Difference of the two sets
	 */
	public DynamicSet<T> difference(DynamicSet<T> otherSet);
	
	/**
	 * Helper method to provide further code abstraction.			
	 */
	public default DynamicSet<T> difference(DynamicSet<T> otherSet, DynamicSet<T> setDifference) {
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
	 * Check if the caller set is a subset of a given set.
	 * 
	 * @param otherSet		Dynamic set
	 * @return				True if it is a subset, false if not
	 */
	public default boolean subset(DynamicSet<T> otherSet) {
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
}
