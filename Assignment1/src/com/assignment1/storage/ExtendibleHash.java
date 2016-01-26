package com.assignment1.storage;

import java.util.Vector;

public interface ExtendibleHash {
	
	public String insertEH(Long key,int index);
	public void searchEH();
//	public int expandAndRemove(int index);
	public int expandAndRemoveEH(int index);
	public Vector<Bucket> getBucket(int n);
	public int getDirectoryEntry(int index,int pointerToDirectory);
	public void updateDirectoryEntries(int startIndex,int numberOfRepetitions,int pointer,int indexOfBucket );
	public int getDirectoryIndex(int bucketIndex, int directoryPointer);
}
