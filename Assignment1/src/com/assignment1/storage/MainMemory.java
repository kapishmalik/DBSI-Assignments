package com.assignment1.storage;

import java.util.List;

public class MainMemory {

	private int mainMemoryArray[] ;
	private int globalDepth       ;
	private int size              ;  
	private int directoryPointer  ;
	public MainMemory()
	{
		globalDepth        = 0;
		mainMemoryArray    = new int[1024];
		size               = 1;
        directoryPointer   = -1;
		mainMemoryArray[0] = 0;
		
	}
	
	public int getGlobalDepth() {
		return globalDepth;
	}

	/*public void setGlobalDepth(int globalDepth) {
		this.globalDepth = globalDepth;
	}*/

	public int getDirectoryEntry(ExtendibleHash extendibleHashfile,int index)
	{
		if(index < 1024)
		{
			return mainMemoryArray[index];
			
		}
		else
		{
			//get from Secondary Storage
			//System.out.println("Global Depth"+this.globalDepth+" Passing index "+index);
			return  extendibleHashfile.getDirectoryEntry(index-1024,directoryPointer);
			
		}
	}
	
	public int getDirectoryIndex(ExtendibleHash extendibleHashfile,int bucketIndex)
	{
		for(int i=0;i<1024;i++)
		{
			if(mainMemoryArray[i] == bucketIndex)
			{
				return i;
			}
		}
		
		return extendibleHashfile.getDirectoryIndex(bucketIndex,directoryPointer);
		
	}
	public int updateDirectoryEntries(ExtendibleHash extendibleHashfile,int indexOfBucket,int bucketIndex,int localDepth) {
		
		int directoryIndex = getDirectoryIndex(extendibleHashfile,bucketIndex);
		int startPt = (int) ((Math.pow(2, globalDepth - localDepth))/2)+directoryIndex;
		int endPt   = ((int) (Math.pow(2, globalDepth - localDepth))) - 1+directoryIndex;
		int i       = 0;
		int counter = 0;
		if(startPt < 1024 && endPt < 1024)
		{
			for(i=startPt;i<=endPt;i++)
			{
				mainMemoryArray[i] = indexOfBucket;
			}
		}
		else if(startPt < 1024)
		{
			for(i=startPt;i<1024;i++)
			{
				mainMemoryArray[i] = indexOfBucket;
			}
			//update directery entry in Main Memory
			extendibleHashfile.updateDirectoryEntries(0,endPt-1023,directoryPointer,indexOfBucket);
			counter = (endPt - 1024)/Bucket.capacity;
		}
		else
		{
		    extendibleHashfile.updateDirectoryEntries(startPt-1024,endPt-startPt+1,directoryPointer,indexOfBucket);	
		    counter = (endPt-startPt)/Bucket.capacity + (startPt-1024)/Bucket.capacity;
		}
		return counter;
	}

