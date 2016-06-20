public class DinMutex extends Process implements Lock {
	private static final int thinking = 0, hungry = 1, eating = 2;
	Boolean fork[] = null,  dirty[] = null, request[] = null;
	public int myState = thinking;
	public DinMutex(MsgHandler initComm) {
		super(initComm);
		fork = new Boolean[n]; dirty = new Boolean[n];
		request = new Boolean[n];
		for (int i=0; i < n; i++) {
			if (myId > neighbors.get(i)) {
				fork[i] = false; request[i] = true;
			} else { fork[i] = true; request[i] = false; }
			dirty[i] = true;
		}
	}
	public synchronized void requestCS() {
		myState = hungry;
		if (haveForks()) myState = eating;
		else
			for (int i=0; i < n; i++)
				if (request[i] && !fork[i]) 
					sendBool(i, "Request", request[i]); 
		while (myState != eating) myWait();
	}
	public synchronized void releaseCS() {
		myState = thinking;
		for (int i=0; i < n; i++){
			dirty[i] = true;
			if (request[i]) sendBool(i, "Fork", fork[i]); 
		}
	}
	boolean haveForks() {
		for (int i=0; i < n; i++)
			if (!fork[i]) return false;
		return true;
	}
	void sendBool(int nid, String tag, Boolean b) {
		int dest = neighbors.get(nid);
		sendMsg(dest, tag);
		b = false;
	}
	public synchronized void handleMsg(Msg m, int src, String tag) {
		int nid = neighbors.indexOf(src);
		if (tag.equals("Request")) {
			request[nid] = true;
			if ((myState != eating) && fork[nid] && dirty[nid]) {
				sendBool(nid, "Fork", fork[nid]);
				if (myState == hungry)
					sendBool(nid, "Request", request[nid]);
			}
		} else if (tag.equals("Fork")) {
			fork[nid] = true; dirty[nid] = false;
			if (haveForks()) 
				myState = eating; 
		}
	}
}
