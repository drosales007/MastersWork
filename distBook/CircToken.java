public class CircToken extends Process implements Lock {
    boolean haveToken;
    boolean wantCS = false;
    public CircToken(MsgHandler initComm) {
        super(initComm);
        haveToken = (myId == Symbols.coordinator);
    }
    public synchronized void init(MsgHandler app) {
    	super.init(app);
        if (haveToken) sendToken();
    }
    public synchronized void requestCS() {
        wantCS = true;
        while (!haveToken) myWait();
    }
    public synchronized void releaseCS() {
        wantCS = false;
        sendToken();
    }
    void sendToken() {
        if (haveToken && !wantCS) {
            int next = (myId + 1) % n;
            Util.println("Process " + myId + "has sent the token");
            sendMsg(next, "token");
            haveToken = false;
        }
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("token")) {
            haveToken = true;
            if (!wantCS) {
                Util.mySleep(1000);
                sendToken();
            }
        }
    }
}
