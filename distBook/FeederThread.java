import java.io.*;
public class FeederThread extends Thread {
	int channel;
	MsgHandler process = null;
	ObjectInputStream inp;
	
	public FeederThread(int channel, String msgFile, MsgHandler process) throws IOException {
		this.channel = channel;
		this.process = process;
		inp = new ObjectInputStream (new FileInputStream(new File(msgFile)));
	}
	
	public void run() {
		Msg m = null;
		try {
			m = (Msg) inp.readObject();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		while (m != null) {
			process.executeMsg(m);
			
		}
	}
}
