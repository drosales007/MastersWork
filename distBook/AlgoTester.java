import java.util.Vector;
// @param1 N
// last param: basename
// remaining parameters as passed to the thread
public class AlgoTester {
	public static void main(String[] args) throws Exception {		
		int newSize = args.length + 2;	
		String baseName = args[args.length-1];
		int N = Integer.parseInt(args[0]);
		for (int i=0; i<N; i++) {
			String [] newArgs = new String[newSize];			
			newArgs[0] = new String(baseName);
			newArgs[1] =  (new Integer(i)).toString();			
			for (int j = 0; j < args.length; j++)			
				newArgs[j+2] = new String(args[j]);				
			for (String s : newArgs)
				System.out.print(s + " ");
			(new AlgoTesterThread(newArgs)).start();			
		}
	}
}
