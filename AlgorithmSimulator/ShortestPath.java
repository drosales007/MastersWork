public class ShortestPath extends Process {  
    public int parent = -1;
    public int cost = -1;
    int edgeWeight[] = null;
    public ShortestPath(Linker initComm, int initCost[]) {
        super(initComm);
        System.out.println(" Constructing new ShortestPath");
        edgeWeight = initCost;
    }
   public ShortestPath(Linker initComm) {
        super(initComm);
        initEdgeWeights();
        //edgeWeight = initCost;
    }
   void initEdgeWeights(){
       edgeWeight = new int [N]; 
       for(int i = 0; i <N ;i +=1){
            if(isNeighbor(i)){
                edgeWeight[i] = 1;
            }
            else edgeWeight[i] = 100;
        }
   }
    public synchronized void initiate() {
        if (myId == Symbols.coordinator) {
            parent = myId;
            cost = 0;
            sendToNeighbors("path", cost);
        }
    }
    public synchronized void handleMsg(Msg m, int src, String tag){
        if (tag.equals("path")) {
            int dist = m.getMessageInt();
            if ((parent == -1) || (dist + edgeWeight[src] < cost)) {
                parent = src;
                cost = dist + edgeWeight[src];
                System.out.println("New cost is " + cost);
                sendToNeighbors("path", cost);
            }
        }
    }
}
