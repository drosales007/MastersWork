public class CamCircToken extends CircToken implements CamUser {
    /* Added by Vinit for Simulator*/
    public CamCircToken(Linker initComm) {
        super(initComm ,   0);
    }
    
    public CamCircToken(Linker initComm, int coordinator) {
        super(initComm, coordinator);
    }
    public synchronized void localState() {
        Util.println("local state: haveToken=" + haveToken);
        addToLog(myId+ ":haveToken= " + haveToken+ " \n");
    }
}
