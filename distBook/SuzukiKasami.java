import java.util.*;
public class SuzukiKasami extends Process implements Lock {
	public boolean havePrivilege;
	public boolean requesting = false;
	public boolean rcvdPrivilege = false;	
	int[] RN;   // Largest sequence number ever received from other processes.
	int[] LN;   // Sequence number of most recent request granted from each process.
	LinkedList<Integer> queue = new LinkedList<Integer>();
	public SuzukiKasami(MsgHandler initComm) {
		super(initComm);
		havePrivilege = (myId == 0);
		RN = new int[n]; Arrays.fill(RN, -1);
		LN = new int[n]; Arrays.fill(LN, -1);
	}
	public synchronized void requestCS() {
		requesting = true;
		if(!havePrivilege) {
			RN[myId]++;
			rcvdPrivilege = false;
			sendMsg(neighbors,"request", RN[myId]);
			while (!rcvdPrivilege) myWait();
			havePrivilege = true;
		}
	}    
	public synchronized void releaseCS() {
		LN[myId] = RN[myId];
		for(int j=0; j<n; j++)
			if(j!=myId && !queue.contains(j) && RN[j]==LN[j]+1)
				queue.add(j);
		if(queue.size() != 0) {
			havePrivilege = false; 
			int dest = queue.removeFirst();
			sendMsg(dest, "privilege", queue, LN);       
		}
		requesting = false;
	}
	public synchronized void handleMsg(Msg m, int src, String tag) {
		if (tag.equals("request")) {
			RN[src] = Util.max(RN[src], m.getMessageInt());
			if( havePrivilege && !requesting && RN[src] == LN[src] + 1) {
				havePrivilege = false;
				sendMsg( src, "privilege", queue, LN);       
			}
		} else if (tag.equals("privilege")) {
			LinkedList<Object> args = m.getMsgBuf();
			queue = (LinkedList<Integer>) args.removeFirst();
			LN = (int [])args.removeFirst();
			rcvdPrivilege = true;
		}
	}
}
