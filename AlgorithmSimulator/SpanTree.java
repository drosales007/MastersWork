public class SpanTree extends Process {
    public int parent = -1; // no parent yet
    public IntLinkedList children = new IntLinkedList();
    public int numReports = 0;
    public boolean done = false;
    public int numNeighbors;
    //Vinit
    public int root =0;
    public SpanTree(Linker initComm, boolean isRoot) {
        super(initComm);
       
    }
     public SpanTree(Linker initComm,Integer _root) {
        super(initComm);
        root = _root.intValue();
        numNeighbors = comm.neighbors.size();
    }
    public void start (){
            sendToNeighbors( "invite", myId);
            tellUser("This algorithm works correctly if exactly one process starts the construction");
    }
    public synchronized void waitForDone() { // block till children known
	while (!done) myWait();
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("invite")) {
            if (parent == -1) {
            	numReports++;
                parent = src;
                sendMsg(src, "accept");
                for (int i = 0; i < N; i++)
                    if ((i != myId) && (i != src) && isNeighbor(i))
                        sendMsg(i, "invite");
            } else
                sendMsg(src, "reject");
        } else if ((tag.equals("accept")) || (tag.equals("reject"))) {
            if (tag.equals("accept")) children.add(src);
            numReports++;
            if (numReports == numNeighbors){ //comm.neighbors.size()) {
		done = true;
		notify();
	    }
        }
    }
    
    //Vinit
    public Boolean getColor(){
        if(done) return Boolean.TRUE;
        else return Boolean.FALSE;
    }
}
