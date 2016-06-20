import java.util.Timer;
public class CircToken extends Process implements Lock {
    public boolean haveToken;
    public boolean wantCS = false;
    public CircToken(Linker initComm, int coordinator) {
        super(initComm);
        haveToken = (myId == coordinator);
    }
    public CircToken(Linker initComm) {
        super(initComm);
        
        haveToken = (myId == 0);
    }
    public void initiate() {
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
        if (haveToken && !wantCS){
            int next = (myId + 1) % N;
            Util.println("Process " + myId + "has sent the token");
            sendMsg(next, "token");
            haveToken = false;
        }
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        Util.println("CircToken.handleMsg:"+tag);
        if (tag.equals("token")) {
            haveToken = true;
            if (wantCS)
                notify();
            else {
                Util.mySleep(1000);
                sendToken();
            }
        }
    }
    
    
    //Added By Vinit for Sim
    public Boolean  newOkayCS(){
        return Boolean.valueOf(haveToken &&wantCS);
    }
    
    
}
