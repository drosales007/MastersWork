import java.util.*;
public class SpanTree extends Process {
    public int parent = -1; // no parent yet
    public LinkedList<Integer> children = new LinkedList<Integer>();
    int numReports = 0;
    boolean done = false;
    public SpanTree(MsgHandler initComm, boolean isRoot) {
        super(initComm);
        if (isRoot) {
            parent = myId;
	    if (neighbors.size() == 0) 
		done = true;
            else 
		sendMsg(neighbors,  "invite", myId);
        }
    }
    public synchronized void waitForDone() { // block till children known
	while (!done) myWait();
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("invite")) {
            if (parent == -1) {
            	numReports++;
                parent = src;
                sendMsg(src, "accept");
                for (int i : neighbors)
                    if ((i != myId) && (i != src))
                        sendMsg(i, "invite");
            } else
                sendMsg(src, "reject");
        } else if ((tag.equals("accept")) || (tag.equals("reject"))) {
            if (tag.equals("accept")) children.add(src);
            numReports++;
            if (numReports == neighbors.size()) 
		done = true;
        }
    }
}
