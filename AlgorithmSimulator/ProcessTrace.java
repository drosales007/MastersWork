/*
 * JFrame.java
 *
 * Created on June 17, 2003, 1:11 PM
 */

/**
 *
 * @author  Vinit
 */


import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class ProcessTrace extends JComponent {
    //Gui gui;
    CommModule comm;
    int x,y;
    int yGap;
    int gapX  = 60;
    int maxY;
    int maxX;
    int lMargin =50;
    int tMargin =50;
    int rMargin =50;
    int gapText =20;
    //[] = new int [maxProc];
    int gapY ;
    int radius = 10;
    int eventsSize ;
    int maxEvents ;
    JScrollPane parent;
    
    //Hints for fast refresh 
    int lastSelectedNode = 0;
    public int nextMessage =0; // Next Message to be proceesed by fast update thread
    public boolean changeSelectedNode =false;
    public boolean drawNewMessages = false;
    public ProcessTrace (CommModule _comm, JScrollPane _parent) {
        comm = _comm;
        //gui = comm.gui;
        parent =_parent;
        parent.setViewportView (this);
        gapX =40;
        lMargin =50;
        tMargin =50;
        rMargin =50;
        gapText =20;
        //[] = new int [maxProc];
        //gapY = (maxY)/gui.getNumProc();
        radius = 3;
        //int eventsSize = Events.size ();
        //int maxEvents = MyUtil.maxArray (numEvents, numEvents.length);
        x = 50;
        y =20;
        MListener ml = new MListener ();
        addMouseListener (ml);
        //setPreferredSize (new Dimension (2000, 400));
        setBackground(Color.white); 
        setOpaque (true);
        parent = _parent;
        //scrollPane = new ScrollPane ();
        //scrollPane.add(this);
    }
    
 
    
    
    public void refresh (){
        repaint ();
    }
    public void refresh(int i){
        changeSelectedNode = true;
    }
    
    class MListener extends MouseInputAdapter{
        
        public void mouseClicked (MouseEvent e){
            int xc= e.getX ();
            int yc =e.getY ();
            for(int i = 0; i<comm.num_nodes;i++){
                if(isInRange (tMargin + i * gapY , yc,3)){
                    lastSelectedNode = comm.getSelectedNode ();
                    comm.setSelectedNode (i);
                    //gui.left.updateDisplay ();
                    comm.refreshGui ();
                    //refresh(i);
                    return;
                }
            }
        }
        
        boolean isInRange (int y1,int y2,int r){
            
            int ydiff= Math.abs (y1-y2);
            if( ydiff < r) return true;
            return false;
        }
        
    }
    
    public void paintComponent (Graphics g1){
        
        Graphics2D g = (Graphics2D) (g1);
       Rectangle display = parent.getViewport ().getViewRect ();
        //if(Events.isEmpty())return;
        // int maxProc = s.gui.getNumProc ();
        //paint background
          Color oldC = g.getColor();
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(oldC);
        
        maxY= getHeight ()-tMargin;
        maxX= getWidth ()-lMargin - rMargin;
        gapY = (maxY)/comm.num_nodes;
        //int eventsSize = Events.size ();
        int totalEvents = comm.getLastMessageId ();
        //lastX =0;
        
        //g.drawRect (20,40,maxX,maxY);
        
         Stroke sOld ;
            BasicStroke stroke;
            sOld =g.getStroke ();
            float[] f = new float [5];
            f[0] =1;
            f[1] =f[2] =f[3]=f[4]= 0;
  
            for(int n = 0 ; n < comm.num_nodes; ++n){
            if(comm.getNodeColor(n)> 0){
                oldC = g.getColor ();
                Color c = MyUtil.translateColor (comm.getNodeColor (n));
                g.setColor (c);
            }
            if(n== comm.getSelectedNode ()){
                stroke = new BasicStroke ((float)3.5,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
                
            }
            else{
                stroke = new BasicStroke (1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
            }
            g.setStroke (stroke);
            g.drawLine (lMargin,tMargin + gapY *n ,maxX, tMargin + gapY *n);
            g.setStroke (sOld);
            if(comm.getNodeColor(n)> 0){
                
                g.setColor (oldC);
            }
            g.drawString (String.valueOf (n), lMargin -20,tMargin + gapY*n);
            }
        java.util.List msgQueue = comm.guiGetMessageQueue ();
       
            
        nextMessage = msgQueue.size();
        for(int i =0; i <nextMessage; ++i){
        
            SingleMessage sm = (SingleMessage)msgQueue.get(i);
            
                 
                 int x1= lMargin + sm.send_lc * gapX;//+ (int)(Math.random ()*10) ;
                 int x2= lMargin + sm.recv_lc*gapX;//+(int)(Math.random ()*(double)gapX/3) ;// x[de.srcId][srcEventId];
                
                 
                 int y1 = tMargin + sm.sourceId * gapY; 
                 int y2 = tMargin + sm.destId* gapY;
                 if(x2 > getSize().width) {
                     setPreferredSize (new Dimension(getSize ().width + 2000, getSize ().height));
                     revalidate();
                 }
                   if(display.x+display.width < x1 ) break; 
                  if(!display.contains (x1,y1) &&  !display.contains(x2,y2)) //{System.out.println(" ....Optimiztion seems to work  ");
                      continue;
                  //}
                 
                 sOld =g.getStroke ();
                /*float[] f = new float [4];
                f[0]  =0;
                f[1]=f[2] =f[3] =1;
                 */
                 BasicStroke s = new BasicStroke ((float)1.3);// (1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 3);
                 g.setStroke (s);
                 //System.out.println(" SendEventId = " + sm.sendEventId+ " :  Send LamportClock = " + sm.send_lc);
                 //System.out.println(" RecvEventId = " + sm.recvEventId+ " :  Recv LamportClock = " + sm.recv_lc);
//                 int x1= lMargin + sm.sendEventId*gapX;//+ (int)(Math.random ()*10) ;
//                 int x2= lMargin + sm.recvEventId*gapX+(int)(Math.random ()*35) ;// x[de.srcId][srcEventId];
//                 int y1 = tMargin + sm.sourceId * gapY;
//                 int y2 = tMargin + sm.destId* gapY;
                 
                 //int tempgapY= ((y2-y1)/Math.abs (y2-y1) * (radius + 13)) ;
                 
                 oldC = g.getColor ();
                 Color c = MyUtil.translateColorMessages ( sm.msg.getTag());
                 g.setColor (c);
                 g.drawLine (x1,y1,x2 ,y2);
                 g.fillPolygon (MyUtil.getArrow (x1,y1,x2,y2, 10,5, 0.5));
                 g.fillOval (x1-radius,y1-radius,radius*2,radius*2);
                 g.drawOval (x2-radius,y2-radius,radius*2,radius*2);
                 g.setStroke (sOld);
                 //g.drawString (de.srcState, x1-gapText ,y1 -tempgapY);
                 //g.drawString (de.destState,x2-gapText ,y2 +tempgapY);
                 
                 g.drawString ( sm.msg.getTag ().substring (0,3),(x1 + x2) /2 ,(y1 + y2) /2);
                 //g.fillOval (x[de.srcId][de.srcEventId] - radius  ,y[de.srcId][de.srcEventId]-radius , 2*(radius ),2*(radius ));
                 g.setStroke (sOld);
                 g.setColor (oldC);
        
        
        }
    }
        
}
        
             /*
                for( int j = 0 ; j < numEvents[i]; j++){
                    //int x1 = lMargin +  j*gapX[i];
                    int y1 =tMargin +  i*gapY;
                    //g.drawOval(x1-radius,y1-radius,radius*2,radius*2);
                    x[i][j] =lMargin ;//x1;
                    y[i][j]= y1;
              
                }
              
              */
           
            
            
       // }
           /*
            for(int j= 0 ; j< eventsSize;j++){
            
                DisplayEvent de = (DisplayEvent) Events.get(j);
                int i= de.destEventId;
            
            
            
                if(x[de.srcId][de.srcEventId] > (x[de.destId][i] - gapX)  ){
            
                    x[de.destId][i] =x[de.srcId][de.srcEventId] + gapX;
                }
            
                x[de.srcId][de.srcEventId +1 ]= Util.max(x[de.srcId][de.srcEventId] + gapX,x[de.srcId][de.srcEventId +1]);
                //if( (de.destEventId < 199) && (x[de.destId][de.destEventId +1 ] - x[de.destId][de.destEventId] <gapX[de.destId] ) ){
                x[de.destId][i+1] = Util.max(x[de.destId][i] +gapX,x[de.destId][i+1]);
                if( x[de.destId][i+1] > lastX) lastX =x[de.destId][i+1] ;
            
                //}
            
            }
            */
//        for(int i =0;i<gui.getNumProc ();i++){
//            
//            for( int j = 0 ; j < numEvents[i]; j++){
//                int x1= x[i][j];
//                int y1 =y[i][j];
//                g.drawOval (x1-radius,y1-radius,radius*2,radius*2);
//                //g.drawString(String.valueOf(j),x1,y1);
//                
//            }
//        }
//        
//        for(int i= 0 ; i< Events.size ();i++){
//            
//            DisplayEvent de = (DisplayEvent) Events.get (i);
//            int destEventId = de.destEventId - currentEventNum[de.destId];
//            int srcEventId = de.srcEventId - currentEventNum[de.srcId];
//            
//            Stroke sOld ;
//            sOld =g.getStroke ();
//                /*float[] f = new float [4];
//                f[0]  =0;
//                f[1]=f[2] =f[3] =1;
//                 */BasicStroke s = new BasicStroke ((float)1.3);// (1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 3);
//                 g.setStroke (s);
//                 int x1= x[de.srcId][srcEventId];
//                 int y1 =y[de.srcId][srcEventId];
//                 int x2 = x[de.destId][destEventId];
//                 int y2 = y[de.destId][destEventId];
//                 int tempgapY= ((y2-y1)/Math.abs (y2-y1) * (radius + 13)) ;
//                 
//                 Color oldC = g.getColor ();
//                 Color c = MyUtil.translateColorMessages (de.command);
//                 g.setColor (c);
//                 g.drawLine (x1,y1,x2 ,y2);
//                 g.fillPolygon (MyUtil.getArrow (x1,y1,x2,y2, 10,5, 0.5));
//                 g.setStroke (sOld);
//                 g.drawString (de.srcState, x1-gapText ,y1 -tempgapY);
//                 g.drawString (de.destState,x2-gapText ,y2 +tempgapY);
//                 
//                 g.drawString ( de.command.substring (0,3),(x[de.srcId][srcEventId] + x[de.destId][destEventId]) /2 ,(y[de.srcId][srcEventId] + y[de.destId][destEventId]) /2);
//                 //g.fillOval (x[de.srcId][de.srcEventId] - radius  ,y[de.srcId][de.srcEventId]-radius , 2*(radius ),2*(radius ));
//                 g.setStroke (sOld);
//                 g.setColor (oldC);
//        }
//        System.out.println ("Pending Events = " + pendingSends.size ());
//        for(int i=0;i<pendingSends.size ();i++){
//            
//            PendingEvent pe = (PendingEvent)pendingSends.get (i);
//            //int destEventId = pe.destEventId - currentEventNum[pe.destId];
//            int srcEventId = pe.srcEventId - currentEventNum[pe.srcId];
//            
//            Stroke sOld ;
//            sOld =g.getStroke ();
//            float[] f = new float [5];
//            f[0] =1;
//            f[1] =f[2] =f[3]=f[4]= 0;
//            Stroke stroke = new BasicStroke ((float)0.4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
//            g.setStroke (stroke);
//            int x1= x[pe.srcId][srcEventId];
//            int y1 =y[pe.srcId][0];
//            int x2 = Util.max (x[pe.destId][numEvents[pe.destId]],x1)+ gapX;
//            int y2 = y[pe.destId][0];
//            //int tempgapY= ((y2-y1)/Math.abs(y2-y1) * (radius + 13)) ;
//            
//            Color oldC = g.getColor ();
//            Color c = MyUtil.translateColorMessages (pe.command);
//            g.setColor (c);
//            g.drawLine (x1,y1,x2 ,y2);
//            g.fillPolygon (MyUtil.getArrow (x1,y1,x2,y2, 10,5, 0.5));
//            
//            //g.drawString(pe.srcState, x1-gapText ,y1 -tempgapY);
//            //g.drawString(pe.destState,x2-gapText ,y2 +tempgapY);
//            
//            g.drawString ( pe.command.substring (0,3),(x1+x2) /2 ,(y1+y2) /2);
//            //g.fillOval (x[de.srcId][de.srcEventId] - radius  ,y[de.srcId][de.srcEventId]-radius , 2*(radius ),2*(radius ));
//            g.setStroke (sOld);
//            g.setColor (oldC);
//            
//        }
//        
//        
//    }
    
    
//}


//    public int[] numEvents = new int[20];
//    int x[][] ;
//    int y[][] ;
//
//    // = new int [maxProc][200];
//    int currentEventNum[];
//    int maxProc ;
//    public java.util.Vector Events = new java.util.Vector () ;
//    public Vector pendingSends = new Vector ();
//    //int radius =6;
//    //Simulator s;

//    MyCanvas myCanvas;
//    int lastX=0;

//    public ProcessTrace (CommModule _comm) {
//        comm = _comm;
//        gui = comm.gui;
//        maxProc = gui.getNumProc ();
//        currentEventNum = new int[maxProc];
//        for(int i = 0;i <maxProc;i++){
//            currentEventNum[i] =0 ;
//        }
//        x = new int [gui.getNumProc ()][1000];
//        y = new int [gui.getNumProc ()][1000];
//        scrollPane = new ScrollPane ();
//
//        myCanvas = new MyCanvas ();
//        scrollPane.add (myCanvas);
//
//        this.add (scrollPane);
//        myCanvas.setSize (2000,(int)scrollPane.getSize ().getHeight () - 20);
//        myCanvas.setBackground (Color.white);
//        MListener ml = new MListener ();
//        myCanvas.addMouseListener (ml);
//
//        for(int i = 0; i < 20;numEvents [i]=0,  i++);
//
//
//        //this.s =s;
//        prepareDisplay ();
//        update();
//        //setSize (1000,750);
//
//    }
//
//    public void update (){
//        myCanvas.repaint ();
//        scrollPane.setScrollPosition ((lastX - scrollPane.getSize ().width/2) , 0) ;
//    }
//    public synchronized void addEvent (int type, int srcId, int destId, String command,String msg, String state ){
//        DisplayEvent de;
//        PendingEvent pe;
//
//        if(type == SimEvent.SEND){
//
//            pe = new PendingEvent (srcId,destId, command , msg,state);
//            pendingSends.add (pe);
//
//            numEvents[srcId]++;
//
//        }
//
//        if(type == SimEvent.BROADCAST){
//            for(int i =0 ;i<gui.getNumProc ();i++){
//                if(i != srcId){
//                    pe = new PendingEvent (srcId,i, command , msg,state);
//                    pendingSends.add (pe);
//
//                }
//            }
//            numEvents[srcId] ++ ;
//        }
//
//        if(type == SimEvent.RECEIVE){
//            Vector tempVector=new Vector ();
//            if( lastX >myCanvas.getSize ().width -40){
//                System.out.println ("Deleting old Events");
//                for(int i =0;i<Events.size ();i++){
//                    DisplayEvent tempde = (DisplayEvent)Events.get (i);
//                    if(x[tempde.srcId][tempde.srcEventId] < myCanvas.getSize ().width/3) {
//                        tempVector.add (tempde);
//                        //if(tempde.srcEventId > currentEventNum[tempde.srcId] -1)
//                        //  currentEventNum[tempde.srcId]= tempde.srcEventId +1;
//                        //if(tempde.destEventId > currentEventNum[tempde.destId] -1)
//                        //  currentEventNum[tempde.destId]= tempde.destEventId +1;
//                        //numEvents[tempde.srcId]--;
//                        //numEvents[tempde.destId]--;
//                        //    Events.remove(tempde);
//                    }
//                }
//                while(!tempVector.isEmpty ()){
//                    DisplayEvent debug =(DisplayEvent)tempVector.get (0);
//                    System.out.println ("Deleting Event SrcID ="+debug.srcId+" SEId ="+debug.srcEventId+" DestId="+debug.destId+"DestEventId="+debug.destEventId);
//                    Events.remove ((DisplayEvent)tempVector.remove (0)) ;
//
//                    if(debug.srcEventId > currentEventNum[debug.srcId] -1)
//                        currentEventNum[debug.srcId]= debug.srcEventId +1;
//                    if(debug.destEventId > currentEventNum[debug.destId] -1)
//                        currentEventNum[debug.destId]= debug.destEventId +1;
//                    //                Events.remove((DisplayEvent)tempVector.remove(0)) ;
//
//
//                }
//
//            }
//            for(int i = 0; i< pendingSends.size ();i++){
//                pe = new PendingEvent (srcId,destId, command , msg,state);
//                PendingEvent peOld = (PendingEvent) pendingSends.get (i);
//
//                if(pe.compare (peOld)){
//                    de = new DisplayEvent (srcId,destId,pe.srcEventId, command ,msg, peOld.srcState, pe.srcState);
//                    numEvents[destId]++;
//                    de.srcEventId = peOld.srcEventId;
//                    pendingSends.remove (peOld);
//                    Events.add (de);
//                    prepareDisplay ();
//                    update ();
//                    return;
//                }
//            }
//            System.out.println ("\n\n\n NO Corresponding SEND found **** \n\n\n ");
//        }
//        prepareDisplay ();
//        update ();
//    }
//
//
//    public  void prepareDisplay (){
//        // if(Events.isEmpty())return;
//
//
//
//        int maxY= myCanvas.getHeight ()-50;
//        int maxX= myCanvas.getWidth ()-80;
//        int lMargin =50;
//        int tMargin =50;
//        int rMargin =50;
//        int gapText =20;
//        int gapX  = 120;
//        int gapY = (maxY)/maxProc;
//        int radius = 10;
//        int eventsSize = Events.size ();
//        int maxEvents = MyUtil.maxArray (numEvents, numEvents.length);
//        lastX =0;
//
//        for(int i =0;i< gui.getNumProc ();i++){
//             y[i][0]= tMargin +  i*gapY;
//            for( int j = 0 ; j < numEvents[i]; j++){
//                //int x1 = lMargin +  j*gapX[i];
//                int y1 =tMargin +  i*gapY;
//                //g.drawOval(x1-radius,y1-radius,radius*2,radius*2);
//                x[i][j] =lMargin ;//x1;
//                y[i][j]= y1;
//
//            }
//               /*
//                Stroke sOld ;
//                BasicStroke stroke;
//                sOld =g.getStroke();
//                float[] f = new float [5];
//                f[0] =1;
//                f[1] =f[2] =f[3]=f[4]= 0;
//                Color oldC = null;
//                if(s.node[i].getNodeColor()> 0){
//                 oldC = g.getColor();
//                 Color c = Util.translateColor(s.node[i].getNodeColor());
//                 g.setColor(c);
//                }
//                 if(i== s.gui.getSelectedNode()){
//                   stroke = new BasicStroke((float)3,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
//
//                }
//                else{
//                    stroke = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
//                }
//                g.setStroke(stroke);
//                g.drawLine(lMargin,y[i][0],maxX, y[i][numEvents[i]-1]);
//                g.setStroke(sOld);
//                if(s.node[i].getNodeColor()> 0){
//
//                 g.setColor(oldC);
//                }
//                g.drawString(String.valueOf(i), lMargin -20,y[i][0]);
//                */
//
//        }
//
//        for(int j= 0 ; j< Events.size ();j++){
//
//            DisplayEvent de = (DisplayEvent) Events.get (j);
//            int destEventId = Util.max (de.destEventId - currentEventNum[de.destId],0);
//            int srcEventId = Util.max (de.srcEventId - currentEventNum[de.srcId],0);
//
//
//            System.out.println ("SrcEId = " +srcEventId+"de.srcId=" +de.srcId+" DestEId = "+destEventId+"de.DestId= "+ de.destId );
//            if(x[de.srcId][srcEventId] > (x[de.destId][destEventId] - gapX)  ){
//
//                x[de.destId][destEventId] = x[de.srcId][srcEventId] + gapX;
//            }
//
//            x[de.srcId][srcEventId +1 ]= Util.max (x[de.srcId][srcEventId] + gapX,x[de.srcId][srcEventId +1]);
//            //if( (de.destEventId < 199) && (x[de.destId][de.destEventId +1 ] - x[de.destId][de.destEventId] <gapX[de.destId] ) ){
//            x[de.destId][destEventId+1] = Util.max (x[de.destId][destEventId] +gapX,x[de.destId][destEventId+1]);
//            if( x[de.destId][destEventId+1] > lastX) lastX =x[de.destId][destEventId+1] ;
//
//            //}
//
//        }
//            /*
//            for(int i =0;i<s.gui.getNumProc();i++){
//
//                for( int j = 0 ; j < numEvents[i]; j++){
//                    int x1= x[i][j];
//                    int y1 =y[i][j];
//                    g.drawOval(x1-radius,y1-radius,radius*2,radius*2);
//                    //g.drawString(String.valueOf(j),x1,y1);
//
//                }
//            }
//             */
//
//
//
//
//    }
//
//
//
//    class MListener extends MouseInputAdapter{
//
//        public void mouseClicked (MouseEvent e){
//            int xc= e.getX ();
//            int yc =e.getY ();
//            for(int i = 0; i<gui.getNumProc ();i++){
//                if(isInRange (y[i][0],xc,yc,3)){
//                    gui.setSelectedNode (i);
//                    gui.left.updateDisplay ();
//                    myCanvas.repaint ();
//                    return;
//                }
//            }
//        }
//
//        boolean isInRange (int y1,int x2,int y2,int r){
//
//            int ydiff= Math.abs (y1-y2);
//            if( ydiff < r) return true;
//            return false;
//        }
//
//    }
//
//
//    class MyCanvas extends Canvas{
//
//
//        public void paint (Graphics g1){
//            //if(Events.isEmpty())return;
//           // int maxProc = s.gui.getNumProc ();
//
//            int maxY= myCanvas.getHeight ()-50;
//            int maxX= myCanvas.getWidth ()-80;
//            int lMargin =50;
//            int tMargin =50;
//            int rMargin =50;
//            int gapText =20;
//            int gapX  = 120; //[] = new int [maxProc];
//            int gapY = (maxY)/maxProc;
//            int radius = 10;
//            int eventsSize = Events.size ();
//            int maxEvents = MyUtil.maxArray (numEvents, numEvents.length);
//            lastX =0;
//            Graphics2D g = (Graphics2D) (g1);
//            //g.drawRect (20,40,maxX,maxY);
//
//            for(int i =0;i<maxProc;i++){
//             /*
//                for( int j = 0 ; j < numEvents[i]; j++){
//                    //int x1 = lMargin +  j*gapX[i];
//                    int y1 =tMargin +  i*gapY;
//                    //g.drawOval(x1-radius,y1-radius,radius*2,radius*2);
//                    x[i][j] =lMargin ;//x1;
//                    y[i][j]= y1;
//
//                }
//
//              */
//                Stroke sOld ;
//                BasicStroke stroke;
//                sOld =g.getStroke ();
//                float[] f = new float [5];
//                f[0] =1;
//                f[1] =f[2] =f[3]=f[4]= 0;
//                Color oldC = null;
//                if(comm.node[i].getNodeColor ()> 0){
//                    oldC = g.getColor ();
//                    Color c = MyUtil.translateColor (comm.node[i].getNodeColor ());
//                    g.setColor (c);
//                }
//                if(i== comm.gui.getSelectedNode ()){
//                    stroke = new BasicStroke ((float)3.5,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
//
//                }
//                else{
//                    stroke = new BasicStroke (1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
//                }
//                g.setStroke (stroke);
//                g.drawLine (lMargin,y[i][0],maxX, y[i][0]);
//                g.setStroke (sOld);
//                if(comm.node[i].getNodeColor ()> 0){
//
//                    g.setColor (oldC);
//                }
//                g.drawString (String.valueOf (i), lMargin -20,y[i][0]);
//
//
//            }
//           /*
//            for(int j= 0 ; j< eventsSize;j++){
//
//                DisplayEvent de = (DisplayEvent) Events.get(j);
//                int i= de.destEventId;
//
//
//
//                if(x[de.srcId][de.srcEventId] > (x[de.destId][i] - gapX)  ){
//
//                    x[de.destId][i] =x[de.srcId][de.srcEventId] + gapX;
//                }
//
//                x[de.srcId][de.srcEventId +1 ]= Util.max(x[de.srcId][de.srcEventId] + gapX,x[de.srcId][de.srcEventId +1]);
//                //if( (de.destEventId < 199) && (x[de.destId][de.destEventId +1 ] - x[de.destId][de.destEventId] <gapX[de.destId] ) ){
//                x[de.destId][i+1] = Util.max(x[de.destId][i] +gapX,x[de.destId][i+1]);
//                if( x[de.destId][i+1] > lastX) lastX =x[de.destId][i+1] ;
//
//                //}
//
//            }
//            */
//            for(int i =0;i<gui.getNumProc ();i++){
//
//                for( int j = 0 ; j < numEvents[i]; j++){
//                    int x1= x[i][j];
//                    int y1 =y[i][j];
//                    g.drawOval (x1-radius,y1-radius,radius*2,radius*2);
//                    //g.drawString(String.valueOf(j),x1,y1);
//
//                }
//            }
//
//            for(int i= 0 ; i< Events.size ();i++){
//
//                DisplayEvent de = (DisplayEvent) Events.get (i);
//                int destEventId = de.destEventId - currentEventNum[de.destId];
//                int srcEventId = de.srcEventId - currentEventNum[de.srcId];
//
//                Stroke sOld ;
//                sOld =g.getStroke ();
//                /*float[] f = new float [4];
//                f[0]  =0;
//                f[1]=f[2] =f[3] =1;
//                 */BasicStroke s = new BasicStroke ((float)1.3);// (1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 3);
//                 g.setStroke (s);
//                 int x1= x[de.srcId][srcEventId];
//                 int y1 =y[de.srcId][srcEventId];
//                 int x2 = x[de.destId][destEventId];
//                 int y2 = y[de.destId][destEventId];
//                 int tempgapY= ((y2-y1)/Math.abs (y2-y1) * (radius + 13)) ;
//
//                 Color oldC = g.getColor ();
//                 Color c = MyUtil.translateColorMessages (de.command);
//                 g.setColor (c);
//                 g.drawLine (x1,y1,x2 ,y2);
//                 g.fillPolygon (MyUtil.getArrow (x1,y1,x2,y2, 10,5, 0.5));
//                 g.setStroke (sOld);
//                 g.drawString (de.srcState, x1-gapText ,y1 -tempgapY);
//                 g.drawString (de.destState,x2-gapText ,y2 +tempgapY);
//
//                 g.drawString ( de.command.substring (0,3),(x[de.srcId][srcEventId] + x[de.destId][destEventId]) /2 ,(y[de.srcId][srcEventId] + y[de.destId][destEventId]) /2);
//                 //g.fillOval (x[de.srcId][de.srcEventId] - radius  ,y[de.srcId][de.srcEventId]-radius , 2*(radius ),2*(radius ));
//                 g.setStroke (sOld);
//                 g.setColor (oldC);
//            }
//            System.out.println ("Pending Events = " + pendingSends.size ());
//            for(int i=0;i<pendingSends.size ();i++){
//
//                PendingEvent pe = (PendingEvent)pendingSends.get (i);
//                //int destEventId = pe.destEventId - currentEventNum[pe.destId];
//                int srcEventId = pe.srcEventId - currentEventNum[pe.srcId];
//
//                Stroke sOld ;
//                sOld =g.getStroke ();
//                float[] f = new float [5];
//                f[0] =1;
//                f[1] =f[2] =f[3]=f[4]= 0;
//                Stroke stroke = new BasicStroke ((float)0.4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1,f, 4);
//                g.setStroke (stroke);
//                int x1= x[pe.srcId][srcEventId];
//                int y1 =y[pe.srcId][0];
//                int x2 = Util.max (x[pe.destId][numEvents[pe.destId]],x1)+ gapX;
//                int y2 = y[pe.destId][0];
//                //int tempgapY= ((y2-y1)/Math.abs(y2-y1) * (radius + 13)) ;
//
//                Color oldC = g.getColor ();
//                Color c = MyUtil.translateColorMessages (pe.command);
//                g.setColor (c);
//                g.drawLine (x1,y1,x2 ,y2);
//                g.fillPolygon (MyUtil.getArrow (x1,y1,x2,y2, 10,5, 0.5));
//
//                //g.drawString(pe.srcState, x1-gapText ,y1 -tempgapY);
//                //g.drawString(pe.destState,x2-gapText ,y2 +tempgapY);
//
//                g.drawString ( pe.command.substring (0,3),(x1+x2) /2 ,(y1+y2) /2);
//                //g.fillOval (x[de.srcId][de.srcEventId] - radius  ,y[de.srcId][de.srcEventId]-radius , 2*(radius ),2*(radius ));
//                g.setStroke (sOld);
//                g.setColor (oldC);
//
//            }
//
//
//        }
//
//
//    }
//
//
//
//
//
//
//    class DisplayEvent{
//
//        int srcId;
//        int destId;
//        public int srcEventId;
//        int destEventId;
//        String command;
//        public String srcState;
//        String destState;
//        String msg;
//
//        public DisplayEvent ( int s, int d, int srcEventId, String command,String msg, String srcState, String destState){
//            srcId =s;
//            destId =d;
//            this.srcEventId = srcEventId ;//numEvents[srcId];
//            destEventId = numEvents[destId];
//            this.msg = msg;
//            this.command = command;
//            this.srcState = srcState;
//            this.destState = destState;
//        }
//
//    }
//
//
//    class PendingEvent{
//
//        int srcId;
//        int destId;
//        int srcEventId;
//        // int destEventId;
//        public String command;
//        public String srcState;
//        //String destState;
//        public String msg;
//        public PendingEvent ( int s, int d, String command,String msg, String state){
//            srcId =s;
//            destId =d;
//            srcEventId = numEvents[srcId];
//            //srcEventId = numEvents[srcId];
//            //destEventId = numEvents[destId];
//            this.msg =msg;
//            this.command = command;
//            this.srcState = state;
//        }
//
//        public  boolean compare (PendingEvent e2){
//            if(command.equals (e2.command) && msg.equals (e2.msg)&& destId == e2.destId && srcId == e2.srcId)
//                return true;
//            else return false;
//
//        }
//
//
//
//
//    }
//}
class SimEvent {
    
    static final int SEND  = 0;
    static final int BROADCAST =1;
    static final int RECEIVE = 2;
    
}




