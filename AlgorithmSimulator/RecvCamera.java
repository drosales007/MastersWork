import java.util.*;
public class RecvCamera  extends Process implements Camera {
    static final int white = 0, red = 1;
    public int myColor = white;
    boolean closed[];
    CamUser app;
    LinkedList chan[] = null;
    public RecvCamera(Linker initComm, CamUser app) {
        super(initComm);
        closed = new boolean[N];
        chan = new LinkedList[N];
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) {
                closed[i] = false;
                chan[i] = new LinkedList();
            } else closed[i] = true;
        this.app = app;
    }
    //Added By Vinit for Sim
    
        public RecvCamera(Linker initComm, Object _app) {
            
        super(initComm);
        closed = new boolean[N];
        chan = new LinkedList[N];
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) {
                closed[i] = false;
                chan[i] = new LinkedList();
            } else closed[i] = true;
        this.app = (CamUser)_app;
    }
    public synchronized void globalState() {
        myColor = red;
        app.localState(); // record local State;
        sendToNeighbors("marker", myId);  // send Markers
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        //By Vinit DEBUG
        System.out.println("Recv Camera got message " +tag+ " at node " + myId);
        //DEBUG ends
        if (tag.equals("marker")) {
            if (myColor == white) globalState();
            closed[src] = true;
            if (isDone()){
                System.out.println("Channel State: Transit Messages ");
                addToLog("\n Channel State: Transit Messages ");
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i))
                        while (!chan[i].isEmpty()){
                            String temp = ((Msg) chan[i].removeFirst()).toString();
                            System.out.println(temp);
                            addToLog(temp); 
                            
                        }
                           
                        
            }
        } else { // application message
            if ((myColor == red) && (!closed[src]))
                chan[src].add(m);
            app.handleMsg(m, src, tag); // give it to app 
        }
    }
    boolean isDone() {
        if (myColor == white) return false;
        for (int i = 0; i < N; i++)
            if (!closed[i]) return false;
        return true;
    }
    
    
    /*Added By Vinit for Simulator
     *
     */
  public Boolean  newOkayCS(){
     return Boolean.TRUE;
     }

    
}
