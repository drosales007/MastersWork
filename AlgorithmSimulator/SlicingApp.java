/*
 * SlicingApp.java
 *
 * Created on July 20, 2004, 5:03 PM
 */

/**
 *
 * @author  ogale
 */
public class SlicingApp extends Process {
      
    public SlicingApp(SlicingLinker _initComm){
      super(_initComm);
      }
    public boolean getPredicate(){
        return true;
    }
    
}
