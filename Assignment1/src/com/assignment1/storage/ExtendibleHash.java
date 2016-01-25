package com.assignment1.storage;

import java.util.Vector;

public interface ExtendibleHash {
	
	public String insertEH(Long key,int index);
	public void searchEH();
	public int expandAndRemove(int index);
	public Vector<Bucket> getBucket(int n);
}
