package com.assignment1.storage;
import java.util.*;
public class Bucket {
	
	private int nextBucket = -1;
	private List<Long> list;
	static int capacity;
	private int freeSpace;
	
	Bucket( ){
		
		list = new ArrayList<Long>();
		freeSpace = capacity;
	}
	
	public void addElemet(long key){
		
		list.add(key);
		freeSpace = Bucket.capacity -list.size(); 
		
	}
	public int size(){
	
		return list.size();
				
	}
	public int getfreespace(){
		
		return freeSpace;
	}
	public void updateNextBucket(int index){
		
		nextBucket = index;		
		
	}
	public int find(long key){
		
		if(list.contains(key))
			
			return 1;
		else
			return 0;
	}

}