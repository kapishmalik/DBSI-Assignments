package com.assignment1.storage;

import java.util.List;
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
		
<<<<<<< 829ebada195e6c32217a6c3b4aec1fb558e0d3ee
		return 0;
	}

     public int totalBuckets(){
=======
	public int totalBuckets(){
>>>>>>> 7f6ca4d6bd80fa1971ce31106ea0a2a1d98f8641
		
		int i,totalbuckets=0;
		for(i= 0;i<store.size();i++){
			
			totalbuckets += store.get(i).size();
			
		}
		
		return totalbuckets;
	}
	public int searchLh(long key,int bucket){
		
		Vector<Bucket> temp = store.get(bucket);
		int i;
		
		for(i=0;i<temp.size();i++){
			
			List<Long> bucketElements = temp.get(i).getBucketList();
			if(bucketElements.contains(key))
				return i+1;
			
			
			
		}
		
		return -1;
		
	}

}
