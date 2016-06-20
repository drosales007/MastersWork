/*
 * SlicingToken.java
 *
 */
import java.util.*;
public class SlicingToken {
    int N,myId;
    
    SlicingEvent init ;
    int[] cut; 
    boolean[] color; 
    SlicingComputation comp;
    Vector pendingEvents;
    public Slice s;
    /** Creates a new instance of SlicingToken */
    public SlicingToken(int _myId, int _N) {
        myId = _myId;
        N = _N;
        comp = new SlicingComputation(N);
        cut =  new int[N]; 
        s = new Slice(N);
        color = new boolean[N];
        pendingEvents = new Vector();
        for(int i = 0 ;i <N ;i+=1){
            cut[i] = 0; //init;
        }
        
    }
    
    public int addEvent(SlicingEvent e ){
         comp.add(e.myId,e);
        pendingEvents.add(e);
        while(!pendingEvents.isEmpty()){
            SlicingEvent temp = (SlicingEvent) pendingEvents.get(0) ;
            int i = updateSlice(e);
            if( i !=-1) return i;
            pendingEvents.remove(0);
        }
        return -1;
    }
    
    public int updateSlice(SlicingEvent e){
       
        
        for(int i=0; i<N; i+=1){
             int k =comp.size(i) -1;
            //Get the last event f on each process such that f--> e or f equals e
            while( k>=0  && !comp.get(i,k).isLessThan(e)) {
                k -=1;
            }
            cut[i] = k;
        }
        //WCP detection
        System.out.print("Init cut for event " + e+"is" + Util.writeArray(cut));
       
        for(int i = 0;i<N;i+=1){
             paintEvent(i);
        }
        
        int i;
        do{
            i = Util.searchArray(color,false,e.myId);
            if(i == -1){// No false element ... add cut to slice
                s.addCut(e,cut);
                return -1 ;
            }
            cut[i] +=1;
            paintEvent(i);
        }while(! comp.get(i,cut[i]).equals(comp.maxEvent));
        return i; //Send Token to i unless myPid equals i
        
    }
    
    void paintEvent(int pid){
        color[pid] = comp.get(pid,cut[pid]).var;
        for(int i=0; i<N; i+=1){
            if(color[i] == true){
                if(comp.get(pid,cut[pid]).isGreaterThan(comp.get(i,cut[i]+1))){
                    color[i]= false;
                }
            }
        }
    }
    
   public void copyTokenFrom(SlicingToken t1){
       comp = t1.comp;
       pendingEvents = t1.pendingEvents;
       s = t1.s ;
       
   }
}
