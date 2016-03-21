import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PageReferenceGeneration {

	
	public void BlockNestedLoopGeneration(String outer,String inner,int outerBlocks,int innerBlocks,String fileName)
	{
		BufferedWriter out = null;
		try {
			System.out.println("I am creating file");
			 out = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=1;i<=outerBlocks;i++)
		{
			for(int j=1;j<=innerBlocks;j++)
			{
				try {
					out.write(outer+":"+i+"-"+inner+":"+j);
					out.write("\n");
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(outer+i+" "+inner+j);
			}
			
		}
	}
	public void SingleLoopGeneration(String outer,int outerBlocks,int outerRecords,String inner,int innerBlocks,int noOfLevels,int []levelBlocks,String fileName)
	{
		BufferedWriter out = null;
		try {
			 out = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random r = new Random();
	    for(int i=1;i<=outerBlocks;i++)
		{
			
			for(int k =1;k<=outerRecords;k++){
			
				try {
					out.write(outer+":"+i+"-"+"NULL");
					out.write("\n");
					out.flush();
					out.write("I"+inner+"L:"+1+",B:"+1+"-"+"NULL");
					out.write("\n");
					out.flush();
					for(int j=2;j<=noOfLevels;j++){					
						
						int random = r.nextInt(levelBlocks[j-1])+1;
						out.write("I"+inner+"L:"+j+",B:"+random+"-"+"NULL");
						out.write("\n");
						out.flush();
						//System.out.println("I"+inner+"L"+j+"B"+a+" NULL");
						
					}				
						
						int random = r.nextInt(innerBlocks)+1;
						out.write(outer+":"+i+"-"+inner+":"+random);
						out.write("\n");
						out.flush();
//						System.out.println(outer+i+" "+inner+a);
										
			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//System.out.println(outer+i+" NULL");
			//System.out.println("I"+inner+"L"+1+"B"+1+" NULL");
			
					
		}
		}
	}
}


