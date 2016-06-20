public class RAMutex extends Process implements Lock { 
    public int myts;
    public LamportClock c = new LamportClock();
    IntLinkedList pendingQ = new IntLinkedList();
    public int numOkay = 0;
    public RAMutex(Linker initComm) {
        super(initComm);
        myts = Symbols.Infinity;
    }
    public synchronized void requestCS() {
        c.tick();
        myts = c.getValue();
        broadcastMsg("request", myts);
        numOkay = 0;
        while (numOkay < N-1)
            myWait();
    }
    public synchronized void releaseCS() {
        myts = Symbols.Infinity;
        while (!pendingQ.isEmpty()) {
            int pid = pendingQ.removeHead();
            sendMsg(pid, "okay", c.getValue());
        }
    }
    public synchronized void handleMsg(Msg m) {
        int timeStamp = m.getMessageInt();
        c.receiveAction(m.srcId, timeStamp);
        if (m.tag.equals("request")) {
            if ((myts == Symbols.Infinity) // not interested in CS
            || (timeStamp < myts)
            || ((timeStamp == myts) && (m.srcId< myId)))
                sendMsg(m.srcId, "okay", c.getValue());
            else
                pendingQ.add(m.srcId);
        } else if (m.tag.equals("okay")) {
            numOkay++;
            if (numOkay == N - 1)
                notify(); // okayCS() may be true now
        }
    }

    
    
}