	public void printMainMemory()
	{
		int temp;
		if(size > 1024)
		{
			temp = 1024;
			//System.out.println("Size Print > 1024"+this.size);
		}
		else
		{
			temp = size;
		}
		for(int i=0;i<temp;i++)
		{
			//System.out.println("Main Memory index value i is"+i+" "+mainMemoryArray[i]);
		}
	}
	private void printTotalBuffer(int []totalBuffer)
	{
		System.out.println("Total Buffer Length"+totalBuffer.length+"  "+this.size);
		for(int i=0;i<this.size;i++)
		{
			System.out.print("  "+totalBuffer[i]+"  ");
		}
		System.out.println(" ");
	}
	private int fetchDirectoryAndReMap(ExtendibleHash extendibleHashfile,int prevSize,int prevGlobalDepth,int oldBucketIndex,int newBucketIndex,List<Long> secondaryStorageDirectory)
	{
		
		int []buffer;
		int []totalBuffer;
		buffer = new int[prevSize];
		totalBuffer = new int[this.size];
		int counter = 0;
		if(size > 1024)
		{
			for(int i=0;i<1024;i++)
		   {
				buffer[i] = mainMemoryArray[i];
	       }
			int j=0;
			for(int i=1024;i<secondaryStorageDirectory.size()+1024;i++)
			{
				buffer[i] = (int)secondaryStorageDirectory.get(j).longValue();
				j++;
			}
			//System.out.println("Size is greater than 1024 PrevSize and Buffer are"+prevSize+"  "+buffer.length);
		   //update Directory in Main Memory and in Secondary Storage too.	
			int i = 0;
			    j = 0;
			while(j < totalBuffer.length)
			{
				 int localDepth = extendibleHashfile.getLocalDepth(buffer[i]);
			    // System.out.println(this.globalDepth +"is global Depth and Local Depth is "+localDepth);
			     int noOfRep    = (int) (Math.pow(2, (this.globalDepth - localDepth)));
			     int temp = buffer[i];
			     if(temp == oldBucketIndex)
			     {
			    	// System.out.println("J value is "+j);
			    	 if(j<1024)
			    	 mainMemoryArray[j] = temp;
			    	 totalBuffer[j]  = temp;
			    	 j++;
			    	 if(j<1024)
			    	 mainMemoryArray[j] = newBucketIndex;
			    	 totalBuffer[j]   = temp;
			    	 i++;
			    	 j++;
			    	 
			     }
			     else
			     {
			    	 
			    	 
			     while(noOfRep > 0)
			     {
			    	 if(j<1024)
			    	 mainMemoryArray[j] = temp;
			    	 totalBuffer[j] = temp;
			    	 j++;
			    	 noOfRep --;
			     }
			    i += (int) Math.pow(2,prevGlobalDepth - localDepth);
			   }
			}
			printTotalBuffer(totalBuffer);
			counter = extendibleHashfile.updateDirectoryEntries(totalBuffer,this.directoryPointer);
			
		}
		if(size <= 1024)
		{
			for(int i=0;i<prevSize;i++)
			{
				buffer[i] = mainMemoryArray[i];
		    }
			//read buffer value and update main Memory Directory Entry Array
			int j = 0;
			//System.out.println("prev Size is "+prevSize);
			int accum = 0;
			for(int i=0;i<prevSize;)
			{
			     int localDepth = extendibleHashfile.getLocalDepth(buffer[i]);
			    // System.out.println(this.globalDepth +"is global Depth and Local Depth is "+localDepth);
			     int noOfRep    = (int) (Math.pow(2, (this.globalDepth - localDepth)));
			     
			     accum +=noOfRep;
			     int temp = buffer[i];
			     
			     if(temp == oldBucketIndex)
			     {
			    //	 System.out.println("J value is "+j);
			    	 mainMemoryArray[j] = temp;
			    	 totalBuffer[j] = temp;
			    	 j++;
			    	 mainMemoryArray[j] = newBucketIndex;
			    	 totalBuffer[j] = newBucketIndex;
			    	 i++;
			    	 j++;
			    	 
			     }
			     else
			     {
			    //	 System.out.println("Total Repititions are "+accum);
			    	 
			     while(noOfRep > 0)
			     {
			    	// System.out.println("J value is in while and no of rep are "+j+"  "+noOfRep);
			    	 mainMemoryArray[j] = temp;
			    	 totalBuffer[j] = temp;
			    	 j++;
			    	 noOfRep --;
			     }
			    i += (int) Math.pow(2,prevGlobalDepth - localDepth);
			   }
		}
			printTotalBuffer(totalBuffer);
			
		}
		return counter;
		
}  
	public int doubleDirectory(ExtendibleHash extendibleHashfile,int oldBucketIndex,int newBucketIndex) {
		// TODO Auto-generated method stub
		int prevGlobalDepth   = this.globalDepth;
		this.globalDepth     +=  1;
		int prevSize          = this.size;
		this.size            *=  2;
		int count = 0;
	
		List<Long> secondaryStorageDirectory = null;
		if(size > 1024)
		{
			//need to create directory entry buckets.
			secondaryStorageDirectory = extendibleHashfile.getDirectoryEntries(directoryPointer);
			directoryPointer = extendibleHashfile.createDirectoryMemory(directoryPointer,size - prevSize);
		    count = fetchDirectoryAndReMap(extendibleHashfile,prevSize,prevGlobalDepth,oldBucketIndex,newBucketIndex,secondaryStorageDirectory);
		}
		else
		{
			count = fetchDirectoryAndReMap(extendibleHashfile,prevSize,prevGlobalDepth,oldBucketIndex,newBucketIndex,secondaryStorageDirectory);
		}
		return count;
	}
	
	
}
