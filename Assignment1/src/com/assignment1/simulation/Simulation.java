package com.assignment1.simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import com.assignment1.extendiblehashing.ExtendibleHashing;
import com.assignment1.linearhashing.LinearHashing;

public class Simulation {
	
	public static void main(String args[]){
		
    /*	final long numberOfKeys = 100000;		
		long counter = 0;
		long temp;
		BufferedWriter out =null,out1 = null,out2 = null, out3 = null;
		
		try{
			
		//	out = new BufferedWriter(new FileWriter("uniform.txt"));
		//	out2 = new BufferedWriter(new FileWriter ("readuniform.txt"));
			out1 = new BufferedWriter(new FileWriter("highbit.txt"));
			out3 = new BufferedWriter(new FileWriter ("readhighbit.txt"));
		}catch(Exception e){
			
		}				
		Random generator = new Random();
		/*
		try{		
		
			while(counter <numberOfKeys){
			
				temp = generator.nextInt(800000);
				out.write(temp+"\n");
				out2.write(temp+"\n");
				out.flush();
				out2.flush();
				counter++;
					
				}  
		}catch(Exception e){
			
		}finally{
			if(out != null){
				try{
					out.close();
				}
				catch(Exception e ){
					
				} 
			}
			
			try{
				counter = 0;
				while(counter <60000){
					
					temp = 700000+generator.nextInt(100000);
					out1.write(temp+"\n");
					out3.write(temp+"\n");
					out1.flush();
					out3.flush();
					counter++;
						
					}
				counter = 0;
				while(counter <40000){

					temp = generator.nextInt(700000);
					out1.write(temp+"\n");
					out3.write(temp+"\n");
					out1.flush();
					out3.flush();
					counter++;						
					
				}
				
			}catch(Exception e){
				
			}finally{
				
				if(out != null){
					try{
						out.close();
					}
					catch(Exception e ){
						
					}
				}
				
			}
		
			
			
			
			
			
		}*/
	Simulate s = new Simulate();
		
		
		
	}


}
class Simulate{
	
	
	Simulate(){
		
//		Create two objects for LH and EH
	/*	LinearHashing.FILENAME = "uniform.txt";
		LinearHashing.storageUtilFileName          = "LH40StorageUniform.csv";
		LinearHashing.storageSuccessSearchFileName = "LH40SuccessSearchUniform.csv";
		LinearHashing.storageSplitFileName         = "LH40SplitUniform.csv";
		LinearHashing.readFileName                 = "readuniform.txt";
		LinearHashing lh = new LinearHashing(2,40);
		lh.simulateLinearHashing();
		
		LinearHashing.FILENAME = "uniform.txt";
		LinearHashing.storageUtilFileName          = "LH10StorageUniform.csv";
		LinearHashing.storageSuccessSearchFileName = "LH10SuccessSearchUniform.csv";
		LinearHashing.storageSplitFileName         = "LH10SplitUniform.csv";
		LinearHashing.readFileName                 = "readuniform.txt";
		LinearHashing lh2 = new LinearHashing(2,10);
		lh2.simulateLinearHashing(); */
		
	/*	LinearHashing.FILENAME = "highbit.txt";
		LinearHashing.storageUtilFileName          = "LH10StorageHighBit.csv";
		LinearHashing.storageSuccessSearchFileName = "LH10SuccessSearchHighBit.csv";
		LinearHashing.storageSplitFileName         = "LH10SplitHighBit.csv";
		LinearHashing.readFileName                 = "readhighbit.txt";
		LinearHashing lh = new LinearHashing(2,10);
		lh.simulateLinearHashing();  */
		
		LinearHashing.FILENAME = "highbit.txt";
		LinearHashing.storageUtilFileName          = "LH40StorageHighBit.csv";
		LinearHashing.storageSuccessSearchFileName = "LH40SuccessSearchHighBit.csv";
		LinearHashing.storageSplitFileName         = "LH40SplitHighBit.csv";
		LinearHashing.readFileName                 = "readhighbit.txt";
		LinearHashing lh2 = new LinearHashing(2,40);
		lh2.simulateLinearHashing(); 
		
		
		/*ExtendibleHashing.FILENAME                     = "uniform.txt";
		ExtendibleHashing.storageUtilFileName          = "EH40StorageUniform.csv";
		ExtendibleHashing.storageSuccessSearchFileName = "EH40SuccessSearchUniform.csv";
		ExtendibleHashing.storageSplitFileName         = "EH40SplitUniform.csv";
		ExtendibleHashing.readFileName                 = "readuniform.txt";
		ExtendibleHashing eh = new ExtendibleHashing(1,40); 
		eh.simulateExtendibleHashing();   
		
		ExtendibleHashing.FILENAME                     = "uniform.txt";
		ExtendibleHashing.storageUtilFileName          = "EH10StorageUniform.csv";
		ExtendibleHashing.storageSuccessSearchFileName = "EH10SuccessSearchUniform.csv";
		ExtendibleHashing.storageSplitFileName         = "EH10SplitUniform.csv";
		ExtendibleHashing.readFileName                 = "readuniform.txt";
		ExtendibleHashing eh2 = new ExtendibleHashing(1,30); 
		eh2.simulateExtendibleHashing();   */
		
	
	/*	ExtendibleHashing.FILENAME                     = "highbit.txt";
		ExtendibleHashing.storageUtilFileName          = "EH40StorageHighBit.csv";
		ExtendibleHashing.storageSuccessSearchFileName = "EH40SuccessSearchHighBit.csv";
		ExtendibleHashing.storageSplitFileName         = "EH40SplitHighBit.csv";
		ExtendibleHashing.readFileName                 = "readhighbit.txt";
		ExtendibleHashing eh = new ExtendibleHashing(1,40);
		eh.simulateExtendibleHashing(); */
		
	}
	
}
