/*
 * SingleEvent.java
 *
 * Created on November 2, 2003, 1:52 AM
 */

/**
 *
 * @author  Vinit
 */
public class SingleEvent{
    public char eventType;
    public int eventId;
    public Message msg;
    public VectorClock v;
    public int lc;
    public int nodeId;
    
    public SingleEvent ( char ev, int ev_id, Message _msg, VectorClock _v, int _lc, int _nodeId){
        eventType =ev;
        eventId = ev_id;
        msg = _msg;
        v= _v;
        lc = _lc;
        nodeId = _nodeId;
        
    }
    
    public String toString (){
        String ret = new String ();
        if(eventType == 's'){
            ret = "Sent message ...  \n   Source:"+ nodeId + "Dest:"+ msg.destId + "\n   Message :" + msg.getMessage ()+ "\n";
        }
        if(eventType == 's'){
            ret = "Revd message ... \n   Source:"+ nodeId + "Dest:"+ msg.destId + "\n   Message :" + msg.getMessage ()+"\n";
        }
        if(eventType == 'b'){
            ret = "Broadcast message ... \n   Source:"+ nodeId +  "\n   Message :" + msg.getMessage ()+ "\n";
        }
        return ret;
    }
}
