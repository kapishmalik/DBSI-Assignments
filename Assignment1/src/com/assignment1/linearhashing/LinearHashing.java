package com.assignment1.linearhashing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import com.assignment1.storage.*;

public class LinearHashing {

	private int level                   ;
	private int nextPtr                 ;
	private int totalInsertDiskAccess   ;
	private int totalSearchDiskAccess   ;
	private int totalSplitCost          ;
	private int m                       ;
	private int noOfRecordsInserted     ;
	private int noOfSuccessSearch       ;
	private LinearHash linearHashfile   ;
	private int noOfDataBuckets         ;
	//Constants
	public static String FILENAME       ;
	public static String readFileName   ;
    public static String storageUtilFileName;
	public static String storageSuccessSearchFileName;
	public static String storageSplitFileName;
	
	private Scanner scanner1;
    public LinearHashing(int m,int capacity)
	{
		this.level                = 0;
		this.nextPtr              = 0;
		this.m                    = m;
		this.noOfRecordsInserted  = 0;
		this.totalInsertDiskAccess= 0;
		this.totalSearchDiskAccess= 0;
		this.noOfSuccessSearch    = 0;
		totalSplitCost            = 0;
//		this.fileName             = fileName;
		Bucket.capacity  = capacity ;
		linearHashfile = new SecondaryStorage(m);
		this.noOfDataBuckets      = 0;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getNextPtr() {
		return nextPtr;
	}
	
	public void setNextPtr() {
		
		if(this.nextPtr+1 == Math.pow(2,this.getLevel())*m)
		{
            this.nextPtr = 0;	
            this.setLevel(this.level+1);
		}
		else
		{
			 this.nextPtr ++;
		}
	}
	
	private void insert()
	{
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
				 long key = scanner.nextLong();
//				 System.out.println("I am waiting for Venky to Complete. Record = "+key);
				 int mod1  = (int) (Math.pow(2,this.level)*m);
				 int mod2  = (int) (Math.pow(2,this.level+1)*m);
				 int hashValue = (int) (key % mod1);
				// System.out.println(mod1+" "+mod2);
				 String diskAccessAndStatus = "-";
				 int diskAccess=0,status=0;
				 if((hashValue) >= this.getNextPtr())
				 {
					diskAccessAndStatus = linearHashfile.insertLh(key,hashValue);
					 
				 }
				 else
				 {
					hashValue = (int) (key %mod2);
					diskAccessAndStatus = linearHashfile.insertLh(key,hashValue);
					
				 }
				// System.out.println(diskAccessAndStatus);
				 String[] del = diskAccessAndStatus.split("-");
				 	 diskAccess = Integer.parseInt(del[0]); 
					 status     = Integer.parseInt(del[1]);
				//	 System.out.println("Status Value is "+status);
				 
				 this.totalInsertDiskAccess += diskAccess;
				  if(status ==  1)
				 {
				    // overflow Happened. Call Split function
					  split();
				     setNextPtr();
				     
				     
					  
				 }
				
				  this.noOfRecordsInserted++;
				  this.noOfDataBuckets = this.linearHashfile.totalBuckets();
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
					  out2.write(this.noOfRecordsInserted+" ,"+avgSuccessSearchCost+"\n");
					  out2.flush();
				  }
			    
			//	 System.out.println("Next Ptr "+nextPtr);
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}
	private int split()
	{
		//System.out.println("I am in split");
		Vector<Bucket> bucketVector= linearHashfile.getBucket(this.nextPtr);
		linearHashfile.expandAndRemove(this.nextPtr);
		int mod2  = (int) (Math.pow(2,this.level+1)*m);
		int i,j;
		String diskAccessAndStatus = "-";
		 int diskAccess=1,prevHashValue = -1;
		for(i=0;i<bucketVector.size();i++)
		{
			List<Long> reHashKeys = bucketVector.get(i).getBucketList();
			diskAccess++;
			
			for(j=0;j<reHashKeys.size();j++)
			{
				long key = reHashKeys.get(j);
				int hashValue = (int) (key%mod2);
				
				diskAccessAndStatus=linearHashfile.insertLh(key, hashValue);
				String[] del = diskAccessAndStatus.split("-");
				if(hashValue != prevHashValue)
				{
				 diskAccess += Integer.parseInt(del[0]);
				}
				prevHashValue = hashValue;
				 
			}
		}
		totalSplitCost += diskAccess;
		
		return 0;
	}
	private void search()
	{
		int counter = 0;
		while(counter <50){
			long keyToBeSearched = scanner1.nextLong();
			int mod1             = (int) (Math.pow(2,this.level)*m);
			int mod2             = (int) (Math.pow(2,this.level+1)*m);
			int hashValue = (int)keyToBeSearched % mod1;
			int diskAccess = 0;
			if(hashValue >= this.nextPtr)
			{
				//call the search function in Secondary Storage
				diskAccess = this.linearHashfile.searchLh(keyToBeSearched,hashValue);
			}
			else
			{
				//re-hash with i+1 hash  function
				hashValue = (int)keyToBeSearched % mod2;
				//call the search function in Secondary Storage
				diskAccess = this.linearHashfile.searchLh(keyToBeSearched,hashValue);
			}
			if(diskAccess > 0)
			{
				this.noOfSuccessSearch++;
				this.totalSearchDiskAccess += diskAccess;
				System.out.println("Successful key found "+keyToBeSearched);
				
			}
			else
			{
				System.out.println("UnSuccessful key not found "+keyToBeSearched);	
			}
			counter++;	
			
		}
	}
	public void simulateLinearHashing()
	{
		System.out.println("I am inside simulate LH");
//		Bucket.capacity = 10;
		insert();
		search();
		this.noOfDataBuckets = this.linearHashfile.totalBuckets();
		System.out.println("Total Disk Access are "+this.totalInsertDiskAccess);
		System.out.println("Total Split Cost is "+this.totalSplitCost);
		System.out.println("Total Records inserted "+this.noOfRecordsInserted);
		System.out.println("Total buckets "+this.noOfDataBuckets);
		System.out.println("Total no of successful search "+this.noOfSuccessSearch);
		System.out.println("Total successful search disk Access "+this.totalSearchDiskAccess);
		
	}
	
}
