package com.assignment1.storage;

import java.util.List;
import java.util.Vector;

public interface ExtendibleHash {
	
	public String insertEH(long key,int index);
	public int  searchEH(long key,int bucketIndex);
//	public int expandAndRemove(int index);
	public int expandAndRemoveEH(int index);
	public Vector<Bucket> getBucket(int n);
	public int getDirectoryEntry(int index,int pointerToDirectory);
	public void updateDirectoryEntries(int startIndex,int numberOfRepetitions,int pointer,int indexOfBucket );
	public int getDirectoryIndex(int bucketIndex, int directoryPointer);
	public int getLocalDepth(int bucketIndex);
	public	List<Long> getDirectoryEntries(int pointer);
	public int createDirectoryMemory(int index,int capacity);
	public int updateDirectoryEntries(int[] totalBuffer, int directoryPointer);
}
