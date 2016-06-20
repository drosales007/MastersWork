import java.util.*; import java.io.*;import java.net.Socket;
import java.rmi.*; import java.rmi.server.*;
public class ReplayableProcess extends Process implements MsgHandler {
	final int simpleMode = 0, logMode = 1, replayMode = 2;
	int numEvents = 0; // number of internal events executed so far
	int targetEvents; // internal events that need to be executed before the next receive
	int targetProc;
	int mode;
	String logFile = null;
	PrintStream pout;
	public ReplayableProcess(MsgHandler initComm, int mode) throws Exception {
		super(initComm);
		this.mode = mode;
		if (mode != simpleMode)
			logFile = prop.getProperty("logFile");
		if (mode == replayMode){			
			String nextRecvEvent = Util.readLine(logFile);			
			StringTokenizer s = new StringTokenizer(nextRecvEvent);
			targetEvents = Integer.parseInt(s.nextToken());
			targetProc = Integer.parseInt(s.nextToken());
		}
		else if (mode == logMode)		
			pout = new PrintStream(new FileOutputStream(logFile));	
	}
	public synchronized void enter(){
		if (mode == replayMode) 			
			while (numEvents == targetEvents) myWait();
		numEvents++;		
	}
	public synchronized void executeMsg(Msg m) {
		if (mode == replayMode) 
			while ((numEvents < targetEvents)|| (targetProc != m.src)) 
				myWait();
		else if (mode == logMode) {
			pout.println(numEvents + " " + m.src);
			numEvents = 0;
		}
		super.executeMsg(m);
	}
	public synchronized void close(){
		if (mode == logMode){
			pout.println(numEvents + " " + myId); // number of events before termination
		}
	}
}