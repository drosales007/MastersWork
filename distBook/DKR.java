public class DKR extends Process implements Election {
    public enum State {ASLEEP,PASSIVE, ACTIVE};
    int myNum, leaderId = -1;
    State state = State.PASSIVE;
    int maxNum, neiborR;
    int next;
    public DKR(MsgHandler initComm,  int number) {
        super(initComm);
        this.myNum = number;
        maxNum = myNum;
        next = (myId + 1) % n;
    }
    public synchronized int getLeader(){
            while(leaderId == -1) myWait(); 
	    return leaderId;
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (state == State.ASLEEP) startElection();
        int rNum = m.getMessageInt(); 
        if (tag.equals("type1")) {
            if(state == State.ACTIVE){
                if(rNum != maxNum){
                    sendMsg(next, "type2", rNum);
                    neiborR = rNum;
                }
                else{   
		   leaderId = rNum; 
                   sendMsg(next, "leader", rNum);
                }
            }
            else sendMsg(next, "type1",rNum);
        }
        else if (tag.equals("type2")) {
            if(state == State.ACTIVE){
                if((neiborR > rNum) && (neiborR > maxNum)){
                    maxNum = neiborR;
                    sendMsg(next, "type1", neiborR);
                }
                else state = State.PASSIVE;
            }
            else sendMsg(next, "type2" ,rNum);
        }
        else if ((tag.equals("leader")) && (leaderId == -1)) {
                   leaderId = rNum;
                   sendMsg(next, "leader", rNum);
         }
    }
    public synchronized void startElection(){
        if (state == State.ASLEEP) { 
           state = State.ACTIVE;
           sendMsg(next, "type1", myNum);   
        }
    }
}
