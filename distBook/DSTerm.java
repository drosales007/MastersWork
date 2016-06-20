public class DSTerm extends Process {
	final static int passive = 0, active = 1;
	int state = passive;
	int D = 0;
	int parent = -1;
	boolean envtFlag;
	public DSTerm(MsgHandler initComm) {
		super(initComm);
		envtFlag = (myId == Symbols.coordinator);
	}
	
	public synchronized void handleMsg(Msg m, int src, String tag) {
		if (tag.equals("signal")) {
			D = D - 1;        
			if (D == 0) {
				if (envtFlag) {
					System.out.println("Termination Detected");
					comm.close();
				}
				else if (state == passive) {
					sendMsg(parent, "signal");
					parent = -1;
				}
			}
		} else { // application message
			state = active;
			if ((parent == -1) && !envtFlag) {
				parent = src;        
			} else
				sendMsg(src, "signal");
		}
	}
	public void sendMsg(int destId, Object ...objects ){   
		D = D + 1;
		comm.sendMsg(destId, objects);
	}
	public synchronized void close() { // app turned passive
		state = passive;
		if ((D == 0) && (parent != -1)) {
			sendMsg(parent, "signal");
			parent = -1;
		}
		
	}
}
