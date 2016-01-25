package com.assignment1.extendiblehashing;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import com.assignment1.storage.Bucket;
import com.assignment1.storage.ExtendibleHash;
import com.assignment1.storage.MainMemory;
import com.assignment1.storage.SecondaryStorage;

public class ExtendibleHashing {
	
	private int totalInsertDiskAccess         ;
	private int totalSearchDiskAccess         ;
	private int totalSplitCost                ;
	private MainMemory     mainMemory         ;
	private ExtendibleHash extendibleHashfile ;
	
	//constant 
	static private String FILENAME = "uniform.txt";

	public ExtendibleHashing(int m, int capacity){
		
		totalSearchDiskAccess = 0                      ;
		totalSplitCost        = 0                      ;
		totalInsertDiskAccess = 0                      ;
		Bucket.capacity       = capacity               ;
		extendibleHashfile    = new SecondaryStorage(m);
		mainMemory            = new MainMemory()       ;
	}	
	private String padBinaryKey(Long key)
	{
		String binaryKey = Long.toBinaryString(key);
		 int length       = binaryKey.length();
		 int diff         = 20 - length;
		while(diff > 0)
		 {
			 binaryKey='0'+binaryKey;
			 diff --;
		 }
		return binaryKey;
	}
	private int getHashValue(String binaryKey)
	{
		if(mainMemory.getGlobalDepth() == 0)
		{
		   return 0;
		}
		else
		{
			String binaryHashValue = binaryKey.substring(0,mainMemory.getGlobalDepth());
			return Integer.parseInt(binaryHashValue, 2);
		}
	}
	public void insert(){
		
		 Scanner scanner;
		 try {
				scanner = new Scanner(new File(FILENAME));
				 while(scanner.hasNextLong())
				 {
					 long key         = scanner.nextLong();
					 String binaryKey = padBinaryKey(key);
				     int hashValue = getHashValue(binaryKey);
				     System.out.println("Hash Value is "+hashValue);
				     
				 }
		 }
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
	public void search(){
		
	}
	
	public void split(){
		
	}
	public void simulateExtendibleHashing(){
		System.out.println("I am inside simulate LH");
//		Bucket.capacity = 10;
		insert();
	}
	

}
