
public class Simulation {
	
	public static void main(String[] args){
		
		PageReferenceGeneration p = new PageReferenceGeneration();
		
		p.BlockNestedLoopGeneration("D","E",25,1000,"query1Ref.txt");
		p.BlockNestedLoopGeneration("D","P",25,500,"query2Ref.txt");
		
		int[] deptlevel = new int[2];
		deptlevel[0] =1;
		deptlevel[1]=4;
		p.SingleLoopGeneration("P", 500, 5000, "D", 25, 2, deptlevel, "query3Ref.txt");
		
	}

}
