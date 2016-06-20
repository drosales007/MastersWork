import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
public class AntichainSharedData extends UnicastRemoteObject implements SharedData {
    ArrayList<TreeSet<Integer>> B;
    int N;
    int Z[];
    int size;
    public AntichainSharedData(int N) throws RemoteException {
	this.N = N;
	int max_comp = (N * (N + 1)) / 2;
	Z = new int[max_comp];
	B = new ArrayList<TreeSet<Integer>>();
	for (int i = 0; i < N; ++i)
	    B.add(new TreeSet<Integer>());
	size = 0;
    }
    public synchronized int getComponent(int id, int[] v, int vSize) throws RemoteException {
	int index = -1;
	for (int i = 0; i < B.size() && (index == -1); ++i) {
	    TreeSet<Integer> currSet = B.get(i);
	    for (int j: currSet) {
		if (v[j] == Z[j]) {
		    index = j;
		    if (i != 0){
			B.set(i, B.get(i - 1));
			B.set(i - 1, currSet);
			B.get(i).add(j);
			B.get(i-1).remove(j);
		    }
		    break;
 		}
	    }
	}
	if (index == -1) {
	    for (int i = 0; i < B.size() && (index == -1); ++i) {
		TreeSet<Integer> currSet = B.get(i);
		if (currSet.size() <= i) {
		    currSet.add(size);
		    index = size;
		    ++size;
		    break;
		}
	    }
	}
	Z[index]++;
	return index;
    }
}
