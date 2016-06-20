

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;


public class Channel {
    CommModule commModule;
    
    
    boolean DEBUG = true;
    static    long delay =5000 ;
 
    public static void setDelay (long i){
        delay =i;
    }
    
    public static long getDelay (){
        return delay;
    }
    public  Channel (int numProc,CommModule c) {
            commModule =c;
        //	msgQ = new Vector[numProc][numProc];
    } // structQ constructor
    
     public void send(SlicingToken t,int  destId){
        
        DelayThreadToken thr = new DelayThreadToken ( t, destId );
        thr.start ();
    }
    
    public void send(SingleEvent e,int  destId){
        
        DelayThread t = new DelayThread ( e, destId );
        t.start ();
    }
        

        class DelayThread extends Thread{
            SingleEvent e;
            int destId ;
            String str;
            public DelayThread (SingleEvent _e, int _destId){
                e =_e;
                destId =_destId;
            }
            
            public void run (){
                try{
                    Thread.sleep (delay);
                }catch (Exception e){}
                if(commModule.isEnabledNode(destId)) {
                commModule.recvMessage(e, destId);
                }
                
            }
        }
        
         class DelayThreadToken extends Thread{
            SlicingToken t;
            int destId ;
            String str;
            public DelayThreadToken (SlicingToken _t, int _destId){
                t =_t;
                destId =_destId;
            }
            
            public void run (){
                try{
                    Thread.sleep (delay);
                }catch (Exception e){}
                if(commModule.isEnabledNode(destId)) {
                commModule.recvMessage(t, destId); 
                }
                
            }
        }
        
    } // structQ
