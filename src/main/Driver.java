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
		
		// get and populate required data
		List<Integer> fileData = getFileData("ints.txt");
		List<Integer> randInts = new ArrayList<>(100);
		
		// generate random numbers
		for(int i = 0; i < 100; i++) {
			int rand = (int) (Math.random() * 49999 + 1);
			randInts.add(rand);
		}
		
		// Create sets 
		DLL<Integer> linkedList = new DLL<>();
		BST<Integer> binaryTree = new BST<>();
		
		addData(fileData, linkedList);
		addData(fileData, binaryTree);
		

		System.out.println("\nPART 2");
		
		// compare the two implementations of dynamic set
		double avgDLL, avgBST;
		
		avgDLL = calculateRunningTime(linkedList, randInts);
		avgBST = calculateRunningTime(binaryTree, randInts);
		
		System.out.println("\n2a)");
		System.out.println("DLL average time: " + avgDLL);
		System.out.println("BST average time: " + avgBST);
		
		System.out.println("\n2b)");
		System.out.println("Size of DLL: "+ linkedList.setSize());
		System.out.println("Size of BST: "+ binaryTree.setSize());
		
		System.out.println("\n2c)");
		System.out.println("Height of BST: "+ binaryTree.getHeight());
		
		
		// testing
		
//		System.out.println();
//		
//		List<Integer> fileData2 = getFileData("int10.txt");
//		List<Integer> fileData3 = getFileData("int10-2.txt");
//		
//		DLL<Integer> linkedList2 = new DLL<>();
//		DLL<Integer> linkedList3 = new DLL<>();
//		BST<Integer> binaryTree2 = new BST<>();
//		BST<Integer> binaryTree3 = new BST<>();
//		
//		addData(fileData2, linkedList2);
//		addData(fileData3, linkedList3);
//		addData(fileData2, binaryTree2);
//		addData(fileData3, binaryTree3);
		
//		System.out.println();
//		System.out.println(linkedList2.union(linkedList3).setSize());
//		System.out.println(linkedList2.intersection(linkedList3).setSize());
//		System.out.println(linkedList2.difference(linkedList3).setSize());
//		System.out.println(linkedList2.subset(linkedList3));
//		
//		System.out.println();
//		System.out.println(binaryTree2.union(binaryTree3).setSize());
//		System.out.println(binaryTree2.intersection(binaryTree3).setSize());
//		System.out.println(binaryTree2.difference(binaryTree3).setSize());
//		System.out.println(binaryTree2.subset(binaryTree3));

		// remove existing value
//		linkedList.remove(2);
//		System.out.println(linkedList.setSize());
//		binaryTree.remove(2);
//		System.out.println(binaryTree.setSize());
		
		// remove non-existent value
//		linkedList.remove(20);
//		System.out.println(linkedList.setSize());
//		binaryTree.remove(20);
//		System.out.println(binaryTree.setSize());
	}
	
	private static void addData(List<Integer> data, DynamicSet<Integer> structure) {
		for(int val : data) {
			structure.add(val);
		}	
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
