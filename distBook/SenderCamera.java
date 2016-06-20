import java.util.*;
public class SenderCamera extends Process implements Camera {
	public Color myColor = Color.white;	
	boolean closed[];
	ArrayList<TreeMap<Integer, Msg>> outChan;
	Integer seqNo[] = null;
	public SenderCamera(MsgHandler initComm) {
		super(initComm);
		closed = new boolean[n]; Arrays.fill(closed, false);
		outChan = new ArrayList<TreeMap<Integer, Msg>>();
		for (int i = 0; i<n; i++) 
			outChan.add(new TreeMap<Integer, Msg>());
		seqNo = new Integer[n]; Arrays.fill(seqNo,0);		
	}
	public synchronized void globalState() {
		myColor = Color.red;
	    ((CamUser) app).localState(); // record local State;		
		sendMsg(neighbors, "marker", myId);  //  send Markers
	}
	public synchronized void handleMsg(Msg m, int src, String tag){
		int nid = neighbors.indexOf(src);
		if (tag.equals("marker")) {
			if (myColor == Color.white) globalState();
			closed[nid] = true;
			if (isDone()) System.out.println("Done recording");
		} else if (tag.equals("ack")) {
			int seqNo = m.getMessageInt();
			outChan.get(nid).remove(seqNo);	    
		} else { // application message 
			LinkedList<Object> args = m.getMsgBuf();
			int seqNo = (Integer) args.removeFirst();	
			Msg appMsg = (Msg) args.removeFirst();
			if ((myColor == Color.white) && (tag.equals("white")))
				sendMsg(src, "ack", seqNo);
			if ((myColor == Color.white) && (tag.equals("red")))
				globalState();
			app.handleMsg(appMsg, appMsg.src, appMsg.tag);
		}
	}
	public void sendMsg(int destId, Object ...objects ){
		String tag = (String) objects[0];
		if ((tag.equals("marker")) || (tag.equals("ack")))
			comm.sendMsg(destId, objects);
		else {// send seq numbers with app msgs
			int nid = neighbors.get(destId);
			seqNo[nid]++;			
			if (myColor == Color.white) 
				outChan.get(nid).put(seqNo[nid], new Msg(myId, destId, tag, Util.getLinkedList(objects)));
			comm.sendMsg(destId, myColor.toString(), seqNo[nid], objects);			
		}
	}
	boolean isDone() {
		if (myColor == Color.white) return false;
		for (int i = 0; i < n; i++)
			if (!closed[i]) return false;
		return true;
	}
}