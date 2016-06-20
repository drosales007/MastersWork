import java.rmi.*;
import java.rmi.server.*;
public class DynamicSharedData extends UnicastRemoteObject implements SharedData {
    int[] Z;
    int[] F;
    int size;
    public DynamicSharedData(int N) throws RemoteException {
	Z = new int[N];
	F = new int[N];
	size = 1;
    }
    public synchronized int getComponent(int id, int[] v, int vSize) throws RemoteException {
	int index = -1;
	for (int i = 0; i < size; i++) {
	    if (F[i] == id) {// found matching index in F
		index = i;
		break;
	    }
	}
	if (index == -1) {
	    for (int i = 0; i < vSize; i++) {
		if (Z[i] == v[i]) {// Found matching index in Z
		    index = i;
		    break;
		}
	    }
	}
	if (index == -1) {
	    index = size;
	    size++;// adding a new index
	}
	Z[index]++;
	F[index] = id;
	return index;
    }
}
