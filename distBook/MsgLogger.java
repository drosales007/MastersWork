import java.io.*;
import java.util.LinkedList;
public class MsgLogger extends Process {
	String recvMsgFileName = null;
	String sendMsgFileName = null;
	ObjectOutputStream rFile = null, sFile = null;
	
	public MsgLogger(MsgHandler initComm) throws FileNotFoundException, IOException {
		super(initComm);
		recvMsgFileName = prop.getProperty("recvMsgFile");
		sendMsgFileName = prop.getProperty("sendMsgFile");		
		rFile = new ObjectOutputStream (new FileOutputStream(new File(recvMsgFileName)));
		sFile = new ObjectOutputStream (new FileOutputStream(new File(sendMsgFileName)));
		sendMsgFileName = prop.getProperty("sendMsgFile");
	}	
	public synchronized void handleMsg(Msg m, int src, String tag) {	
		println("Message received" + m.toString());	
		try {
			rFile.writeObject(m);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	public void sendMsg(int destId, Object ... objects) {
		println("Sending Message to " + destId + Util.getLinkedList(objects).toString());	
		try {
			LinkedList<Object> objectList = Util.getLinkedList(objects);
			String tag = (String) objectList.removeFirst();
			sFile.writeObject(new Msg(myId, destId, tag, objectList));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		super.sendMsg(destId, objects);
	}
}