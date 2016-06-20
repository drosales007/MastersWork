public class RingLeader extends Process implements Election {
    public int number;    
    public int leaderId = -1;
    public int next;   
    public boolean awake = false;
    public RingLeader(Linker initComm, int number) {
        super(initComm);
        this.number = number;
        next = (myId + 1) % N;
    }
     public RingLeader(Linker initComm) {
        super(initComm);
        this.number = N - myId;
        next = (myId + 1) % N;
    }
    public synchronized int getLeader(){
	while (leaderId == -1) myWait();
	return leaderId;
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        int j = m.getMessageInt(); // get the number
        if (tag.equals("election")) {
            if (j > number)
                sendMsg(next, "election", j); // forward the message
            else if (j == number) // I won!
                sendMsg(next, "leader", myId);
            else if ((j < number) && !awake) startElection();
        } else if (tag.equals("leader")) {
            leaderId = j;
	    notify();
            if (j != myId) sendMsg(next, "leader", j);
        }
    }
    public synchronized void startElection() {
        awake = true;
        sendMsg(next, "election", number);
    }
    
    public Integer getColor(){
        if(leaderId == -1) return new Integer(0);
        else return(new Integer(1));
    }
}
