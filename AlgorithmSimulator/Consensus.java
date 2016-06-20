import java.util.*;
public class Consensus extends Process {
    public int myValue;
    public int f; // maximum number of faults
    public boolean changed = true;
    public boolean hasProposed = false;
    //    public Consensus(Linker initComm, int f) {
    //        super(initComm);
    //        this.f = f;
    //
    //    }
    public Consensus(Linker initComm, Integer _f) {
        super(initComm);
        this.f = _f.intValue();
        propose(myId);
        //decide();
        new DelayThread(100);
    }
     class DelayThread extends Thread{
            int delay;
            public DelayThread(int d){
                delay = d;
                start();
            }
             public void run (){
                try{
                    Thread.sleep (delay);
                }catch (Exception e){}
             decide();                 
            }
        }
    public synchronized void propose(int value) {
        myValue = value;
        hasProposed = true;
        notify();
    }
    //    public int decide() {
    //        for (int k = 0; k <= f; k++) { // f+1 rounds
    //            synchronized (this) {
    //                if (changed) broadcastMsg("proposal", myValue);
    //            }
    //            // sleep enough to receive messages for this round
    //            Util.mySleep(Symbols.roundTime);
    //        }
    //        synchronized (this) {
    //            return myValue;
    //        }
    //    }
    public void decide() {
        for (int k = 0; k <= f; k++) { // f+1 rounds
            synchronized (this) {
                if (changed) broadcastMsg("proposal", myValue);
            }
            // sleep enough to receive messages for this round
            Util.mySleep(Symbols.roundTime);
        }
        synchronized (this) {
            addToLog(myId +" decided"+ myValue+"\n");
        }
    }
    
    public Integer getColor(){
        return new Integer(myValue);
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        while (!hasProposed) myWait();
        if (tag.equals("proposal")) {
            int value = m.getMessageInt();
            if (value < myValue) {
                myValue = value;
                changed = true;
            } else
                changed = false;
        }
    }
}
