/*
 * SlicingEvent.java
 *
 * Created on July 19, 2004, 2:31 PM
 */

/**
 *
 * @author  ogale
 */
public class SlicingEvent {
    public VectorClock vc;
    public int myId;
    public int type;
    public int procId;
    public boolean var = false;
    /** Creates a new instance of SlicingEvent */
    public SlicingEvent(VectorClock _vc, int _myId, int _type, int _procId,boolean _var) { // ProcId is the destId or srcId for send/recv events 
                                                                 //  .. myId for internal events ,types 1,2,3  send,recv,inernal
        vc = new VectorClock(_vc); 
        myId = _myId;
        type =type;
        procId = procId;
        var = _var;
        
    }
    
    public boolean isLessThan(SlicingEvent e){
        return vc.isLessThan(e.vc);
    }
    public boolean isGreaterThan(SlicingEvent e){
        return vc.isGreaterThan(e.vc);
    } 
    public boolean equals(SlicingEvent e){
        return vc.equals(e.vc);
    }
    
    public String  toString(){
        return vc.toString();
    }
}
