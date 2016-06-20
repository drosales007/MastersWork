import java.util.Timer;
public class StableBottom extends Process implements Lock {
    public int myState = 0;
    public int leftState = 0;
    public int next;
    Timer t = new Timer();
    public boolean tokenSent = false;
    public boolean bottom =false;
    public StableBottom(Linker initComm) {
        super(initComm);
	next = (myId + 1) % N;
       if(myId == N/2) bottom = true;
    }
    public synchronized void initiate() {
        
        if(bottom)
        t.schedule(new RestartTask(this), 1000, 10000);
        else tellUser("Please initiate the bottom node");
    }
    public synchronized void requestCS() {
        if(bottom)
            while (leftState != myState) myWait();
        else
              while (leftState == myState) myWait();
    }
    public synchronized void releaseCS() {
        if(bottom){
              myState = (leftState + 1) % N;
         }
        else{
             myState = leftState;
            sendToken();
        }
    }
    public synchronized void sendToken() {
        if (!tokenSent) {
          sendMsg(next, "token", myState);
	  tokenSent = true;
	} else tokenSent = false;
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if(bottom){
        if (tag.equals("token") )
        {
            leftState = m.getMessageInt();
            notify();
	    Util.mySleep(1000);
            sendMsg(next, "token", myState);
	    tokenSent = true;
        } else if (tag.equals("restart") )
            sendToken();
        }
        else{
                if (tag.equals("token")) {
            leftState = m.getMessageInt();
            notify();
	    Util.mySleep(1000);
            sendToken();
        }
    }
        
    
    }
    
    
    public Boolean getColor(){
        
        if(bottom && myState == leftState) return Boolean.TRUE;
        if(!bottom && myState != leftState)return Boolean.TRUE;
        return Boolean.FALSE;
    }
}
