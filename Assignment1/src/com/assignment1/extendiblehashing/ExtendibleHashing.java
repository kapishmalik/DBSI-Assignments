package com.assignment1.extendiblehashing;

import java.io.File;
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
		 int diff         = 25 - length;
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
				     //get index from Directory Entry
				     int index = mainMemory.getDirectoryEntry(extendibleHashfile,hashValue);
				     String DepthAndStatus = extendibleHashfile.insertEH(key,index);
				     String[] del   = DepthAndStatus.split("-");
				     int localDepth = Integer.parseInt(del[2]);
				     int status     = Integer.parseInt(del[1]); 
				     int accessCost = Integer.parseInt(del[0]);
				   //  System.out.println(DepthAndStatus);
				     if(status == 1)
				     {
				       if(mainMemory.getGlobalDepth() > localDepth)
				       {
				        // do something  -  add one new Bucket
				    	 //  System.out.println("Global depth greater than local depth"+localDepth+"  "+mainMemory.getGlobalDepth());
				    	   splitBucket(index);
				          
				       }
				       else
				       {
				        //do something   - Double Size of Directory and add one new Bucket
				    	   //mainMemory.doubleDirectory(extendibleHashfile);
				    	//  System.out.println("Global depth equal to local depth"+localDepth+"  "+mainMemory.getGlobalDepth());
				    	   splitBucketAndDoubleDirectorySize(index);
				    	   
				       }
				     }
				     else
				     {
				    	 // Successful Insertion - Go to Next record
				    	// System.out.println("Record inserted Successfully");
				    	 
				     }
				     totalInsertDiskAccess ++;
				 }
		 }
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
	private void splitBucketAndDoubleDirectorySize(int bucketIndex) {
		// TODO Auto-generated method stub
		Vector<Bucket> bucketVector = extendibleHashfile.getBucket(bucketIndex)        ;
		int newBucketIndex          = extendibleHashfile.expandAndRemoveEH(bucketIndex);
		int splitCost               = 2;
		//System.out.println("New Bucket Index and Old Bucket Index "+newBucketIndex+"  "+bucketIndex);
		splitCost += mainMemory.doubleDirectory(extendibleHashfile, bucketIndex, newBucketIndex);
		mainMemory.printMainMemory();
		for(int i=0;i<bucketVector.size();i++)
	     {
			 List<Long> reHashKeys = bucketVector.get(i).getBucketList();
			 //System.out.println("Re hashing Keys are ");
			 //System.out.println(" "+reHashKeys);
			 for(int j=0;j<reHashKeys.size();j++)
			{
			   String binaryKey = padBinaryKey(reHashKeys.get(j));
		       int hashValue = getHashValue(binaryKey);
		       //System.out.println("Hash Value is "+hashValue);
		       //get entry from Directory Entry
		       int index = mainMemory.getDirectoryEntry(extendibleHashfile,hashValue);
		       String cost = extendibleHashfile.insertEH(reHashKeys.get(j),index);
		       String[] del   = cost.split("-");
		       int accessCost = Integer.parseInt(del[0]);
		       //System.out.println(cost);
		       splitCost += accessCost;
			} 
	     }
		this.totalSplitCost += splitCost;
		
	}
	public void splitBucket(int bucketIndex)
	{
		Vector<Bucket> bucketVector = extendibleHashfile.getBucket(bucketIndex)        ;
		int localDepth              = bucketVector.get(0).getDepth()                   ;
		int newBucketIndex          = extendibleHashfile.expandAndRemoveEH(bucketIndex);
		int splitCost = 2;
		//System.out.println("New Bucket Index and Old Bucket Index "+newBucketIndex+"  "+bucketIndex);
		splitCost += mainMemory.updateDirectoryEntries(extendibleHashfile,newBucketIndex,bucketIndex,localDepth);
		mainMemory.printMainMemory();
		for(int i=0;i<bucketVector.size();i++)
	     {
			 List<Long> reHashKeys = bucketVector.get(i).getBucketList();
			 //System.out.println(" "+reHashKeys);
			 //System.out.println("Re hashing Keys are ");
			 //System.out.println(" "+reHashKeys);
			 for(int j=0;j<reHashKeys.size();j++)
			{
			   String binaryKey = padBinaryKey(reHashKeys.get(j));
		       int hashValue = getHashValue(binaryKey);
		       //System.out.println("Hash Value is "+hashValue);
		       //get entry from Directory Entry
		       int index = mainMemory.getDirectoryEntry(extendibleHashfile,hashValue);
		       String cost = extendibleHashfile.insertEH(reHashKeys.get(j),index);
		       String[] del   = cost.split("-");
		       int accessCost = Integer.parseInt(del[0]);
		       //System.out.println(cost);
		       splitCost += accessCost;
			} 
	     }
		this.totalSplitCost += splitCost;
		
	}
	public void search(){
		
	}
	
	public void split(){
		
	}
	public void simulateExtendibleHashing(){
		insert();
		System.out.println("I am inside simulate EH");
		System.out.println("Total Disk Access"+this.totalInsertDiskAccess);
		System.out.println("Total Split Cost"+this.totalSplitCost);
//		Bucket.capacity = 10;
		
	}
	

}
