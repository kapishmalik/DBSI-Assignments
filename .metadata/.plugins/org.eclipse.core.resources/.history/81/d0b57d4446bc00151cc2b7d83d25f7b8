package com.assignment1.linearhashing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LinearHashing {

	private int level              ;
	private int nextPtr            ;
	private int noOfBuckets        ;
	private int noOfRecordsInserted;
    private String fileName        ;
	
    LinearHashing(String fileName)
	{
		this.level                = 0;
		this.nextPtr              = 0;
		this.noOfBuckets          = 2;
		this.noOfRecordsInserted  = 0;
		this.fileName             = fileName;
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
	
	public void insert()
	{
		 FileInputStream in = null;
		 try {
	         in = new FileInputStream(this.fileName);
	         int c;
	         while ((c = in.read()) != -1) {
	        	 //logic for insertion
	        	 System.out.println("I am waiting for Venky to Complete. Record 1 = "+c);
	         }
		 } catch (Exception e) {
			
		 }
		 finally {
	         if (in != null) {
	            try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	         }
		 }
	}
	public void search()
	{
		
	}
	public void simulateLinearHashing()
	{
		
	}
	
}
