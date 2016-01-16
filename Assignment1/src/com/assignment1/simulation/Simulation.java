package com.assignment1.simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import com.assignment1.linearhashing.LinearHashing;

public class Simulation {
	
	public static void main(String args[]){
		
		final long numberOfKeys = 100000;		
		long counter = 0;
		long temp;
		BufferedWriter out =null,out1 = null;
		
		try{
			
			out = new BufferedWriter(new FileWriter("uniform.txt"));
			out1 = new BufferedWriter(new FileWriter("highbit.txt"));
			
		}catch(Exception e){
			
		}				
		Random generator = new Random();
		
		try{		
		
			while(counter <numberOfKeys){
			
				temp = generator.nextInt(800000);
				out.write(temp+"\n");
				out.flush();
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
					out1.flush();
					counter++;
						
					}
				counter = 0;
				while(counter <40000){

					temp = generator.nextInt(700000);
					out1.write(temp+"\n");
					out1.flush();
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
		
			
			
			
			
			
		}
		Simulate s = new Simulate();
		
		
		
	}

}

class Simulate{
	
	
	Simulate(){
		
//		Create two objects for LH and EH
		
		LinearHashing lh = new LinearHashing(2,10);
		lh.simulateLinearHashing();
		
	}
	
}
