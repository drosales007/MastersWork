public class CamCircToken extends CircToken implements CamUser {
    public CamCircToken(Camera initComm) {
        super(initComm);
    }
    public synchronized void localState() {
        println("local state: haveToken=" + haveToken);
    }
}
