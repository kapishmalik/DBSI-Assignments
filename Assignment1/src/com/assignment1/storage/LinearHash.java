package com.assignment1.storage;

public interface LinearHash {
	
	public String insertLh(long key ,int bucket);
	public int searchLh(long key);
	public void expand();
	
}
