public class Tree extends Process {
    public int parent = -1;
    public int level;
    public Tree(MsgHandler initComm, boolean isRoot) {
        super(initComm);
        if (isRoot) initiate();
    }
    public void initiate() {
        parent = myId;
        level = 0;
        sendMsg(neighbors, "invite", level + 1);
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("invite")) 
            if (parent == -1) {
                parent = src;
                level = m.getMessageInt();
                for (int i: neighbors)
                	if (i != src)
                		sendMsg(i, "invite", level + 1);
            }
    }
}
