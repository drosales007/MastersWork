import java.lang.reflect.*; 
public class AlgoTesterThread extends Thread {
	String args[];
	public AlgoTesterThread(String args[]) {
		this.args = args;
	}
	public void run()  {
		MsgHandler p = null;
		try {
			p = new Linker(args);			
			for (int i = 3; i< args.length; i++) {
				Class classLoaded = Class.forName(args[i]);
				Constructor mainCons = classLoaded.getConstructor(MsgHandler.class);
				p =  (MsgHandler) mainCons.newInstance(p);
			}
			p.init(null);	
		} catch (Exception e) {			
			e.printStackTrace();
		}
		// p.close();
	}
}
