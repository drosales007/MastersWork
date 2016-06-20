public class Tree extends Process {
    public int parent = -1;
    public int level;
    /** Added By Vinit 
     *root to determine which node is root
     *And new constructor for Simulator
     *Note: Config file sets the value of root
     **/
    public int root = 0 ;
    
    public Tree(Linker initComm, Integer _root) {
        super(initComm);
        root= _root.intValue();
    }
    public Tree(Linker initComm) {
        super(initComm);
        
    }
    public Tree(Linker initComm, boolean isRoot) {
        super(initComm);
        if (isRoot) initiate();
    }
    public void initiate() {
        // Added by Vinit : Let non root processes ignore initiate
        if(myId != root) return; 
        parent = myId;
        level = 0;
        for (int i = 0; i < N; i++)
            if (isNeighbor(i))
                sendMsg(i, "invite", level + 1);
    }
    
    public Integer getColor(){
        
        return new Integer(level);
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("invite")) {
            if (parent == -1) {
                parent = src;
                level = m.getMessageInt();
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i) && (i != src))
                        sendMsg(i, "invite", level + 1);
            }
        }
    }
}
