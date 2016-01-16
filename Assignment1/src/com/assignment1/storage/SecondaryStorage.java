package com.assignment1.storage;

import java.util.Vector;

public class SecondaryStorage  implements LinearHash {
	
	private Vector<Vector<Bucket>> store;
	
	public SecondaryStorage(int initialCapacity){
		
		store = new Vector<Vector<Bucket>>(initialCapacity,1);
		int i;		
		for(i=0;i<initialCapacity;i++)
		{
			Vector<Bucket> bucket = new Vector<Bucket>(1,1);
			Bucket b  = new Bucket();
			bucket.add(b);
			store.add(bucket);
	    }
		
	}
	
	
	public String insertLh(long key,int bucket){
		
		int numberOfBucketAccess=1;
		Vector<Bucket> bucketVector =  store.get(bucket);
		//System.out.println(key+" "+bucket);
		numberOfBucketAccess =bucketVector.size();	
		
		if(bucketVector.get(numberOfBucketAccess-1).getfreespace() !=0){
			
			bucketVector.get(numberOfBucketAccess-1).addElemet(key);	
			return String.valueOf(numberOfBucketAccess)+"-"+"0";
			
		}
		else{
			bucketVector.add(new Bucket());
			bucketVector.get(numberOfBucketAccess-1).updateNextBucket(numberOfBucketAccess);
			bucketVector.get(numberOfBucketAccess).addElemet(key);
			return String.valueOf(numberOfBucketAccess+1)+"-"+"1";
		}
			
		
					
	    
	}
	public void expandAndRemove(int index){
		
		store.get(index).removeAllElements();
		store.get(index).add(new Bucket());
		Vector<Bucket> bucketVector = new Vector<Bucket>(1,1);
		bucketVector.add(new Bucket());
		store.add(bucketVector);
		
	}
	
	public Vector<Bucket> getBucket(int index)
	{
		Vector<Bucket> temp = new Vector<Bucket>();
		int i;
		for(i=0;i<store.get(index).size();i++)
		{
			temp.add(store.get(index).get(i));
		}
		return temp;
	}
	public int searchLh(long key){
		
		return 0;
	}

     public int totalBuckets(){
		
		int i,totalbuckets=0;
		for(i= 0;i<store.size();i++){
			
			totalbuckets += store.get(i).size();
			
		}
		
		return totalbuckets;
	}

}
