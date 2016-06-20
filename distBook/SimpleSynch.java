import java.util.LinkedList;
public class SimpleSynch extends Process implements Synchronizer {
    int pulse = 0;
    MsgHandler prog;
    boolean rcvEnabled [];
    LinkedList<Integer> pendingS = new LinkedList<Integer>();
    LinkedList<Integer> pendingR = new LinkedList<Integer>();
    public SimpleSynch(MsgHandler initComm) {
        super(initComm);
        rcvEnabled = new boolean[n];
        for (int i = 0; i < n; i++)
            rcvEnabled[i] = false;
    }
    public synchronized void initialize(MsgHandler initProg) {
        prog = initProg;
        pendingS.addAll(neighbors);
        notifyAll();
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        while (!rcvEnabled[src]) myWait();
        pendingR.remove(new Integer(src));
        if (pendingR.isEmpty()) notifyAll();
        if (!tag.equals("synchNull"))
            prog.handleMsg(m, src, tag);
        rcvEnabled[src] = false;
    }
    public synchronized void sendMessage(int destId, String tag, int msg) {     
        if (pendingS.contains(destId)) {
            pendingS.remove(new Integer(destId));
            sendMsg(destId, tag, msg);
        } else
            System.err.println("Error: sending two messages/pulse");
    }
    public synchronized void nextPulse() {
        while (!pendingS.isEmpty()) { // finish last pulse by sending null
            int dest = pendingS.remove();          
            sendMsg(dest, "synchNull", 0);
        }
        pulse ++;
        Util.println("**** new pulse ****:" + pulse);
        pendingS.addAll(neighbors);
        pendingR.addAll(neighbors);
        for (int i = 0; i < n; i++)
            rcvEnabled[i] = true;
        notifyAll();
        while (!pendingR.isEmpty()) myWait();
    }
}
