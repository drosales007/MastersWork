import java.rmi.*;
public class ChainClock {
    public int[] v;
    public int size;
    int myId;
    int N;
    SharedData shared;
    public ChainClock(int numProc, int id, SharedData s, int maxSize) {
	myId = id; N = numProc;
        shared = s;
	size = 1;
	v = new int[maxSize];
        for (int i = 0; i < maxSize; i++) v[i] = 0;
    }
    public void tick() throws RemoteException{
	int index = shared.getComponent(myId, v, size);
	v[index]++;
	if (index >= size) {
	    size = index + 1;
	}
    }
    public void sendAction(boolean isRelevant) throws RemoteException {
	// include the vector in the message
	if (isRelevant) tick();
    }
    public void receiveAction(int[] sentValue, int sent_size, boolean isRelevant) throws RemoteException {
	for (int i = 0; i < sent_size; i++) 
	    v[i] = Util.max(v[i], sentValue[i]);
	
	if (sent_size > size) size = sent_size;
	
	if (isRelevant) tick();
    }
    public int getValue(int i) { return v[i]; }
    public String toString() { return Util.writeArray(v); }
}
