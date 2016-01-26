package com.assignment1.storage;

import java.awt.font.NumericShaper;
import java.util.ArrayList;
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
	public int getLocalDepth(int bucketIndex){
		
		return store.get(bucketIndex).get(0).getDepth();
	}
		
		
		public int createDirectoryMemory(int index,int capacity){
			
			
			int temp;
			int buckets;
			int directoryHeader;
			if(index == -1){
				directoryHeader = store.size();
				temp = directoryHeader;
				buckets = (capacity /Bucket.capacity)+1;
				
				while(buckets >0){
					
					Bucket b = new Bucket();
					b.updateNextBucket(temp+1);
					temp++;
					Vector<Bucket> v = new Vector<Bucket>(1,1);
					v.add(b);
					store.add(v);	
					buckets--;
					
				}
				store.get(store.size()-1).get(0).updateNextBucket(-1);
				
			}
			else{
				temp = index;
				directoryHeader = index;
				
				while(store.get(temp).get(0).getNextBucketPointer() != -1){
					
					temp = store.get(temp).get(0).getNextBucketPointer();					
				}
				capacity -=  store.get(temp).get(0).getfreespace();
				buckets =  (capacity /Bucket.capacity)+1;
				store.get(temp).get(0).updateNextBucket(store.size());
				temp = store.size();
				while(buckets >0){

					Bucket b = new Bucket();
					b.updateNextBucket(temp+1);
					temp++;
					Vector<Bucket> v = new Vector<Bucket>(1,1);
					v.add(b);
					store.add(v);	
					buckets--;

				}		
				store.get(store.size()-1).get(0).updateNextBucket(-1);
				
			}
			
			return directoryHeader;
			
		}
		
	public	List<Long> getDirectoryEntries(int pointer){
		
		List<Long> list = new ArrayList<Long>();
		
		if(pointer == -1){
			
			return list;
		}
		else{
			
			int temp = pointer;
			while(temp != -1){
				
				Bucket b = store.get(temp).get(0);
				list.addAll(b.getBucketList());
				temp = store.get(temp).get(0).getNextBucketPointer(); 				
			}
			
			return list;
		}
		
	}
//	public int updateDirectoryEntries(int bufferPointer,int[] buffer,int directoryPointer,int newGlobalDepth,int prevGlobalDepth,int oldBucketIndex){
//		
//		int bucketAccesses =0;
//		int temp = directoryPointer;
//		int i = bufferPointer;
//		int listIndex=0;
//		
//		while(i < buffer.length && temp != -1 ){
//			
//			
//			int localDepth = getLocalDepth(buffer[i]);
//		     System.out.println(newGlobalDepth +"is global Depth and Local Depth is "+localDepth);
//		     int noOfRep    = (int) (Math.pow(2, (newGlobalDepth - localDepth)));
//		     int bufferEntry = buffer[i];
//		     int buckets = noOfRep /Bucket.capacity;
//		     int offset = noOfRep % Bucket.capacity;
//		     
//		     while(buckets > 0 ){
//		    	 Bucket b= store.get(temp).get(0);
//		    	 for(int j=0;j<Bucket.capacity;j++){
//		    		 
//		    		 b.updateElement(j, bufferEntry);		    		 
//		    	 }
//		    	 bucket--;
//		     }
//		     if(bufferEntry == oldBucketIndex){
//		    	 
//		     }
//		     else{
//		    	 
//		    	 while( noOfRep > 0 &){
//		    		 
//		    	 }
//		    	 
//		     }
//					
//		}
//		
//		return bucketAccesses;
//	}
//		
	
	public int updateDirectoryEntries(int[] buffer,int pointer){
		
		int bucketAccesses =0;
		int temp = pointer;
		int j = 1024;
		int counter=0;
		Bucket b = null;
		
		int buckets = (buffer.length -1024)/Bucket.capacity;
		int offset = (buffer.length -1024)%Bucket.capacity;
		//System.out.println(buffer.length);
		System.out.println("Buckets "+buckets+" offset "+offset);
		while(buckets >0 ){
			
			counter = 0;
			b= store.get(temp).get(0);
			bucketAccesses++;			
			while(counter < Bucket.capacity ){
				
				//System.out.println(counter+" "+b.getBucketList().size());
				b.updateElement(counter, buffer[j]);
				j++;
				counter++;
				
			}
			//System.out.println("Temp before value is "+temp);
			temp = b.getNextBucketPointer();
			//System.out.println("Temp after value is "+temp);
			buckets--;
		
		}
		counter = 0;
		if(offset != 0)
		b= store.get(temp).get(0);
		
		while(counter < offset){
			
			b.updateElement(counter, buffer[j]);
			j++;	
			counter++;
			
		}	
		
		return (bucketAccesses+1);
	}
		
	


	@Override
	public void searchEH() {
		// TODO Auto-generated method stub
		
	}


	
	
}
