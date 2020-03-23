package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bst.BST;
import dll.DLL;

public class Driver {
	public static void main(String[] args) throws NumberFormatException, IOException {		
		
		//get and populate required data
		List<Integer> fileData = getFileData("int20k.txt");
		List<Integer> randList = new ArrayList<>(100);
		
		// generate random numbers
		for(int i = 0; i < 100; i++) {
			int rand = (int)(Math.random() * 49999 + 1);
			randList.add(rand);
		}
		
		// Create sets 
		DynamicSet<Integer> linkedList = new DLL<>();
		BST<Integer> binaryTree = new BST<>();
		
		// Add values to DLL dynamic set
		System.out.println("\nAdding values to DLL. . .");
		for(int val : fileData) {
			linkedList.add(val);
		}
		
		// Add values to BST dynamic set
		System.out.println("\nAdding values to BST. . .");
		for(int val : fileData) {
			binaryTree.add(val);
		}
		
		System.out.println("\nHeight of BST: "+ binaryTree.getHeight());
		System.out.println("\nSize of BST: "+ binaryTree.setSize());
		System.out.println("\nSize of DLL: "+ linkedList.setSize());
		
		
		// compare the two implementations of dynamic set
		double avgDLL, avgBST;
		
		avgDLL = calculateRunningTime(linkedList, randList);
		avgBST = calculateRunningTime(binaryTree, randList);
		
		System.out.println("\nDLL average time: " + avgDLL);
		System.out.println("\nBST average time: " + avgBST);
	}
	
	private static double calculateRunningTime(DynamicSet<Integer> set, List<Integer> randomInts) {
		long time1, time2;
		double avg;
		
		time1 = System.nanoTime();
		
		// run isElement on the random numbers
		for(int val: randomInts) {
			set.isElement(val);
		}
		
		time2 = System.nanoTime();
		
		avg = (double)(time2 - time1) / randomInts.size();
		
		return avg;
	}
	
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
