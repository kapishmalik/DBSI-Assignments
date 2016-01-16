package com.assignment1.storage;

import java.util.Vector;

public class SecondaryStorage  implements LinearHash {
	
	private Vector<Vector<Bucket>> store;
	
	SecondaryStorage(int initialCapacity){
		
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
		
		numberOfBucketAccess =bucketVector.size();	
		
		if(bucketVector.get(numberOfBucketAccess-1).getfreespace() !=0){
			
			bucketVector.get(numberOfBucketAccess-1).addElemet(key);	
			return String.valueOf(numberOfBucketAccess)+"|"+"0";
			
		}
		else{
			bucketVector.add(new Bucket());
			bucketVector.get(numberOfBucketAccess-1).updateNextBucket(numberOfBucketAccess);
			bucketVector.get(numberOfBucketAccess).addElemet(key);
			return String.valueOf(numberOfBucketAccess+1)+"|"+"1";
		}
			
		
					
	    
	}
	
	public int searchLh(long key){
		
		return 0;
	}

}
