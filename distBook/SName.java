import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.net.*;
public class SName extends UnicastRemoteObject
        implements NameService2 {
    NameTable table;
    public SName() throws RemoteException {
	table = new NameTable();
    }
    public InetSocketAddress search(String s) throws RemoteException {
        return table.search(s);
    }
    public InetSocketAddress blockingFind(String s) throws RemoteException {
        return table.blockingFind(s);
    }
    public int insert(String s, String hostName, int portNumber)
            throws RemoteException {
            return table.insert(s, hostName, portNumber);
    }
    public void handleList(LinkedList<Integer> L) throws RemoteException {
	L.remove();
    	System.out.println(L);
    }
    public static void main(String args[]) {
        // create security manager
	if (System.getSecurityManager() == null) {
        	System.setSecurityManager(new SecurityManager());
    	}

        try {
            SName obj = new SName();
            Naming.rebind("MyNameServer2", obj);
            System.out.println("MyNameServer2 bound in registry");
        } catch (Exception e) {
            System.out.println("NameServiceImpl err: " + e.getMessage());
        }
    }
}

