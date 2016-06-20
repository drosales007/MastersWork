public class LockApp extends Process {
		Lock lock;
    public LockApp(MsgHandler initComm) {
        super(initComm);
		lock = (Lock) comm;
    }
    public synchronized void init(MsgHandler app) {
    	comm.init(app);
		try {
			for (int i = 0; i < 5; i++) {				
				Util.mySleep(2000);
				lock.requestCS();				
				System.out.println(comm.getMyId() + " is in CS ******");
				Util.mySleep(2000);		
				lock.releaseCS();
				System.out.println(comm.getMyId() + " is not in CS");
			}
		} catch (Exception e) {
			lock.close();
			e.printStackTrace();
		}
	}
}
