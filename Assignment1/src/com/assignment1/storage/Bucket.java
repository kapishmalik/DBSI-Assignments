package com.assignment1.storage;
import java.util.*;
public class Bucket {
	
	private int nextBucket = -1;
	private List<Long> list;
	public static int capacity;
	private int freeSpace;
	private int depth;
	
	Bucket( ){
		
		list = new ArrayList<Long>();
		freeSpace = capacity;
	}
	
	public void addElemet(long key){
		
		list.add(key);
		freeSpace = Bucket.capacity -list.size(); 
		
	}
	public void updateElement(int index,long bucketIndex)
	{
		list.set(index, bucketIndex);
	}
	public List<Long> getBucketList()
	{
		return list;
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
	public int  getDepth(){
		
		return depth;
		
	}
	public void  updateDepth(int depth){
		
		this.depth = depth;
		
	}
	public int getNextBucketPointer(){
		
		return nextBucket;
	}
	public long getElement(int index){

		return list.get(index);
	}

}
