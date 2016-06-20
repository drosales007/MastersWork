import java.io.IOException;

public class MsgFeeder extends Process {
	String recvMsgFileName = null;
	public MsgFeeder(MsgHandler initComm) {	
		// MsgHandler c  = null;
		super(initComm);// initComm ignored
		// XXX: initComm needs to be ignores
		recvMsgFileName = prop.getProperty("recvMsgFile");		
	}
	public void init(MsgHandler app){
		this.app = app;
		startFeeding();
	}
	public void sendMsg(int destId, Object ... objects) {
		println("Sending Message to " + destId + Util.getLinkedList(objects).toString());	
		println("Message not sent in this mode");
	}
	private void startFeeding(){
		for (int pid : neighbors)
			try {
				(new FeederThread(pid, recvMsgFileName, this)).start();
			} catch (IOException e) {				
				e.printStackTrace();
			}		    	
	}
	public void close() { }	
	public void turnPassive() { }	
}
