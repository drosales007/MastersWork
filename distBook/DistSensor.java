import java.util.*;
public class DistSensor extends Process implements Runnable,Sensor {
	final static int red = 0, green = 1;
	int candidate[], color[],G[];
	boolean finished = false, haveToken = false;
	LinkedList q = new LinkedList();	
	public DistSensor(VCTagger initComm) {
		super(initComm); 
		candidate = new int[n]; color = new int[n]; G = new int[n];
		Arrays.fill(color, red); Arrays.fill(G, 0);
		if (myId == Symbols.coordinator) haveToken=true;
		new Thread(this).start();
	}
	public synchronized void run(){
		while (!finished) {
			while (!haveToken) myWait();
			handleToken();
		}
	}
	public synchronized void handleToken() {
		while (color[myId] == red) {
			while (q.isEmpty() && !finished) myWait();
			if (q.isEmpty() && finished) {
				((SensorUser) app).globalPredicateFalse(myId); return;
			}
			candidate = (int[]) q.removeFirst();
			if (candidate[myId] > G[myId]) {
				G[myId] = candidate[myId]; color[myId] = green;
			}
		}
		for (int j = 0; j < n; j++)
			if ((j != myId) && (candidate[j] >= G[j])) {
				G[j] = candidate[j]; color[j] = red;
			}
		int j = Util.searchArray(color, red);
		if (j != -1) sendToken(j);
		else { ((SensorUser) app).globalPredicateTrue(G); finished = true; }
	}
	public synchronized void handleMsg(Msg m, int src, String tag) {
		if (tag.equals("Token")) {
			G = (int []) m.getMsgBuf().removeFirst();
			color = (int []) m.getMsgBuf().removeFirst();                 
			haveToken = true;
		} else if (tag.equals("finished")) finished = true;
	}
	void sendToken(int j) {
		((VCTagger) comm).simpleSendMsg(j, "Token", G, color);      
		haveToken = false;
	}
	public synchronized void localPredicateTrue(VectorClock vc) {
		q.add(vc.v); notifyAll();
	}
}
