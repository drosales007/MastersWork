public class ShortestPath extends Process {  
    int parent = -1;
    int cost = -1;
    int edgeWeit[] = null;
    public ShortestPath(MsgHandler initComm, int initCost[]) {
        super(initComm);
        edgeWeit = initCost;
    }
    public synchronized void initiate() {
        if (myId == Symbols.coordinator) {
            parent = myId;
            cost = 0;
            sendMsg(neighbors, "path", cost);
        }
    }
    public synchronized void handleMsg(Msg m, int src, String tag){
        if (tag.equals("path")) {
            int dist = m.getMessageInt();
            if ((parent == -1) || (dist + edgeWeit[src] < cost)) {
                parent = src;
                cost = dist + edgeWeit[src];
                System.out.println("New cost is " + cost);
                for (int i: neighbors)
                	if (i != src)
                		sendMsg(i,"path", cost);
            }
        }
       turnPassive();
    }
}
