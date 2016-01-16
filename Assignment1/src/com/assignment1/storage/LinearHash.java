package com.assignment1.storage;

import java.util.Vector;

public interface LinearHash {
	
	public String insertLh(long key ,int bucket);
	public int searchLh(long key);
	public void expandAndRemove(int index); 
	public Vector<Bucket> getBucket(int n);
	public int totalBuckets();
}
