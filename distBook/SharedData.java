import java.rmi.*;
public interface SharedData extends Remote{
    public int getComponent(int id, int[] v, int vSize) throws RemoteException;
}
