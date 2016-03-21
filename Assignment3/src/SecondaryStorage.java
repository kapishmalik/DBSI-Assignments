import java.util.Vector;

public class SecondaryStorage {
	
	private Vector<Page> store= new Vector<Page>();
	
	
	public void initialize(){
		
		Page temp=null;
		
		for(int i =1;i<=1000;i++){
			
			temp = new Page();
			temp.init("E"+i);
			store.add(temp);
			
		}
		for(int i=1;i<=25;i++){
			
			temp = new Page();
			temp.init("D"+i);
			store.add(temp);
		}
		for(int i=1;i<=500;i++){
			
			temp = new Page();
			temp.init("P"+i);
			store.add(temp);
		}
		temp = new Page();
		temp.init("I:E:L:1:B:1");
		store.add(temp);
		for(int i=1;i<12;i++){
			
			temp = new Page();
			temp.init("I:E:L:2:B:"+i);
			store.add(temp);			
		}
		for(int i=1;i<=334;i++){
			
			temp = new Page();
			temp.init("I:E:L:3:B:"+i);
			store.add(temp);	
		}
		temp = new Page();
		temp.init("I:D:L:1:B:1");
		store.add(temp);
		for(int i=1;i<4;i++){
			
			temp = new Page();
			temp.init("I:D:L:2:B:"+i);
			store.add(temp);			
		}
		
	}
	public Page get(int index){
		
		return store.get(index);
	}

}
