package com.assignment1.extendiblehashing;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

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
				     //get entry from Directory Entry
				     int index = mainMemory.getDirectoryEntry(extendibleHashfile,hashValue);
				     String DepthAndStatus = extendibleHashfile.insertEH(key,index);
				     String[] del   = DepthAndStatus.split("-");
				     int localDepth = Integer.parseInt(del[0]);
				     int status     = Integer.parseInt(del[1]); 
				     if(status == 1)
				     {
				       if(mainMemory.getGlobalDepth() > localDepth)
				       {
				        // do something  -  add one new Bucket
				    	   splitBucket(index,key);
				          
				       }
				       else
				       {
				        //do something   - Double Size of Directory and add one new Bucket
				       
				       }
				     }
				     else
				     {
				    	 
				     }
				     
				 }
		 }
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
	public void splitBucket(int bucketIndex,long key)
	{
		Vector<Bucket> bucketVector = extendibleHashfile.getBucket(bucketIndex)        ;
		int localDepth              = bucketVector.get(0).getDepth()                   ;
		int newBucketIndex          = extendibleHashfile.expandAndRemoveEH(bucketIndex);
		mainMemory.updateDirectoryEntries(extendibleHashfile,newBucketIndex,bucketIndex,localDepth);
		int i;
		for(i=0;i<bucketVector.size();i++)
	     {
			 List<Long> reHashKeys = bucketVector.get(i).getBucketList();
			 System.out.print(" "+reHashKeys);
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
