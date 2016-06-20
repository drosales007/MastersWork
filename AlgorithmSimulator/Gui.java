//
//import java.util.*;
//import javax.swing.*;
//import java.io.*;
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.event.*;
//
//
//public class Gui {
//    
//    Simulator s;
//    Container c;
//    public CommModule comm;
//    JButton startApplet;
//    JTextArea output;
//    JScrollPane scroll;
//    int i =0;
//    String op= "";
//    int numProc;// =6;
//    int MaxProc = 20;
//    public boolean initComplete =true;
//    public LeftToolBar left;
//    RightToolBar right;
//    TopToolBar top;
//    BottomToolBar bottom ;
//    public DrawArea drawarea;
//    JFrame mainWindow;
//    static int selectednode=0;
//    public Vector msgQ  = new Vector();
//    
//    public Gui(Simulator s , JFrame _mainWindow, CommModule _comm){
//        mainWindow = _mainWindow;
//        this.s = s;
//        this.c = mainWindow.getContentPane(); 
//        comm = _comm;
//    } // Gui constructor
//    public void refresh(){
//        mainWindow.repaint ();
//    }
//   /* public void update(){
//        if(drawarea != null && drawarea.nodeView != null)
//            
//            drawarea.nodeView.update();
//    }*/
//    public void init(){
//        numProc = comm.num_nodes;
//        
//        left = new LeftToolBar(this);
//        right = new RightToolBar(this);
//        top = new TopToolBar(this);
//        bottom  = new BottomToolBar(this);
//        drawarea = new DrawArea(this);
//        c.setLayout(new BorderLayout(5,5));
//        c.add(top,BorderLayout.NORTH);
//        c.add(left,BorderLayout.WEST);
//        c.add(right,BorderLayout.EAST);
//        c.add(bottom,BorderLayout.SOUTH);
//        c.add(drawarea,BorderLayout.CENTER);
//    	//drawarea.nodeView.setTopology();
//        initComplete = true;
//        refresh();
//    }
//    public static void setSelectedNode(int n){
//        selectednode = n;
//    }
//    public static int getSelectedNode(){
//        return selectednode;
//    }
//    public int getNumProc(){
//        return numProc;
//    }
//    
//    public void setNumProc(int n){
//        numProc= n;
//    }
//    
//    
//    
//    
//} // Gui
//
//
//
//
//class LeftToolBar extends JPanel implements ActionListener{
////    JButton selectVars ; //increment, decrement ;
//    Gui gui;
//    Container c;
//    JTextArea output;
//    JLabel label,labelUpdate;
//    JComboBox nodes;
//    JScrollPane scroll;
//    Vector displayVars =new Vector();
//    
//    
//    public  LeftToolBar(Gui gui){
//        this.gui =gui;
//        c = this;
//        
//        
//        label = new JLabel("Variables: Node "+ gui.getSelectedNode());
//        Box b = Box.createVerticalBox();
//        output = new JTextArea(32,15);
////        selectVars= new JButton("Select Variables");
////        selectVars.setToolTipText("Select variables to be displayed above ");
//        
//        c.setLayout(new FlowLayout());
//        b.add(label);
//        b.add(output);
//        b.add( new JScrollPane(output));
////        b.add(selectVars);
////        selectVars.addActionListener(this);
//        c.add(b);
//        
////        try{
////            FileReader ipConfig = new FileReader(gui.s.configFile2);
////            
////            BufferedReader input = new BufferedReader(ipConfig);
////            
////            for(int i = 0 ; i<gui.s.numVar2 ; i++){
////                // while(!input.ready());
////                String tempstr = new String();
////                tempstr = (input.readLine());
////                
////                
////                if(tempstr.equals("true")) System.out.println("Read File Line "+i+ " "+tempstr);
////                displayVars.add(i, new Boolean(tempstr));
////            }
////            ipConfig.close();
////            input = null ;
////            System.out.println("Vector inited to "+displayVars.toString());
////        }catch(Exception e){e.printStackTrace();}
////        
////        
////        // pack();
//        
//        
//    }
//    
//    
//    
////    public void repaint(){
////       super.repaint();
////    }
//    
//    public synchronized void updateDisplay(){
//        if(!gui.initComplete) return;
//        gui.setSelectedNode(gui.getSelectedNode());
//        label.setText(" Selected Node : "+gui.getSelectedNode());
//        output.setText(" ");
//        output.append(gui.comm.getVarDisplay(gui.getSelectedNode()) );
////        for (int j = 0;j< gui.getNumProc() ;j++){
////            output.append("Variables at Node : " + j +" \n");
////            
////            for(int i=0; i< gui.s.numVar2;i++){
////                if(((Boolean)displayVars.get(i)).booleanValue()){
////                    output.append( gui.s.getNameVar2(j,i) +" : " + gui.s.node[j].getVar2(i)+"\n");
////                    
////                }
////            }
////            output.append("**************\n");
////        }
//        
//        
//        
//        
//    }
//    public void actionPerformed(ActionEvent e){ 
////        varDialog prompt = new varDialog();
////        prompt.setModal(true);
////        prompt.show();
//    }
//    
////    class varDialog extends JDialog implements ActionListener{
////        Container c;
////        JCheckBox [] chkBox = new JCheckBox [gui.s.numVar2];
////        JButton okayButton,cancelButton;
////        JLabel label = new JLabel("Select variables to be displayed : \n\n ");
////        
////        public varDialog(){
////            setSize(200,300);
////            setLocation(200,200);
////            setTitle("Select Variables");
////            
////            c= getContentPane();
////            Box b = Box.createVerticalBox();
////            c.setLayout(new FlowLayout());
////            b.add(label);
////            for (int i = 0; i <  gui.s.numVar2 ;i++ ){
////                
////                chkBox[i] = new JCheckBox(gui.s.getNameVar2(0,i));
////                Boolean temp =(Boolean) displayVars.get(i);
////                chkBox[i].setSelected(temp.booleanValue());
////                
////                chkBox[i].setActionCommand(String.valueOf(i));
////                b.add(chkBox[i]);
////                
////            }
////            Box b2 = Box.createHorizontalBox();
////            
////            okayButton = new JButton("Okay");
////            
////            cancelButton = new JButton("Cancel");
////            
////            okayButton.setActionCommand("okay");
////            cancelButton.setActionCommand("cancel");
////            
////            okayButton.addActionListener(this);
////            cancelButton.addActionListener(this);
////            b.add(okayButton);
////            c.add(b);
////            //repaint();
////            pack();
////        }
//        
//        
//        
////        public void actionPerformed(ActionEvent e){
////            try{
////                String c = new String(e.getActionCommand());
////                if(c.equals("okay")){
////                    FileWriter opConfig = new FileWriter(gui.s.configFile2);
////                    BufferedWriter output = new BufferedWriter(opConfig);
////                    for(int i = 0; i< gui.s.f2.length ;i ++){
////                        displayVars.add(i,new Boolean(chkBox[i].isSelected()));
////                        Boolean temp = (Boolean)displayVars.get(i);
////                        output.write(temp.toString());
////                        output.newLine();
////                    }
////                    output.flush();
////                    opConfig.close();
////                    output = null;
////                    
////                    updateDisplay();
////                    //gui.repaint();
////                    dispose();
////                    
////                    
////                    return;
////                }
////                
////                if(c.equals("cancel")){
////                    dispose();
////                    return;
////                }
////            }catch (Exception ex){ex.printStackTrace(); dispose();}
////            
////        }
////        
////        
////        
////        
////        
////    }
//    
// 
//    
//}
//
//class RightToolBar extends JPanel{
//    
//    JTextArea output,varOutput;
//    JScrollPane scroll;
//    Gui gui;
//    Container c;
//    JLabel label;
//    
//    public  RightToolBar(Gui gui){
//        this.gui =gui;
//        c = this;
//        label = new JLabel("Message History");
//        Box b = Box.createVerticalBox();
//        output = new JTextArea(35,15);
//        c.setLayout(new FlowLayout());
//        b.add(label);
//        b.add(output);
//        b.add( new JScrollPane(output));
//        c.add(b);
//    }
//    
//    
//}
//class TopToolBar extends JPanel implements ActionListener{
//    Gui gui;
//    Container c;
//    
//    //int height = gui.comm.
//    JButton[] button ;
//    int numButtons;
//    Vector buttonList;
////    JButton acceptMessage;
//    public  TopToolBar(Gui gui){
//        this.gui =gui;
//        c = this;
//        Box b = Box.createHorizontalBox();
//        c.setLayout(new FlowLayout());
//        buttonList = gui.comm.getFuncNames(gui.getSelectedNode ());
//        numButtons = buttonList.size ();
//        button =  new JButton[numButtons];
//           for(int i=0; i< numButtons ;i+=1){
//               
//            //if(gui.s.getNameFunc2(0,i) != null){
//                button[i] = new JButton( ((SingleFunction)buttonList.get(i)).toString () );
//                b.add(button[i]);
//                button[i].addActionListener(this);
//                button[i].setActionCommand(String.valueOf(i));
//            }
//        
////        acceptMessage = new JButton(" Accept Message");
////        acceptMessage.setActionCommand("100");
////        acceptMessage.addActionListener(this);
////        acceptMessage.setVisible(false);
////        b.add(acceptMessage);
//        c.add(b);
//    }
//        
////        for(int i=0; i< gui.s.m2.length ;i++){
////            if(gui.s.getNameFunc2(0,i) != null){
////                button[i] = new JButton(gui.s.getNameFunc2(0,i));
////                b.add(button[i]);
////                button[i].addActionListener(this);
////                button[i].setActionCommand(String.valueOf(i));
////            }
////        }
////        acceptMessage = new JButton(" Accept Message");
////        acceptMessage.setActionCommand("100");
////        acceptMessage.addActionListener(this);
////        acceptMessage.setVisible(false);
////        b.add(acceptMessage);
////        c.add(b);
////    }
// //   public void paintComponent(Graphics g){
//       
// //       if(gui.initComplete){
// //           acceptMessage.setVisible(!gui.s.node[gui.getSelectedNode()].getAutoAccept());
////        }
//        
// //   }
////      public void repaint(){
////        super.repaint();
////    }
//    public void actionPerformed(ActionEvent e){
//        //if(!gui.initComplete) return;
//        int selectedNode= gui.getSelectedNode();
//        
//        System.out.println("Selected node = " + selectedNode);
//        int i =Integer.parseInt(e.getActionCommand());
// //       if (i==100){
// //           gui.s.node[selectedNode].funcAcceptMessage();
////            return;
////        }
//        System.out.println(" Calling Function " + i + "on Node " + selectedNode);
//        gui.comm.invokeFunction((SingleFunction)buttonList.get(i) , gui.getSelectedNode ()); 
////        gui.s.node[selectedNode].func2(i);
//        
//        return;
//        
//        
//    }
//    
//    
//    
//}
//class BottomToolBar extends JPanel implements ChangeListener, ActionListener{
//    Gui gui;
//    Container c;
////    JSlider numNodes,channelDelay;
//    JButton update, viewTrace;
////    JLabel labelUpdate,labelDelay,labelAccept;
//    Box b;
//    JCheckBox autoAccept;
//    public  BottomToolBar(Gui gui){
//        this.gui =gui;
//        c = this;
//        b = Box.createHorizontalBox();
//       update = new JButton("Start");
/////        update.setActionCommand("Start");
////        numNodes = new JSlider(2,15,6);
////        channelDelay = new JSlider(0,10000,3000);
////        numNodes.setPaintTicks(true);
////        numNodes.setPaintLabels(true);
////       numNodes.setMinorTickSpacing(1);
////        numNodes.setSnapToTicks(true);
////        numNodes.setPaintTrack(true);
////        channelDelay.setMinorTickSpacing(100);
////        channelDelay.setPaintTicks(true);
////        channelDelay.setPaintLabels(true);
////        channelDelay.setSnapToTicks(true);
////        channelDelay.setPaintTrack(true);
////        labelUpdate = new JLabel("Set Number of Nodes : "+numNodes.getValue());//"Change number of nodes");
////        labelDelay = new JLabel(" Set Channel delay : " + channelDelay.getValue());
////        labelAccept = new JLabel(" Auto Accept Messages :" );
// //       autoAccept = new JCheckBox(" Automatically accept messages at selected node", true);
//        viewTrace= new JButton("Switch to Process Trace View");
//       viewTrace.addActionListener(this);
//        c.setLayout(new FlowLayout());
////        b.add(labelUpdate);
////        b.add(numNodes);
//        
////        b.add(labelDelay);
////        b.add(channelDelay);
// //       b.add(update);
// //       c.add(b);
//          c.add(viewTrace);
////        numNodes.addChangeListener(this);
////        channelDelay.addChangeListener(this);
////        autoAccept.addActionListener(this);
////        update.addActionListener(this);
//    }
//    
//    public void actionPerformed(ActionEvent e){
//        if(e.getSource() == update){
//            
//            
//            if(e.getActionCommand().equals("Restart")){
////                gui.s.stop();
//                Util.mySleep((int)Channel.getDelay()+1000);
//                gui.initComplete = false;
////                gui.s.init();
//                update.setText("Start");
//                update.setActionCommand("Start");
//                return;
//            }
//            c.remove(b);
////            b.remove(numNodes);
////            b.remove(labelUpdate);
//            b.remove(update);
//            b.add(autoAccept);
//            b.add(viewTrace);
//            Dimension d = new Dimension(10,10);
//            b.add(new Box.Filler(d,d,d));
//            c.add(b);
//            //update.setText("Restart");
//            //  update.setActionCommand("Restart");
//            gui.initComplete = true;
//            Util.mySleep(500);
//            gui.left.updateDisplay();
////            gui.s.repaint();
//            return;
//            
//        }
//        
//        else if(e.getSource() == autoAccept){
//            if(!autoAccept.isSelected() && !gui.initComplete){
//                autoAccept.setSelected(true);
//                gui.top.repaint();
// //               gui.s.repaint();
//                return;
//            }
////            gui.s.node[gui.getSelectedNode()].setAutoAccept(autoAccept.isSelected());
//        }
//        else if(e.getSource() == viewTrace){
//            if (viewTrace.getText().equals("Switch to Process Trace View")){
//                viewTrace.setText("Switch to Nodes View");
//               gui.drawarea.addremove(gui.drawarea.processTrace,gui.drawarea.nodeView);
//                
//            }
//            else if(viewTrace.getText().equals("Switch to Nodes View")){
//                viewTrace.setText("Switch to Process Trace View");
//                
//               gui.drawarea.addremove(gui.drawarea.nodeView,gui.drawarea.processTrace);
//                
//                
//            }
//            
//      
//        }
//           repaint();   
//    }
//   public void stateChanged(ChangeEvent e){
////        if(e.getSource()== numNodes) {
//            
// //           gui.setNumProc( numNodes.getValue());
//     //       gui.drawarea.nodeView.setTopology();
//     //       gui.update();
////            String s= new String("Set Number of Nodes : "+numNodes.getValue());
////            labelUpdate.setText(s);
////        }
////        if(e.getSource()== channelDelay){
// //           Channel.setDelay(channelDelay.getValue());
////            labelDelay.setText(" Set Channel delay : " + channelDelay.getValue());
////        }
////        gui.s.repaint();
//    }
//    
////      public void repaint(){
////       // super.repaint();
////    }
////    public void paintComponent(Graphics g){
////        if(gui.initComplete){
//////            autoAccept.setSelected(gui.s.node[gui.getSelectedNode()].getAutoAccept());
////        }
////    }
//    
//}
//
//
//class DrawArea extends JPanel {
//    Gui gui;
//    NodeView nodeView;
//    ProcessTrace processTrace;
//    Container c;
//    Box b;
//    CardLayout cl;
//   int width = 640;
//    int height =480;
//    JScrollPane scrollPaneNode;// = new JScrollPane();
//    JScrollPane scrollPaneTrace;// = new JScrollPane;
//    public  DrawArea(Gui gui){
//        //setSize(width,height);
//        this.gui =gui;
//        
//       nodeView = new NodeView(gui.comm); 
//       scrollPaneNode = new JScrollPane(nodeView);
//       scrollPaneTrace = new JScrollPane(processTrace);
//       scrollPaneNode.setPreferredSize (new Dimension(width,height));
//       scrollPaneTrace.setPreferredSize (new Dimension(width,height));
//       scrollPaneNode.setBackground (Color.WHITE);
//       processTrace = new ProcessTrace(gui.comm, scrollPaneTrace);
//       
//        nodeView.setSize(width-15,height-15);
//        processTrace.setSize (3000, height-15);
////	nodeView.setSize(width,height);
//       //scrollPane.add(processTrace);
//	//scrollPane.add(nodeView);
//        //cl = new CardLayout();
//        //setLayout(cl);
//        add(scrollPaneNode);
//        repaint();
//        //add(nodeView,"NodeView");
//    }
//    
//    void addremove(NodeView n,ProcessTrace p){
//        
//       remove(scrollPaneTrace);
//       add(scrollPaneNode);
//       repaint ();
//       //scrollPane.add(nodeView);      
////       scrollPane.remove(processTrace);
////       scrollPane.setSize(width,height);
////       scrollPane.add(nodeView);
//        //cl.next(this);:w
//	//
//        
//        //add(n);
//        // remove(p);
//    }
//    
//    void addremove(ProcessTrace p,NodeView n){
//        // add(p);
//        remove(scrollPaneNode);
////       scrollPane.setSize(width,height);
//       add(scrollPaneTrace);
//       repaint();
////        scrollPane.remove(nodeView);
////        scrollPane.setSize(width,height);
////        scrollPane.add(processTrace);
//        // cl.next(this);
//        //remove(n);
//    }
//    
//}
