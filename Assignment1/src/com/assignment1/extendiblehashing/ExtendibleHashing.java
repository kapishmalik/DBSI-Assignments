package com.assignment1.extendiblehashing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import com.assignment1.storage.Bucket;
import com.assignment1.storage.ExtendibleHash;
import com.assignment1.storage.MainMemory;
import com.assignment1.storage.SecondaryStorage;

public class ExtendibleHashing {
	
	private int noOfRecordsInserted                 ;         
	private int totalSplitCost                      ;
	private MainMemory     mainMemory               ;
	private ExtendibleHash extendibleHashfile       ;
	private int noOfDataBuckets                     ;
	private int noOfSuccessSearch                   ;
	private int totalSearchDiskAccess               ;
	//constant 
	//Constants
		public static String FILENAME       ;
		public static String readFileName   ;
		private Scanner scanner1            ;
		public static String storageUtilFileName         ;
		public static String storageSuccessSearchFileName;
		public static String storageSplitFileName        ;

	public ExtendibleHashing(int m, int capacity){
		
		totalSearchDiskAccess = 0                      ;
		totalSplitCost        = 0                      ;
		noOfRecordsInserted   = 0                      ;
		Bucket.capacity       = capacity               ;
		extendibleHashfile    = new SecondaryStorage(m);
		mainMemory            = new MainMemory()       ;
		this.noOfDataBuckets  = 0                      ;
		this.noOfSuccessSearch= 0                      ;
		this.totalSearchDiskAccess = 0                 ; 
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
			String binaryHashValue;
			if(mainMemory.getGlobalDepth() > 20)
			{
				binaryHashValue= binaryKey.substring(0,20);
			}
			else
			{
				binaryHashValue= binaryKey.substring(0,mainMemory.getGlobalDepth());
			}
			return Integer.parseInt(binaryHashValue, 2);
		}
	}
	public void insert(){
		
		 Scanner scanner;
		 BufferedWriter out1 = null,out2 = null,out3 = null;
		 try {
				scanner = new Scanner(new File(FILENAME));
				scanner1= new Scanner(new File(readFileName));
				out1 = new BufferedWriter(new FileWriter(this.storageUtilFileName));
				out2 = new BufferedWriter(new FileWriter(this.storageSuccessSearchFileName));
				out3 = new BufferedWriter(new FileWriter(this.storageSplitFileName));
				 while(scanner.hasNextLong())
				 {
					 long key         = scanner.nextLong();
					 System.out.println("Key to be inserted now is "+key);
					 String binaryKey = padBinaryKey(key);
				     int hashValue = getHashValue(binaryKey);
				     //get index from Directory Entry
				     int index = mainMemory.getDirectoryEntry(extendibleHashfile,hashValue); 
				     System.out.println("Bucket no trying to insert "+index);
				     String DepthAndStatus = extendibleHashfile.insertEH(key,index);
				     String[] del   = DepthAndStatus.split("-");
				     int localDepth = Integer.parseInt(del[2]);
				     int status     = Integer.parseInt(del[1]); 
				     int accessCost = Integer.parseInt(del[0]);
				   System.out.println(DepthAndStatus);
				     if(status == 1)
				     {
				       if(mainMemory.getGlobalDepth() > localDepth)
				       {
				        // do something  -  add one new Bucket
				    	  System.out.println("Global depth greater than local depth"+localDepth+"  "+mainMemory.getGlobalDepth());
				    	   splitBucket(index);
				          
				       }
				       else
				       {
				        //do something   - Double Size of Directory and add one new Bucket
				    	   //mainMemory.doubleDirectory(extendibleHashfile);
				    	System.out.println("Global depth equal to local depth"+localDepth+"  "+mainMemory.getGlobalDepth());
				    	   splitBucketAndDoubleDirectorySize(index);
				    	   
				       }
				       
				     }
				     else
				     {
				    	 // Successful Insertion - Go to Next record
				    	// System.out.println("Record inserted Successfully");
				    	 
				     }
				     this.noOfRecordsInserted ++;
				     this.noOfDataBuckets = this.getDataBuckets();
					  float storageUtilization = ((float)this.noOfRecordsInserted)/(this.noOfDataBuckets * Bucket.capacity);
					  out1.write(this.noOfRecordsInserted+" ,"+storageUtilization+"\n");
					  out1.flush();
					  out3.write(this.noOfRecordsInserted+" ,"+this.totalSplitCost+"\n");
				      out3.flush();
				     if(noOfRecordsInserted % 5000 == 0)
					  {
				    	 this.totalSearchDiskAccess = 0;
				    	 this.noOfSuccessSearch     = 0;
						  this.search();
						  float avgSuccessSearchCost = ((float)this.totalSearchDiskAccess)/(this.noOfSuccessSearch);
						  float splitCost = this.totalSplitCost;
						  out2.write(this.noOfRecordsInserted+" ,"+avgSuccessSearchCost+"\n");
						  out2.flush();
					  }
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
		int prevIndex = -1;
		//System.out.println("New Bucket Index and Old Bucket Index "+newBucketIndex+"  "+bucketIndex);
		splitCost += mainMemory.doubleDirectory(extendibleHashfile, bucketIndex, newBucketIndex);
		mainMemory.printMainMemory();
		for(int i=0;i<bucketVector.size();i++)
	     {
			 List<Long> reHashKeys = bucketVector.get(i).getBucketList();
			 System.out.println("Re hashing Keys are ");
			 System.out.println(" "+reHashKeys);
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
		       if(prevIndex != index)
				{
		    	   splitCost += accessCost;
				}
		       prevIndex = index;
		      
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
		int prevIndex = -1;
		//System.out.println("New Bucket Index and Old Bucket Index "+newBucketIndex+"  "+bucketIndex);
		splitCost += mainMemory.updateDirectoryEntries(extendibleHashfile,newBucketIndex,bucketIndex,localDepth);
		mainMemory.printMainMemory();
		for(int i=0;i<bucketVector.size();i++)
	     {
			 List<Long> reHashKeys = bucketVector.get(i).getBucketList();
			 //System.out.println(" "+reHashKeys);
			 System.out.println("Re hashing Keys are ");
			 System.out.println(" "+reHashKeys);
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
		       if(prevIndex != index)
				{
		    	   splitCost += accessCost;
				}
		       prevIndex = index;
		      
			} 
	     }
		this.totalSplitCost += splitCost;
		
	}
	public void search(){
		
		int counter      = 0;
		
		while(counter <50){
			long keyToBeSearched = scanner1.nextLong();
			String binaryKey = padBinaryKey(keyToBeSearched);
		    int hashValue = getHashValue(binaryKey);
		    if(hashValue >= 1024)
		    {
		    	this.totalSearchDiskAccess ++;
		    }
		    int index = mainMemory.getDirectoryEntry(extendibleHashfile,hashValue);
		    int count = extendibleHashfile.searchEH(keyToBeSearched,index);
		    if(count != 0)
		    {
		    	this.noOfSuccessSearch++;
		    }
		    this.totalSearchDiskAccess += count;
		    counter++;
		}
	}
	
	
	private int getDataBuckets()
	{
		this.noOfDataBuckets = mainMemory.getDataBuckets(extendibleHashfile);
		return this.noOfDataBuckets;
	}
	public void simulateExtendibleHashing(){
		insert();
		System.out.println("I am inside simulate EH");
	//	Bucket.capacity = 10;
		
	}
	

}
