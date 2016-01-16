package com.assignment1.linearhashing;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class LinearHashing {

	private int level                   ;
	private int nextPtr                 ;
	private int totalInsertDiskAccess   ;
	private int totalSearchDiskAccess   ;
	private int m                       ;
	private int noOfRecordsInserted     ;
	private int noOfSuccessSearch       ;
	
	//Constants
	static private String FILENAME = "uniform.txt";
//    private String fileName      ;
	
    public LinearHashing(int m)
	{
		this.level                = 0;
		this.nextPtr              = 0;
		this.m                    = m;
		this.noOfRecordsInserted  = 0;
//		this.fileName             = fileName;
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
		if(this.nextPtr == Math.pow(2,this.getLevel()))
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
		
		 FileInputStream in = null;
		 Scanner scanner;
		 int count = 0;
		try {
			scanner = new Scanner(new File(FILENAME));
			 while(scanner.hasNextLong())
			 {
				 long key = scanner.nextLong();
				 System.out.println("I am waiting for Venky to Complete. Record = "+key);
				 int mod1  = (int) (Math.pow(2,this.level)*m);
				 int mod2  = (int) (Math.pow(2,this.level+1)*m);
				 int hashValue = (int) (key % mod1);
				 String diskAccessAndStatus = "|";
				 int diskAccess=0,status=0;
				 if((hashValue) >= this.getNextPtr())
				 {
					//diskAccessAndStatus = insertLh(key,hashValue);
					 
				 }
				 else
				 {
					hashValue = (int) (key %mod2);
				//	diskAccessAndStatus = insertLh(key,hashValue);
					
				 }
				 String[] del = diskAccessAndStatus.split("|");
				 if(del.length ==2)
				 {
					 diskAccess = Integer.parseInt(del[0]); 
					 status     = Integer.parseInt(del[1]);
				 }
				 else
				 {
					 //wrong return code do something
				 }
				 this.totalInsertDiskAccess += diskAccess;
				  if(status ==  -1)
				 {
				    // overflow Happened. Call Split function
				     setNextPtr();
				 }
				
				 count++;
			     System.out.println("Count is "+count);
			 }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	private int split()
	{
		//Vector<Bucket> = getBucket();
		return 0;
	}
	private void search()
	{
	  /* 
	     
	*/	
	}
	public void simulateLinearHashing()
	{
		System.out.println("I am inside simulate LH");
		insert();
	}
	
}
