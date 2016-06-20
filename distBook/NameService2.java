import java.rmi.*;
import java.util.LinkedList;
import java.net.*;
public interface NameService2 extends Remote {
    public InetSocketAddress search(String s) throws RemoteException;
    public InetSocketAddress blockingFind(String s) throws RemoteException;
    public int insert(String s, String hostName, int portNumber)
            throws RemoteException;
    public void handleList(LinkedList<Integer> L) throws RemoteException;
}

