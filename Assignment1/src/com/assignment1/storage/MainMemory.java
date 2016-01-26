package com.assignment1.storage;

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

	public void setGlobalDepth(int globalDepth) {
		this.globalDepth = globalDepth;
	}

	public int getDirectoryEntry(ExtendibleHash extendibleHashfile,int index)
	{
		if(index < 1024)
		{
			return mainMemoryArray[index];
		}
		else
		{
			//get from Secondary Storage
			return  extendibleHashfile.getDirectoryEntry(index-1024,directoryPointer);
			
		}
	}
	private void fetchDirectoryAndReMap(SecondaryStorage memory,int startIndex)
	{
		int i,flag = 0;
		int []buffer;
		buffer = new int[this.size];
		if(size > 1024)
		{
			for(i=0;i<1024;i++)
		   {
				buffer[i] = mainMemoryArray[i];
	       }
			
			
		}
		if(size < 1024)
		{
			for(i=0;i<1024;i++)
			{
				buffer[i] = mainMemoryArray[i];
		    }
			
		}
		// Secondary Memory function will be there to get directory block
		
		
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
	public void updateDirectoryEntries(ExtendibleHash extendibleHashfile,int indexOfBucket,int bucketIndex,int localDepth) {
		
		int directoryIndex = 0;
		//directoryIndex = getDirectoryIndex(extendibleHashfile,bucket);
		int startPt = (int) ((Math.pow(2, globalDepth - localDepth))/2)+directoryIndex;
		int endPt   = ((int) (Math.pow(2, globalDepth - localDepth))) - 1+directoryIndex;
		int i       = 0;
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
		}
		else
		{
		    extendibleHashfile.updateDirectoryEntries(startPt-1024,endPt-startPt+1,directoryPointer,indexOfBucket);	
		}
	}
	
}
