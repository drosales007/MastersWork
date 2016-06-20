public class DrinkMutex extends Process implements Lock {
    private static final int tranquil = 0, thirsty = 1, drinking = 2;
    Boolean bottle[] = null,  requestBottle[] = null, needBottle[] = null; /* needBottle = required resources */
    DinMutex din;
    int myState = tranquil;
    public DrinkMutex(MsgHandler initComm) {
        super(initComm);
        din = new DinMutex(initComm); /* create diner instance for each drinker */
        bottle = new Boolean[n]; requestBottle = new Boolean[n];
        needBottle = new Boolean[n];
	for (int i : neighbors) 
            if (myId > i) {
                bottle[i] = false; requestBottle[i] = true;
            } else { 
                bottle[i] = true; requestBottle[i] = false;
            }

    }
    public synchronized void requestCS() {
        myState = thirsty;
        needBottle[myId] = true; 	/* this for testing only - pass in required resources array instead */
        if (haveBottles()) myState = drinking;
        else {
            din.requestCS(); 		/* force diner to hungry state */
            for (int i : neighbors)
                if (needBottle[i] && requestBottle[i] && !bottle[i]) 
			sendBool(i, "Request", requestBottle[i]);
        }
        while (myState != drinking) myWait();
    }
    public synchronized void releaseCS() {
        myState = tranquil;
        din.releaseCS(); 			/* force diner to thinking state */
        for (int i : neighbors) {
            needBottle[i] = false; 	/* clear required resources array */
            if (requestBottle[i]) sendBool(i, "Bottle", bottle[i]); 
        }
    }
    boolean haveBottles() {
        for (int i : neighbors)
            if (needBottle[i] && !bottle[i]) return false;
        return true;
    }
    void sendBool(int dest, String tag, Boolean b) {
	sendMsg(dest, tag); b = false;
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        if (tag.equals("Request")) {
            requestBottle[src] = true;
            if (!needBottle[src])  
		sendBool(src, "Bottle", bottle[src]); 
            else if ((myState != drinking) && !din.fork[src]) { 
		sendBool(src, "Bottle", bottle[src]);
		if (needBottle[src])
                    sendBool(src, "Request", requestBottle[src]);
            }
        }
        else if (tag.equals("Bottle")) {
            bottle[src] = true;
            if (haveBottles()) myState = drinking; 
        }
    }
}
