public class SimCamera  extends Process implements Camera {
    static final int orange = 0, blue = 1;
    int myColor = orange;
    boolean dirty[];
    boolean closed[];
    CamUser app;
    public SimCamera(MsgHandler initComm, CamUser app) {
        super(initComm);
        closed = new boolean[n];
        for (int i = 0; i < n; i++)
                dirty[i] = false;
        this.app = app;
    }
    public synchronized void globalState() {
        app.localState(); // record local State;
        sendMsg(neighbors, "marker", 0);  // send Markers
        for (int i = 0; i < n; i++)
                dirty[i] = false;
	myColor = blue;
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("marker")) {
            if ((myColor == orange) || (dirty[src]))
			 globalState();
        } else { // application message
            dirty[src] = true;
            app.handleMsg(m, src, tag); // give it to app
        }
    }
}
