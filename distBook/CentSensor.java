import java.util.*;
//import static myconst.Color;
// Assumes completely connected topology
public class CentSensor extends Process implements Runnable, Sensor {
    final static int red = 0, green = 1;
    LinkedList q[]; // q[i] stores vector timestamps from process i
    int cut[][], color[],gstate[];
    boolean finished[]; // process i finished
    final int checker = Symbols.coordinator;
    VectorClock receiveVC;
    public CentSensor(VCTagger initComm) {
        super(initComm);
        cut = new int[n][n]; q = new LinkedList[n];
        color = new int[n]; gstate = new int[n];finished = new boolean[n];
        for (int i = 0; i < n; i++) {
            q[i] = new LinkedList(); color[i] = red; finished[i] = false;
        }
        if (myId == checker) new Thread(this).start();
    }
    public synchronized void localPredicateTrue(VectorClock vc){
    	LinkedList msgbuf = new LinkedList();
    	msgbuf.add(vc);
        if (myId == checker)
          handleMsg(new Msg(0,0,"trueVC", msgbuf), 0, "trueVC");
        else
          ((VCTagger)comm).simpleSendMsg(checker,"trueVC",msgbuf);
    }
    public synchronized void run() {
        int i = Util.searchArray(color, red);
        while (i != -1) {
            while (q[i].isEmpty() && !finished[i]) myWait();
            if (finished[i]) {
                ((SensorUser) app).globalPredicateFalse(i);
                return;
            }
            cut[i] = (int[]) q[i].removeFirst();
            paintState(i);
            i = Util.searchArray(color, red);
        }
        for (int j = 0; j < n; j++) gstate[j] = cut[j][j];
        ((SensorUser) app).globalPredicateTrue(gstate);
    }
    public synchronized void handleMsg(Msg m, int src, String tag){
        if (tag.equals("trueVC")) {
            receiveVC = (VectorClock) m.getMsgBuf().removeFirst();
            q[src].add(receiveVC.v); 
        } else if (tag.equals("finished")) 
            finished[src] = true; 
    }
    void paintState(int i) {
        color[i] = green;
        for (int j = 0; j < n; j++)
            if (color[j] == green)
                if (Util.lessThan(cut[i], cut[j])) color[i] = red;
                else if (Util.lessThan(cut[j], cut[i])) color[j] = red;
    }
}