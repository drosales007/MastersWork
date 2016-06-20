import java.lang.reflect.*; 
public class AlgoRunner {
	public static void main(String[] args) throws Exception {
		MsgHandler p = new Linker(args);
		for (int i = 3; i< args.length; i++) {
			Class classLoaded = Class.forName(args[i]);
			Constructor mainCons = classLoaded.getConstructor(MsgHandler.class);
			p =  (MsgHandler) mainCons.newInstance(p);
		}
		p.init(null);		
	}
}
