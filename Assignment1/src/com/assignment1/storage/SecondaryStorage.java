package com.assignment1.storage;

import java.awt.font.NumericShaper;
import java.util.List;
import java.util.Vector;

public class SecondaryStorage  implements LinearHash,ExtendibleHash {
	
	private Vector<Vector<Bucket>> store;
	
	public SecondaryStorage(int initialCapacity){
		
		store = new Vector<Vector<Bucket>>(initialCapacity,1);
		int i;		
		for(i=0;i<initialCapacity;i++)
		{
			Vector<Bucket> bucket = new Vector<Bucket>(1,1);
			Bucket b  = new Bucket();
			b.updateDepth(0);
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
//		return store.size();
		
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
	 public int totalBuckets(){
		
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

/*-------------------------------------------EHashing---------------------------------------------------------------------*/
//	/*insert the key in specified bucket by index */
//	public String insertEH(Long key,int index) {
//		// TODO Auto-generated method stub
//		Bucket b = store.get(index).get(0);
////		b.addElemet(key);
//		if(b.getfreespace() !=0){
//			
//			b.addElemet(key);
//			return String.valueOf(b.getDepth())+"-"+"0";
//		}
//		else{
//			
//			return  String.valueOf(b.getDepth())+"-"+"1";
//		}
				
//	}
	 

	 public String insertEH(long key,int bucket){
			
			int numberOfBucketAccess=1;
			Vector<Bucket> bucketVector =  store.get(bucket);
			//System.out.println(key+" "+bucket);
			numberOfBucketAccess =bucketVector.size();	
			
			if(bucketVector.get(numberOfBucketAccess-1).getfreespace() !=0){
				
				bucketVector.get(numberOfBucketAccess-1).addElemet(key);	
				return String.valueOf(numberOfBucketAccess)+"-"+"0"+"-"+bucketVector.get(numberOfBucketAccess-1).getDepth();
				
			}
			else{
				bucketVector.add(new Bucket());
				bucketVector.get(numberOfBucketAccess-1).updateNextBucket(numberOfBucketAccess);
				bucketVector.get(numberOfBucketAccess).addElemet(key);
//				bucketVector.get(numberOfBucketAccess).updateDepth(depth);
				return String.valueOf(numberOfBucketAccess+1)+"-"+"1"+"-"+bucketVector.get(0).getDepth();
			}
		}
				
	public int getDirectoryEntry(int index,int pointerToDirectory){
		
		int iterations = index /Bucket.capacity;
		int offset = index % Bucket.capacity;
		int temp = pointerToDirectory;
		
		while (store.get(temp).get(0).getNextBucketPointer()!=-1 && iterations > 0){
			
			temp = store.get(temp).get(0).getNextBucketPointer();
			iterations--;
		}
		return (int)store.get(temp).get(0).getElement(offset);
	}
	public int expandAndRemoveEH(int index){
		
		int depth = store.get(index).get(0).getDepth();
		store.get(index).removeAllElements();
		store.get(index).add(new Bucket());
		store.get(index).get(0).updateDepth(depth+1);
		Vector<Bucket> bucketVector = new Vector<Bucket>(1,1);
		bucketVector.add(new Bucket());
		bucketVector.get(0).updateDepth(depth+1);
		store.add(bucketVector);
		return (store.size()-1);

	}
	
	public void updateDirectoryEntries(int startIndex,int numberOfRepetitions,int pointer,int indexOfBucket ){
		
		int iterations = startIndex /Bucket.capacity;
		int offset = startIndex % Bucket.capacity;
		int temp = pointer;
		
//		if(iterations ==0){
//			while(numberOfRepetitions > 0){
//			
//				Bucket b = store.get(pointer).get(0);
//				int band = b.size() - offset+1;
//				if(band-numberOfRepetitions >0){
//					
//				}
//			}
//			
//			
//		}
		while (store.get(temp).get(0).getNextBucketPointer()!=-1 && iterations > 0){

			temp = store.get(temp).get(0).getNextBucketPointer();
			iterations--;
		}
		Bucket s = store.get(temp).get(0);
		if((s.size() - offset < numberOfRepetitions))
		{
		for(int i=offset;i<Bucket.capacity;i++)
		{
			store.get(temp).get(0).updateElement(i,indexOfBucket);
		}
        int counter = (numberOfRepetitions-(Bucket.capacity - offset))/Bucket.capacity;
        offset      = (numberOfRepetitions-(Bucket.capacity - offset))%Bucket.capacity;
        while(counter > 0)
		{
			for(int i=0;i<Bucket.capacity;i++)
			{
				store.get(temp).get(0).updateElement(i,indexOfBucket);
			}
			temp = store.get(temp).get(0).getNextBucketPointer();
			counter --;
		}
        for(int i=0;i<offset;i++)
        {
        	store.get(temp).get(0).updateElement(i,indexOfBucket);
        }
		}
		else
		{
			for(int i=offset;i<offset + numberOfRepetitions;i++)
			{
				store.get(temp).get(0).updateElement(i,indexOfBucket);
			}	
		}
		
}
	
	public int getDirectoryIndex(int bucketIndex,int index){
		
		int temp =index; 
		int counter =0;
		int length=0;
		List<Long> list= null;
//				store.get(temp).get(0).getNextBucketPointer();
		while(temp !=-1){
			
			Bucket b = store.get(temp).get(0);
			list = b.getBucketList();
			if(! list.contains(bucketIndex)){
				
				counter++;
				temp = store.get(temp).get(0).getNextBucketPointer();
			}
			else{
				
				length= counter*Bucket.capacity + list.indexOf(bucketIndex)+1024;
				break;
			}
				
			
		}
		
		return length;
		
	}
		
		
		
		
		
		
	


	@Override
	public void searchEH() {
		// TODO Auto-generated method stub
		
	}


	
	
}
