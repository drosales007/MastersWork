/*
 * SlicingCut.java
 *
 * Created on July 20, 2004, 12:16 PM
 */

import java.util.*;
public class Slice {
    
    
    List cuts;
    int N;
    /** Creates a new instance of SlicingCut */
    public Slice(int _N) {
        N =_N;
        cuts = new Vector();
    }
    
    public void addCut(SlicingEvent e, int[] _cut ){
        for(int i =0; i<cuts.size(); i+=1){
            if (((SlicingCut)cuts.get(i)).equals(_cut))
                ((SlicingCut)cuts.get(i)).add(e);
            return;
        }
        cuts.add(new SlicingCut(e,_cut));
    }
    
      public String toString(){
        String s = new String();
        for(int i =0; i< cuts.size() ;i+=1){
            SlicingCut cut = (SlicingCut) cuts.get(i) ;
            s = s + cut;
        }
        return s;
    }
    
    class SlicingCut{
        int [] cut;
        public List events;
        /** Creates a new instance of SlicingCut */
        public SlicingCut(SlicingEvent e, int[] _cut ) {
            events = new Vector();
            cut = new int [N];
            for(int i =0; i<N ;i+=1) cut[i] = _cut[i];
            add(e);
        }
        public void add(SlicingEvent e){
            events.add(e);
        }
        public boolean equals(int [] _cut){
            for (int i = 0 ; i < N ;i +=1){
                if(cut[i]!= _cut[i]) return false;
            }
            return true;
        }
        
        public String toString(){
            String s = new String();
            s =s +" [ ";
            for(int j = 0; j<N;j+=1 ) s = s+ " " +cut[j];
            s = s+ " ] " + "\n";
            s += "Events \n";
            for (int i=0;i<events.size(); i+=1){
                s += (SlicingEvent)events.get(i) + "\n";
            }
            s+= "\n";
            return s;
        }
        
    }
    
  
}