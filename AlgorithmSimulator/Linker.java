import java.util.*;
import javax.swing.*;
import java.io.*;

public class Linker {
    CommModule commModule;
    public int numProc;
    public int myId;
    public int N;
    IntLinkedList neighbors = new IntLinkedList();
    boolean DEBUG = true;
    public Linker(String basename, int myId, int _numProc) throws Exception {
        this.myId = myId;
        N = numProc= _numProc;
        commModule = MyUtil.getCommModule();
        neighbors.add((myId+1)%N);
        neighbors.add((myId+N-1)%N);
        /** Vinit : Dont want to Read topolgy from files in applet mode
         *
        Topology.readNeighbors(myId, N, neighbors);
         **/
    }
    public int getMyId(){
        return myId;
    }
    public int getNumProc(){
        return numProc;
    }
    
    public void sendMsg(int destId, String command, String msg){
        commModule.sendMessage(new Message(myId,destId, command,  msg));
    }
    
    
    public void sendMsg(int destId, String command) {
        sendMsg(destId, command, " 0");
    }
    
    
    public Msg receiveMsg(int fromId) throws IOException {
        
        return null;
    }
   
    /**Added By Vinit
      * Broadcast and multicast are special cases because 
     * ProcessTrace (should) show broadcasted msgs originating from the 
     * same point. This is not possible if we just use a number of sendMsg
     * WARNING !!! May cause problems if a loaded class overides sendMsg and 
     *uses these functions expecting that the overloaded sendMsg will be called
     *SOLUTION: Change the original program ... :-(
     **/
    
    public void broadcastMessage(String command,int msg){
        
        commModule.broadcast(new Message(myId, myId, command,  String.valueOf(msg)) );
    }
    public void multicastInRing( String command, String msg){
        if (myId !=0 && myId != numProc-1){
            sendMsg(myId+1, command,msg);
            sendMsg(myId-1, command,msg);
        }
        else if(myId == 0){
            sendMsg(myId+1, command,msg);
            sendMsg(numProc-1, command,msg);
        }else if (myId == numProc -1){
            sendMsg(myId-1, command,msg);
            sendMsg(0, command,msg);
        }
        
    }
    /** Doing nothing right now if close() is called
     **/
    
    public void receiveToken(SlicingToken t){
        //Overide method if this function is needed
    }
    public void close(){
         System.out.println("Linker.close() called .. doing nothing");
    }
    }// Linker
