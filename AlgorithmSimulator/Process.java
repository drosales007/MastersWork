import java.io.*;
import java.lang.*;
public class Process implements MsgHandler {
    int N, myId;
    Linker comm;
    public Process(Linker initComm) {
        comm = initComm;
        myId = comm.getMyId();
        N = comm.getNumProc();
    }
    
    public synchronized void handleMsg(Msg m, int source, String tag) {
    }
    public void sendMsg(int destId, String tag, String msg) {
        Util.println("Sending msg to " + destId + ":" +tag + " " + msg);
        comm.sendMsg(destId, tag, msg);
    }
    public void sendMsg(int destId, String tag, int msg) {
        sendMsg(destId, tag, String.valueOf(msg)+" ");
    }
    public void sendMsg(int destId, String tag, int msg1, int msg2) {
        sendMsg(destId,tag,String.valueOf(msg1)+" "+String.valueOf(msg2)+" ");
    }
    public void sendMsg(int destId, String tag) {
        sendMsg(destId, tag, " 0 ");
    }
    public void broadcastMsg(String tag, int msg) {
        comm.broadcastMessage(tag, msg);
        /** Commented out by Vinit See Linker.java for details
         *
         *
         * for (int i = 0; i < N; i++)
         * if (i != myId) sendMsg(i, tag, msg);
         */
        
    }
    public void sendToNeighbors(String tag, int msg) {
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) sendMsg(i, tag, msg);
        
    }
   /**Vinit
    *Comment out the topology file based is Neigbor because java applet
    *reads the files
    *If not using applet or if u have local topology files uncomment the function
    
    
    public boolean isNeighbor(int i) {
           if (comm.neighbors.contains(i)) return true;
            else return false;
    }
    *
    **/
    /**Vinit 
     *Lets assume a ring topolgy ... loks pretty
     **/
      public boolean isNeighbor(int i) {
        if(i == myId +1 || i == myId -1) return true;
        if (myId == 0 && i == comm.numProc -1) return true;
        if (myId == comm.numProc -1  && i == 0 ) return true;
        return false;
    }


    public Msg receiveMsg(int fromId) {
        try {
            return comm.receiveMsg(fromId);
        } catch (IOException e){
            System.out.println(e);
            comm.close();
            return null;
        }
    }
    public synchronized void myWait() {
        try {
            wait();
        } catch (InterruptedException e) {System.err.println(e);
        }
    }
    /** Output Method for Programs
     *
     */
    
    public void tellUser(String s){
        javax.swing.JOptionPane.showMessageDialog(Simulator.appWindow,s);
        
    }
    public void addToLog(String s){
        MyUtil.getCommModule().appendProcessLog(s);
    }
    /**
     *add to user log 
    **/
    //TODO: Add the coments to a text box displayed at request ??
  
    
}
