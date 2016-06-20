/*
 * TestFrame.java
 *
 * Created on January 16, 2004, 1:42 PM
 */

/**
 *
 * @author  Vinit
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class MainWindow extends javax.swing.JFrame {
    CommModule comm;
    NodeView nodeView;
    ProcessTrace processTrace;
    JScrollPane scrollPaneNode;// = new JScrollPane();
    JScrollPane scrollPaneTrace;
    Container w;
    
            // Top toolbar 
        
         JButton[] button ;
    int numButtons;
    java.util.Vector buttonList;
    
    /** Creates new form TestFrame */
    public MainWindow (CommModule _comm) {
        initComponents ();
        addWindowListener( new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                //System.exit(0);
                //hide ();
                //dispose();
                  if(! Simulator.isApplet) System.exit (0);
                  dispose();
                //System.exit(0);
            }
        });
        comm = _comm;
        myInit();
    }
        public MainWindow () {
        w = getContentPane ();
        initComponents ();
        //comm = _comm;
        //myInit();
    }
        
   public void refresh(){
    jTextAreaLocalVars.setText (comm.getVarDisplay (comm.getSelectedNode ()));   
    repaint();
       
   }
   public void refreshAll(){
          jTextAreaLocalVars.setText (comm.getVarDisplay (comm.getSelectedNode ()));
          nodeView.refresh();
          processTrace.refresh();
       
   }
   public void addDisplayEvent(SingleEvent se){
        String temp = jTextAreaMessageLog.getText ();
        jTextAreaMessageLog.setText(se + temp);
    
   }
   public void appendProcessLog(String se){
       String temp = jTextAreaProcessLog.getText();
       jTextAreaProcessLog.setText(se + temp);
       
   }
    public void myInit(){
        jScrollPane2.setViewportView(jTextAreaProcessLog);
        jMenuItem4.setSelected(false);
        jToolBar1.setPreferredSize(new java.awt.Dimension(getWidth(), jToolBar1.getHeight ()));
       int width = jPanel1.getWidth ();
       int height =jPanel1.getHeight ();
       //jPanel1.setPreferredSize (new Dimension(height,width));
       nodeView = new NodeView(comm,width-10,height-10); 
       nodeView.setPreferredSize (new Dimension(width,height));
       scrollPaneNode = new JScrollPane(nodeView);
       scrollPaneTrace = new JScrollPane();//processTrace);
       scrollPaneNode.setPreferredSize (new Dimension(width-5,height-5)); 
       scrollPaneTrace.setPreferredSize (new Dimension(width-5,height-5));
       scrollPaneNode.setBackground (Color.WHITE);
       processTrace = new ProcessTrace(comm, scrollPaneTrace);
       
        nodeView.setPreferredSize(new Dimension(width -10 ,height-10));
       processTrace.setPreferredSize (new Dimension(3000, height-25));
        jPanel1.add(scrollPaneNode);
        
        
        
        newEventDetector ne = new newEventDetector();
        ne.start ();
        // Top toolbar 
        jToolBar1.setLayout(new FlowLayout());
        //BoxLayout b = new Box.createHorizontalBox ();
        buttonList = comm.getFuncNames(comm.getSelectedNode ());
        numButtons = buttonList.size ();
        button =  new JButton[numButtons];
           for(int i=0; i< numButtons ;i+=1){
  
                button[i] = new JButton( ((SingleFunction)buttonList.get(i)).toString () );
                //b.add(button[i]);
                //jToolBar1.setLayout (Box.createHorizontalBox ());
                jToolBar1.add(button[i]);
                
                jToolBar1.addSeparator ();
                //button[i].setBorder ( BorderFactory.createEtchedBorder(javax.swing.border.LineBorder.createBlackLineBorder ()));
               
                button[i].addActionListener(new FunctionButtonActionListener());
                button[i].setActionCommand(String.valueOf(i));
            }
 
        pack();
    }
        

    class FunctionButtonActionListener implements ActionListener{ 
        public void actionPerformed(ActionEvent e){ 
        //if(!gui.initComplete) return;
        int selectedNode= comm.getSelectedNode();
        
        System.out.println("Selected node = " + selectedNode);
        int i =Integer.parseInt(e.getActionCommand());
 //       if (i==100){
 //           gui.s.node[selectedNode].funcAcceptMessage();
//            return;
//        }
        System.out.println(" Calling Function " + i + "on Node " + selectedNode);
       comm.invokeFunction((SingleFunction)buttonList.get(i) , comm.getSelectedNode ()); 
//        gui.s.node[selectedNode].func2(i);
        
        return;
        
        
    }
    }
    
    class newEventDetector extends Thread{
        
        public void run (){
            int i =0;
            while (true){
            SingleEvent se = comm.guiGetEvent(i++);
            addDisplayEvent(se);
            refresh();
            }
           }
        
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLocalVars = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMessageLog = new javax.swing.JTextArea();
        jTextAreaProcessLog = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu2.setText("Menu");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuBar2.add(jMenu2);

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setTitle("Distributed Program Simulator v0.1");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jToolBar1.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setDoubleBuffered(true);
        jToolBar1.setFont(new java.awt.Font("Dialog", 1, 14));
        jToolBar1.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jToolBar1.setPreferredSize(new java.awt.Dimension(500, 40));
        jToolBar1.setAutoscrolls(true);
        jToolBar1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 750, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 750, 620));

        jToggleButton1.setText("Process Trace");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 280, 160, 40));

        jScrollPane1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        jTextAreaLocalVars.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaLocalVars.setColumns(12);
        jTextAreaLocalVars.setEditable(false);
        jTextAreaLocalVars.setRows(15);
        jTextAreaLocalVars.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(), "Local Variables", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14)));
        jTextAreaLocalVars.setAutoscrolls(false);
        jTextAreaLocalVars.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextAreaLocalVarsMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jTextAreaLocalVars);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 170, 260));

        jScrollPane2.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        jTextAreaMessageLog.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaMessageLog.setColumns(12);
        jTextAreaMessageLog.setEditable(false);
        jTextAreaMessageLog.setLineWrap(true);
        jTextAreaMessageLog.setRows(15);
        jTextAreaMessageLog.setWrapStyleWord(true);
        jTextAreaMessageLog.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(), "Message Log", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14)));
        jScrollPane2.setViewportView(jTextAreaMessageLog);

        jTextAreaProcessLog.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaProcessLog.setColumns(12);
        jTextAreaProcessLog.setEditable(false);
        jTextAreaProcessLog.setLineWrap(true);
        jTextAreaProcessLog.setRows(20);
        jTextAreaProcessLog.setWrapStyleWord(true);
        jTextAreaProcessLog.setBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(), "Process Log", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14)));
        jScrollPane2.setViewportView(jTextAreaProcessLog);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 330, 160, 320));

        jMenu1.setText("Simulator");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("About");
        jMenuItem1.setToolTipText("About the Simulator");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenuItem2.setToolTipText("Exit the simulator");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("View");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        jMenuItem3.setText("Show  process trace");
        jMenuItem3.setToolTipText("Toggle Between Node View and Process Trace");
        jMenuItem3.setRolloverEnabled(true);
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });

        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Show Message Log");
        jMenuItem4.setToolTipText("Toggle between message log and process output log");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });

        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        jToggleButton1.doClick();
       //jToggleButton1ActionPerformed (evt);
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        if(jMenuItem4.isSelected()){
                jScrollPane2.setViewportView(jTextAreaProcessLog);
                jMenuItem4.setSelected(false);
                jMenuItem4.setText("Show Message Log");
        }
        else{
              jScrollPane2.setViewportView(jTextAreaMessageLog);
                jMenuItem4.setSelected(true);
                jMenuItem4.setText("Show Process Log");
        }
              
        refresh();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jTextAreaLocalVarsMouseClicked (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextAreaLocalVarsMouseClicked
        // Add your handling code here:
        refresh();
    }//GEN-LAST:event_jTextAreaLocalVarsMouseClicked

    private void jToggleButton1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // Add your handling code here:
        if(jToggleButton1.isSelected ()){
            jToggleButton1.setText("Hide Process Trace");
            jMenuItem3.setText("Hide  Process trace");
            jPanel1.remove(scrollPaneNode);
            jPanel1.add(scrollPaneTrace);
            repaint();
        }else {
            
          jToggleButton1.setText("Show Process Trace");
          jMenuItem3.setText("Show  process trace");
            jPanel1.remove(scrollPaneTrace);
            jPanel1.add(scrollPaneNode);
            repaint();   
            
        }
        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jMenuItem2ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // Add your handling code here:
        //hide();
        this.dispose();
      
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // Add your handling code here:
        new AboutDialog(this, true).show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenu2ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenu1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed
    
    /** Exit the Application */
    private void exitForm (java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        hide();
        this.dispose();
        //System.exit (0);
        
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
        new MainWindow ().show ();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaLocalVars;
    private javax.swing.JTextArea jTextAreaMessageLog;
    private javax.swing.JTextArea jTextAreaProcessLog;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    
}
