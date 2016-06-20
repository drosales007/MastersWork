/*
 * NodeView.java
 *
 * Created on July 11, 2003, 12:33 AM
 */

/**
 *
 * @author  Vinit
 */

import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class NodeView extends JComponent {
	public int selectedNode;
    Container c;
    int centerx,centery,radius;
    //double theta;
    int[] x = new int[20];
    int[] y=new int[20];
    double[] theta = new double[20];
    int smallR=20;
    GuiNode[] guiNode = new GuiNode[20];
    Vector messages = new Vector();
    String lastMessage = new String();
    //NodeCanvas nodeCanvas;
    newMessageChecker mchk = new newMessageChecker();
    public boolean fastRefresh = false;
    //CommModule comm;
    public Vector msgQ  = new Vector();
    //int radius;
    public  NodeView(int width , int height){
        			//comm =_comm;
        //this.gui =comm.gui;
        //nodeCanvas = new NodeCanvas();
        setPreferredSize(new Dimension(width,height));
        setSize(width,height);
        setBackground(Color.white);
        //add(nodeCanvas);
        this.addMouseListener(new MListener());
        setTopology();
        setOpaque(true);
        mchk.start();
        refresh();
    }
    
    
    public void  paintComponent(Graphics g1){
        Graphics2D g= (Graphics2D) g1;
        //paint background
        Color oldC = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(oldC);
        
        
        g.drawRect(1,1,2*centerx-10,2*centery-10);
        
        for(int i=0 ;i<Util.getDashboard().N;i++){
            guiNode[i].drawNode(g);
        }
        
        g.drawString("Last Msg : "+lastMessage, 10,20);
        
        if(!msgQ.isEmpty()) {
            for(int i =0 ; i< msgQ.size();i++){
                ((GuiMessage)msgQ.elementAt(i)).draw(g);
            }
        }
    }
    
    
    public void refresh(){
        repaint();
    }
    
    public void update(Graphics g){
        
        if(fastRefresh){
            for(int i =0 ; i< msgQ.size();i++){
                ((GuiMessage)msgQ.elementAt(i)).draw(g);
            }
            fastRefresh = false;
            return;
        }
        
        super.update(g);
    }
    
    public void refresh(GuiMessage gm){
        fastRefresh = true;
        repaint();
    }
    public void setTopology(){
        int x,y;
        centerx = getWidth() /2;
        centery=getHeight() /2;
        radius = MyUtil.min(centerx,centery) -30 ;
        
        int N = Util.getDashboard().N;
        for(int i=0 ;i<N;i++){
            theta[i] = (2*Math.PI)* i / N;
            x=(int)(centerx + radius * Math.sin(theta[i]));
            y=(int)(centery - radius*Math.cos(theta[i]));
            guiNode[i]= new GuiNode( i ,x,y,smallR);
        }
        //gui.refresh();
    }
    
    public void drawMessages(Graphics2D g){
        //if(!gui.initComplete) return;
        
        if(!msgQ.isEmpty()) {
            for(int i =0 ; i< msgQ.size();i++){
                ((GuiMessage)msgQ.elementAt(i)).draw(g);
            }
        }
    }
    
    
    
    public void displayMessage(String str){
        lastMessage = str;
    }
    
    class newMessageChecker extends Thread{
        int counter =0;
        public void run(){
            while(true){
            	
                //TODO
                /*				
				SingleEvent e = comm.guiGetEvent(counter++); //(e, destId);
                //                if(null == e) {
                //                    System.out.println("Got Null Event from comm !!! :-(");
                //                }else{
                if(e.msg == null )  System.out.println("Got Null Event.Message from comm !!! :-(");
                //if(e.msg.getMessage ().equals('b'){
                
                if(e.eventType =='b')
                    for(int i =0; i< Util.getDashboard().N; i+=1){
                        e.msg.destId = i;
                        msgQ.add(new GuiMessage(e.msg));
                    }
                if(e.eventType =='s')
                    msgQ.add(new GuiMessage(e.msg));
                //                    }
                //nodeUpdated = true;
                
                Util.mySleep(5);
                refresh();
                */ // endo TODO
            	Util.mySleep(5);                
            }
        }
    }
    
    void addMessageForDisplay(Msg msg)
    {
    	msgQ.add(new GuiMessage(msg));
    	refresh();
    }
    
    class MListener extends MouseInputAdapter{
        
        public void mouseClicked(MouseEvent e){
            int x= e.getX();
            int y =e.getY();
            for(int i = 0; i<Util.getDashboard().N;i++){
                if(isInRange(guiNode[i].x,guiNode[i].y,x,y,smallR)){
                    if(e.getButton() == e.BUTTON1){
                    selectedNode = i;
                    Util.getDashboard().refreshGui();
                    return;
                    }
                    else if(e.getButton()==e.BUTTON3){
                        //System.out.println("**********Button 3 Clicked ********");
                        if(Util.getDashboard().isAlive(i)){
                        	Util.getDashboard().makeDead(i);
                        }
                        else
                        	Util.getDashboard().makeAlive(i,1);
                        Util.getDashboard().refreshGui();
                        return;
                    }
                }
            }
        }
        
        boolean isInRange(int x1,int y1,int x2,int y2,int r){
            int xdiff = Math.abs(x1-x2);
            int ydiff= Math.abs(y1-y2);
            if(xdiff <r && ydiff < r) return true;
            return false;
        }
        
    }
    
    class GuiPoints{
        public int x1,y1,x2,y2;
        public GuiPoints(int x1,int y1,int x2,int y2){
            this.x1=x1;this.y1=y1;this.x2=x2;this.y2=y2;
        }
        
    }
    
    class GuiNode {
        public int x;
        public int y;
        public boolean selected = false;
        int myId;
        int smallR;
        
        
        
        public GuiNode(int myId,int x,int y,int smallR){
            this.x =x;
            this.y = y;
            this.myId= myId;
            this.smallR=smallR;
            
            
        }
        
        public void drawNode(Graphics2D g){
            if(myId == selectedNode){
                selected =true;
            }else selected = false;
            Stroke sOld = g.getStroke();
            sOld =g.getStroke();
            if(selected){
                BasicStroke s = new BasicStroke(4);
                g.setStroke(s);
            }
            int myColor = Util.getDashboard().getPcs(myId).color;
             if( myColor> 0){
                Color oldC = g.getColor();
                
                Color c = MyUtil.translateColor(myColor);
                g.setColor(c);
                g.fillOval(x-smallR,y-smallR,2*smallR,2*smallR);
                g.setColor(oldC);
            }
            
            g.drawOval(x-smallR,y-smallR,2*smallR,2*smallR);
            g.setStroke(sOld);
            g.drawString(""+myId+" ",x-4,y+4);
            if(!Util.getDashboard().isAlive(myId)){
		//Font f =g.getFont();
                Color oldC = g.getColor();
                sOld = g.getStroke();
                BasicStroke s = new BasicStroke(4);
                g.setStroke(s);
                g.setColor(Color.magenta);
                g.drawLine(x-smallR-3, y-smallR -3, x+smallR+3, y+smallR+3);
                g.drawLine(x+smallR+3, y-smallR -3, x-smallR-3, y+smallR+3);
                g.setStroke(sOld);
                g.setColor(oldC);
            }            
        }
    }
    class GuiMessage extends Thread {
        int srcId, destId;
        long delay =3000,pauseTime =100;
        int maxSteps =150;
        int x1,y1,x2,y2, oldx2, oldy2;
        String command = new String();
        Vector colorMessages = new Vector();
        public GuiMessage(Msg msg){
            
            colorMessages.add("Request");
            colorMessages.add("Release");
            
            this.srcId= msg.getSrcId();
            this.destId = msg .getDestId();
            this.command = msg.getTag();
            
            this.x1 = guiNode[srcId].x;
            this.y1 = guiNode[srcId].y;
            oldx2 = x2 = x1;
            oldy2 = y2 =y1;
            
            this.start();
        }
        
        public void draw(Graphics g){
            
            Color oldC = g.getColor();
            Color c = MyUtil.translateColorMessages(command);
            Color erase = getBackground();
            synchronized(this){
                g.setColor(erase);
                g.drawLine(x1,y1,oldx2,oldy2);
                g.fillPolygon(MyUtil.getArrow(x1, y1,oldx2 , oldy2, 10, 5, 0.5));
                if(Math.abs(oldx2-x1)>25 ||Math.abs(oldy2-y1)  >25) g.drawString( command.substring(0,3),(x1 + oldx2)/2 ,(y1 + oldy2) /2);
                g.setColor(c);
                g.drawLine(x1,y1,x2,y2);
                g.fillPolygon(MyUtil.getArrow(x1, y1,x2 , y2, 10, 5, 0.5));
                if(Math.abs(x2-x1)>25 ||Math.abs(y2-y1)  >25) g.drawString( command.substring(0,3),(x1 + x2)/2 ,(y1 + y2) /2);
                oldx2 = x2;
                oldy2 =y2;
                g.setColor(oldC);
            }
        }
        
        public void run(){
            int step = 1;
            int index;
            for(step = 1 ;step < maxSteps; step++){
                synchronized(this){
                    x2 = guiNode[srcId].x + (guiNode[destId].x - guiNode[srcId].x)*step/maxSteps;
                    y2 = guiNode[srcId].y + (guiNode[destId].y - guiNode[srcId].y)*step/maxSteps;
                }
                refresh(this);
                try{
                    Thread.sleep((delay-pauseTime)/maxSteps);
                    
                }
                catch (Exception e){}
            }
            msgQ.remove(this);
            refresh();
        }
    }
}
