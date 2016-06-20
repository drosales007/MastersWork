public class SynchBfsTree extends Process {
    public int parent = -1;
    public int level;
    Synchronizer s;
    public boolean isRoot;
    
    /**Added By Vinit
     *root is always 0
     *algo detrmined  Simple Synch or Alpha Sync
     */
    public SynchBfsTree(Linker initComm, 
                        Integer algo) {
        super(initComm);
       
        if(myId == 0){
            this.isRoot = true;
        }
        else  this.isRoot = true;
        
        if(algo.intValue() == 0){
            s = new SimpleSynch(initComm);
        }else
            s = new AlphaSynch(initComm);
    
              s.initialize(this);
    } 
    
    public SynchBfsTree(Linker initComm, 
                        Synchronizer initS, boolean isRoot) {
        super(initComm);
        s = initS;
        this.isRoot = isRoot;
        
    }
    public void initiate() {
        if (isRoot) {
            parent = myId;
            level = 0;
        }
      
        for (int pulse = 0; pulse < N; pulse++) {
            if ((pulse == 0) && isRoot) {
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i))
                        s.sendMessage(i, "invite", level + 1);
            }
            s.nextPulse();
        }
    }
    //Added by Vinit Color Node accoring to level 
    public Integer getColor(){
        return new Integer(level);
    }
    public void handleMsg(Msg m, int src, String tag) {
       //     s.handleMsg(m,src,tag);
        if (tag.equals("invite")) {
            if (parent == -1) {
                parent = src;
                level = m.getMessageInt();
                Util.println(myId + " is at level " + level);
                for (int i = 0; i < N; i++)
                    if (isNeighbor(i) && (i != src))
                        s.sendMessage(i, "invite", level + 1);
            }
        }
    
    }
}
