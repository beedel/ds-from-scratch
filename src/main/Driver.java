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
		List<Integer> fileData = getFileData("ints.txt");
		List<Integer> randList = new ArrayList<>(100);
		
		for(int i = 0; i < 100; i++) {
			int rand = (int)(Math.random() * 49999 + 1);
			randList.add(rand);
		}
		
		DynamicSet<Integer> linkedList = new DLL<>();
		BST<Integer> binaryTree = new BST<>();
		
		System.out.println("\nAdding values to DLL. . .\n");
		for(int val : fileData) {
			linkedList.add(val);
		}
		
		System.out.println("\nAdding values to BST. . .\n");
		for(int val : fileData) {
			binaryTree.add(val);
		}
		
		System.out.println("\nHeight of BST: "+ binaryTree.getHeight());
		System.out.println("\nSize of BST: "+ binaryTree.setSize());
		System.out.println("\nSize of DLL: "+ linkedList.setSize());
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
