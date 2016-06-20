import java.util.*;

public class VCTagger extends Process {
    public VectorClock vc;
   
    public VCTagger(MsgHandler comm) {
        super(comm);
        vc = new VectorClock(n, myId);      
    }
    public void sendMsg(int destId, Object ... objects) {
    	List<Object> objectList = Util.getLinkedList(objects) ;    	
    	super.sendMsg(destId, "VectorClockMsg", vc.v, objectList);
    	vc.sendAction();
    }
    public void simpleSendMsg(int destId, Object ... objects) { 	
    	List<Object> objectList = Util.getLinkedList(objects) ;
    	objectList.add("SimpleMsg");
    	super.sendMsg(destId,"SimpleMsg", objectList);
     }
    public synchronized void handleMsg(Msg m, int src, String tag)  {    	
    	LinkedList<Object> args = m.getMsgBuf();
    	if(m.tag.equals("VectorClockMsg")) {    		
    		int [] w = (int []) args.removeFirst();
    		vc.receiveAction(w);
    	}
    	Msg appMsg = (Msg) args.removeFirst();
    	app.handleMsg(appMsg, appMsg.src, appMsg.tag);
    }
   
   
}
