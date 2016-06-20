public class LampMutexOLD extends Process implements Lock {
    public DirectClock v;
    public int[] q; // request queue
    public LampMutexOLD(Linker initComm) {
        super(initComm);
        System.out.println("in LampMutex Cons "+ N + " " +myId );
        v = new DirectClock(N, myId);
        q = new int[N];
        for (int i = 0; i < N; i++) 
            q[i] = Symbols.Infinity;
    }
    public synchronized void requestCS() {
        v.tick();
        q[myId] = v.getValue(myId);
        broadcastMsg("request", q[myId]);
        while (!okayCS())
            myWait();
    }
    public synchronized void releaseCS() {
        q[myId] = Symbols.Infinity;
        broadcastMsg("release", v.getValue(myId));
    }
    public boolean okayCS() {
        for (int i = 0; i < N; i++){
            if (isGreater(q[myId], myId, q[i], i))
                return false;
            if (isGreater(q[myId], myId, v.getValue(i), i))
                return false;
        }
        System.out.println(" Node : " + myId +" is in CS ...... yipeeee");
        return true;
    }
        public Boolean newOkayCS() {
        for (int i = 0; i < N; i++){
            if (isGreater(q[myId], myId, q[i], i))
                return Boolean.FALSE;
            if (isGreater(q[myId], myId, v.getValue(i), i))
                return Boolean.FALSE;
        }
        System.out.println(" Node : " + myId +" is in CS ...... yipeeee");
        return Boolean.TRUE;
    }
    boolean isGreater(int entry1, int pid1, int entry2, int pid2){
        if (entry1 == Symbols.Infinity) return true;
        if (entry2 == Symbols.Infinity) return false;
        return ((entry1 > entry2) 
                || ((entry1 == entry2) && (pid1 > pid2)));
    }
    public synchronized void handleMsg(Message m, int source, String tag) {
        int timeStamp = m.getMessageInt();
        v.receiveAction(source, timeStamp);
        if (tag.equals("request")) {
            try{
            Thread.sleep (100);
            }catch (Exception e){}
            q[source] = timeStamp;
            sendMsg(source, "ack", v.getValue(myId));
        } else if (tag.equals("release"))
            q[source] = Symbols.Infinity;
        notify(); // okayCS() may be true now
    }
}
