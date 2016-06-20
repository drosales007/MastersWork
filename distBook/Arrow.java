import java.util.*;
public class Arrow extends Process implements Lock {
    int holder;             // Neighbor in direction of privileged node.
    boolean using = false;  // True if we are in critical section.
    LinkedList<Integer> requestQ = new LinkedList<Integer>(); // requesting neighbors 
    boolean asked = false;  // Eliminates redundant requests for the privilege.
    boolean initialized = false;

    public Arrow(MsgHandler initComm) {
        super(initComm);
        // if(myId==0) initialize(0);
    }
    private void initialize(int src) {
            holder = src;
            initialized = true;
            for (int i: neighbors)
            	if (i != src)
            		sendMsg(i, "INITIALIZE");
    }
    public synchronized void requestCS() {
    	while (!initialized) myWait();
        requestQ.add(myId);
        assignPrivilege();
        if(!using) makeRequest();
        while(!using) myWait();
    }
    private void assignPrivilege() {
        if ((holder==myId) && !using && !requestQ.isEmpty()) {
            holder = requestQ.remove(); // New holder gets the privilege.
            asked = false;
            if(holder == myId) 
                using = true;   // New holder is me, enter the CS.
            else 
                sendMsg(holder, "PRIVILEGE")  ;
        }
    }
    private void makeRequest() {
        if(holder!=myId && requestQ.size()!=0 && !asked) {
            sendMsg(holder, "REQUEST");
            asked = true;
        }
    }
    public synchronized void releaseCS() {
        using = false;
        assignPrivilege(); makeRequest();        
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
	    if (tag.equals("INITIALIZE")) initialize(src);
	    else {
               while (!initialized) myWait();
               if (tag.equals("REQUEST")) {
                   requestQ.add(src);
                   assignPrivilege(); makeRequest();
               } else if (tag.equals("PRIVILEGE")) {
                   holder = myId;
                   assignPrivilege(); makeRequest(); 
               }
             }
    }
}
