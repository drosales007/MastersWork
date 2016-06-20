import java.util.*;import java.io.*;
public class Connector {
	ServerSocket listener;
	Socket[] link;
	public ObjectInputStream[] dataIn;
	public ObjectOutputStream[] dataOut;
	Name myNameclient;
	public void Connect(String basename, int myId, List<Integer> neighbors)
	throws Exception {
		int numNeigh = neighbors.size();
		dataIn = new ObjectInputStream[numNeigh];
		dataOut = new ObjectOutputStream[numNeigh];
		
		/* accept connections from all the smaller processes */
		for (int pid : neighbors) {
			if (pid  < myId) {
				InputStream is = Global.connect.getInputStream();
				ObjectInputStream din = new ObjectInputStream(is);
				Integer hisId = (Integer) din.readObject();
				int i = neighbors.indexOf(hisId);
				String tag = (String) din.readObject();
				if (tag.equals("hello")) {
					link[i] = s;
					dataIn[i] = din;
					dataOut[i] = new ObjectOutputStream(s.getOutputStream());
				}
			}
		}
		/* contact all the bigger processes */
		for (Integer pid : neighbors) {
			if (pid > myId) {
				InetSocketAddress addr = myNameclient.searchName(basename + pid, true);
				int i = neighbors.indexOf(pid);
				link[i] = new Socket(addr.getHostName(), addr.getPort());
				dataOut[i] = new ObjectOutputStream(link[i].getOutputStream());
				/* send a hello message to P_i */
				dataOut[i].writeObject(new Integer(myId));
				dataOut[i].writeObject(new String("hello"));
				dataOut[i].flush();
				dataIn[i] = new ObjectInputStream(link[i].getInputStream());
			}	
		}
	}
	int getLocalPort(int id) {return Symbols.ServerPort + 20 + id;	}
	public void closeSockets() {
		try {
			listener.close();
			for (Socket s : link) s.close();
			myNameclient.clear();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
