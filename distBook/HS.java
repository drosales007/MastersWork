public class HS extends Process implements Election {
    public enum State {PASSIVE, CANDIDATE, LOST, ELECTED};
    int myNum, leaderId = -1, lgmax;
    int nbresp = 0;
    boolean respOK = true;
    State state = State.PASSIVE;
    int left, rit;
    public HS(MsgHandler initComm,  int number) {
        super(initComm);
        this.myNum = number;
        rit = (myId + 1) % n; left = (myId - 1 + n) % n;
    }
    public synchronized int getLeader(){
            while(leaderId == -1) myWait(); 
	    return leaderId;
    }
    private int next(int src){
       if (src == rit) return left;
       else return rit;
    }
     public synchronized void handleMsg(Msg m, int src, String tag) {
        int rNum = m.getMessageInt(); 
        if (tag.equals("election")) {
            int lg = m.getMessageInt();
            int lgmax = m.getMessageInt();
            if(rNum < myNum){ 
                sendMsg(src, "reject",  rNum);  
                if(state==State.PASSIVE) startElection();
            }
            else if(rNum > myNum){     
                state = State.LOST;
                if(lg+1 < lgmax) sendMsg(next(src), "election", rNum, lg+1, lgmax); 
                else sendMsg(src, "accept", rNum); 
            }
            else if ((rNum == myNum) && (state != State.ELECTED)) {
                    state = State.ELECTED;
                    leaderId = myNum;
                    sendMsg(next(src), "leader", myNum);
                }
         }
         else if( (tag.equals("leader")) && (leaderId != rNum)) {
                 sendMsg(next(src), "leader", rNum);
                 leaderId = rNum;
         }
         else if(tag.equals("accept")){
             if(rNum == myNum) nbresp = nbresp + 1;
             else sendMsg(next(src), "accept", rNum);
         }
         else if(tag.equals("reject")){
             if(rNum == myNum) { nbresp = nbresp + 1; respOK = false;}
             else sendMsg(next(src), "reject", rNum);
	}
    }
    public synchronized void startElection(){
        lgmax = 1;
        state = State.CANDIDATE;
        while(state == State.CANDIDATE){
           nbresp = 0; respOK = true;
           sendMsg(rit, "election", myNum, 0, lgmax);   
           sendMsg(left, "election", myNum, 0, lgmax);   
           while(nbresp != 2) myWait();
           if(!respOK) state = State.LOST;
           lgmax = lgmax * 2;
        }
    }
}
