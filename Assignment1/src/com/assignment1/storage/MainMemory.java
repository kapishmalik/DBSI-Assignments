package com.assignment1.storage;

public class MainMemory {

	private int mainMemoryArray[] ;
	private int globalDepth       ;
	private int size              ;  
	private int directoryPointer  ;
	public MainMemory()
	{
		globalDepth = 0;
		mainMemoryArray = new int[1024];
		size        = 1;
		mainMemoryArray[0] = 0;
		
	}
	
	public int getGlobalDepth() {
		return globalDepth;
	}

	public void setGlobalDepth(int globalDepth) {
		this.globalDepth = globalDepth;
	}

	public int getDirectoryEntry(int index)
	{
		if(index < 1024)
		{
			return mainMemoryArray[index];
		}
		else
		{
			//get from Secondary Storage
			return 0;
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
}
