package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bst.BST;
import dll.DLL;
import ds.DynamicSet;

/**
 * Driver class for running the program.
 * Answers part 2 of ADS2 AE2.
 * 
 * @author Tomas Mikus, 2473026m
 *
 */
public class Driver {
	public static void main(String[] args) throws NumberFormatException, IOException {		
		
		// Read integers from a file into a list
		List<Integer> fileData = getFileData("files/int20k.txt");
		// List for storing 100 random integers
		List<Integer> randInts = new ArrayList<>(100);
		
		// Add 100 random integers to randInts
		for(int i = 0; i < 100; i++) {
			int randInt = (int) (Math.random() * 49999 + 1);
			randInts.add(randInt);
		}
		
		// Dynamic set implementation using a doubly linked list
		DynamicSet<Integer> linkedList = new DLL<>();
		// Dynamic set implementation using a binary search tree
		DynamicSet<Integer> binaryTree = new BST<>();
		
		// Add integers read from a file to both sets
		addData(fileData, linkedList);
		addData(fileData, binaryTree);
		
		// Start part 2 of the assessed exercise
		System.out.println("\nPART 2");
		
		System.out.println("\n2a)");
		double avgDLL, avgBST;
		avgDLL = calculateRunningTime(linkedList, randInts);
		avgBST = calculateRunningTime(binaryTree, randInts);
		System.out.println("DLL average time (ns): " + avgDLL);
		System.out.println("BST average time (ns): " + avgBST);
		
		System.out.println("\n2b)");
		System.out.println("Size of DLL: "+ linkedList.setSize());
		System.out.println("Size of BST: "+ binaryTree.setSize());
		
		System.out.println("\n2c)");
		System.out.println("Height of BST: "+ ((BST<Integer>) binaryTree).getHeight());
		
		
		// Testing
		
		System.out.println("\n\nTESTING\n");
	
		// Create DLLs and BSTs for testing
		DynamicSet<Integer> linkedList2 = new DLL<>();
		DynamicSet<Integer> linkedList3 = new DLL<>();
		DynamicSet<Integer> binaryTree2 = new BST<>();
		DynamicSet<Integer> binaryTree3 = new BST<>();
	
		
		// Add values to dynamic sets
		linkedList2.add(1);
		linkedList2.add(2);
		linkedList2.add(3);
		linkedList2.add(4);
		linkedList2.add(5);
		linkedList2.add(6);
		
		linkedList3.add(1);
		linkedList3.add(2);
		linkedList3.add(8);
		
		binaryTree2.add(1);
		binaryTree2.add(2);
		binaryTree2.add(3);
		binaryTree2.add(4);
		binaryTree2.add(5);
		binaryTree2.add(6);
		
		binaryTree3.add(1);
		binaryTree3.add(2);
		binaryTree3.add(8);		
	
		// Check set operations for a DLL dynamic set implementation
		System.out.println("LINKED LISTS\n");
		System.out.println("Size of dynamic set 1: " + linkedList2.setSize());
		System.out.println("Size of dynamic set 2: " + linkedList3.setSize());
		System.out.println("Size of their union: " + linkedList2.union(linkedList3).setSize());
		System.out.println("Size of their intersection: " + linkedList2.intersection(linkedList3).setSize());
		System.out.println("Size of their difference: " + linkedList2.difference(linkedList3).setSize());
		System.out.println("Set 1 subset of set 2?: " + linkedList2.subset(linkedList3));
		linkedList3.remove(8);
		System.out.println("Remove 8 from set 2, size after removal: " + linkedList3.setSize());
		System.out.println("Set 2 subset of set 1?: " + linkedList3.subset(linkedList2));

		// Check set operations for a BST dynamic set implementation
		System.out.println("\nBINARY SEARCH TREES\n");
		System.out.println("Size of dynamic set 1: " + binaryTree2.setSize());
		System.out.println("Size of dynamic set 2: " + binaryTree3.setSize());
		System.out.println("Size of their union: " + binaryTree2.union(binaryTree3).setSize());
		System.out.println("Size of their intersection: " + binaryTree2.intersection(binaryTree3).setSize());
		System.out.println("Size of their difference: " + binaryTree2.difference(binaryTree3).setSize());
		System.out.println("Set 1 subset of set 2?: " + binaryTree2.subset(binaryTree3));
		binaryTree3.remove(8);
		System.out.println("Remove 8 from set 2, size after removal: " + binaryTree3.setSize());
		System.out.println("Set 2 subset of set 1?: " + binaryTree3.subset(binaryTree2));
	}
	
	/**
	 * Add integers to a dynamic set from a list.
	 * 
	 * @param data			List of integers
	 * @param structure		Dynamic Set which the integers will be added to
	 */
	private static void addData(List<Integer> data, DynamicSet<Integer> structure) {
		for(int val : data) {
			structure.add(val);
		}	
	}
	
	/**
	 * Calculates the running time of calling isElement() on a dynamic
	 * set for a list of integers.
	 * 
	 * @param set 			DynamicSet of integers 
	 * @param randomInts	List of random integers 
	 * @return				Average running time
	 */
	private static double calculateRunningTime(DynamicSet<Integer> set, List<Integer> randomInts) {
		long time1, time2;
		double avg;
		
		// Start time
		time1 = System.nanoTime();
		
		// Check if the random integers are elements of the given set
		for(int val: randomInts) {
			set.isElement(val);
		}
		
		// End time
		time2 = System.nanoTime();
		
		// Find average time for running isElement on a given dynamic set
		avg = (double)(time2 - time1) / randomInts.size();
		
		return avg;
	}
	
	/**
	 * Reads integers from a given file into a list.
	 * 
	 * @param filename 		File with a list of integers
	 * @return 				A list of integers
	 * @throws 				NumberFormatException
	 * @throws 				IOException
	 */
	private static List<Integer> getFileData(String filename) throws NumberFormatException, IOException{
		
		BufferedReader abc = new BufferedReader(new FileReader(filename));
		List<Integer> lines = new ArrayList<Integer>();

		String line;
		while((line = abc.readLine()) != null) {
		    lines.add(Integer.parseInt(line));
		}
		abc.close();
		
		return lines;
	}
}
