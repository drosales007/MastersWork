/*
 * EventQueue.java
 *
 * Created on December 11, 2003, 9:45 PM
 */

/**
 *
 * @author  Vinit
 */
import java.util.*;
//import java.io.*;
public class EventQueue extends Vector {
    public Vector myEventQueue= new Vector();
    public Vector myMessageQueue= new Vector();
    private int eventId = -1;
    public int lastMessageId = -1;
    public int numProc;
    /** Creates a new instance of EventQueue */
//    public EventQueue(int _numProc){
//        numProc = _numProc;
//        dc = new DirectClock[numProc]; 
//        for(int i = 0; i< numProc ;++i)
//            dc[i] = new DirectClock (numProc, i);
//    }
    public synchronized SingleEvent addSendEvent(char type, Message msg, VectorClock vc,int lc , int sourceId){
       // System.out.println( "In Event Queue: Adding send message : " + msg.getMessage()); 
        SingleEvent e =  new SingleEvent( type, getNextEventId(),msg, vc ,lc ,sourceId);
        myEventQueue.add(e);
        notifyAll (); 
        return e;
    }
        public synchronized SingleMessage addReceiveEvent(char type, Message msg, VectorClock vc,int lc, int sourceId, SingleEvent se){
        
        SingleEvent e =  new SingleEvent( type, getNextEventId(),msg, vc ,lc,sourceId);
        myEventQueue.add(e);
        SingleMessage m = new SingleMessage(se, e);
        myMessageQueue.add(m);
        //System.out.println(" Added message with SrcEvent Id = "+ m.sendEventId +" Dest EventId = " + m.recvEventId + "Last Event = " + getEventId());
        lastMessageId = Util.max (lastMessageId, e.eventId);
        notifyAll();
        return m;
    }
        
     public boolean pendingEvents(){
        if(lastMessageId < getEventId ()) return false;
        else return true; // Not all sends have been received 
        
    }
    public EventQueue () {
    }
    private int getEventId(){
        return eventId;
    }
    private int getNextEventId(){
        return ++eventId;
    }
    public synchronized SingleMessage guiGetMessage(int i){
        while(i>=myMessageQueue.size()){
        //    System.out.print("Gui is Waiting for a new message .... "); 
        Util.myWait(this);
        
        }
       // System.out.println("Gui has got the new message ");
        return (SingleMessage)myMessageQueue.get(i);        
        
        
        
    }
    public synchronized void guiWaitForEvent(){
         Util.myWait(this);
        
    }
    public synchronized SingleEvent guiGetEvent(int i){
        while(i>=myEventQueue.size()){
            //System.out.print("Gui is Waiting for a new event .... "); 
        Util.myWait(this);
        
        }
        //System.out.println("Gui has got the new event ");
        return (SingleEvent)myEventQueue.get(i);
    }


}
