import java.lang.reflect.*;

public class LockTester {
	public static void main(String[] args) {
		MsgHandler comm;
		Lock lock = null;
		try {
			comm = new Linker (args);
			Class classLoaded = Class.forName(args[3]);
			Constructor mainCons = classLoaded.getConstructor(MsgHandler.class);
			lock = (Lock) mainCons.newInstance(comm);
			lock.init(null);
			for (int i = 0; i < 5; i++) {				
				Util.mySleep(2000);
				lock.requestCS();				
				System.out.println(comm.getMyId() + " is in CS ****** for the " + i + "th time");
				Util.mySleep(2000);		
				lock.releaseCS();
				System.out.println(comm.getMyId() + " is not in CS");
			}
		} catch (Exception e) {
			e.printStackTrace();
			lock.close();
		}
                finally {
			//lock.close();
                 }
	        System.out.println( " finished.");
	}
}
