/*
 * ConfigForm.java
 *
 * Created on February 23, 2005, 8:53 PM
 */

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConfigForm extends JFrame {
	Dashboard db;
 //   CommModule c ;
    MainWindow  appWindow;
    int numNodes;
    JPanel jpLoad;
    JPanel jpStart;
    JPanel jpCurrent;
    /** Creates new form ConfigForm */
    public ConfigForm() {
        jpStart = new ConfigFormPane(this);        
        showStart();
        initComponents();
    }
    
    public void setNumNodes(int n){
           numNodes = n;
    }
    
    public void showStart(){
        jpCurrent = jpStart;
        getContentPane().add(jpCurrent);
        pack();
    }
    public void showLoadClass(File file){
        getContentPane().remove(jpCurrent);
//        jpLoad = new NoConfigFormLoadClass(this,file);//TODO OS
        jpCurrent = jpLoad;
        getContentPane().add(jpCurrent);
        pack();
    }
    public void load()
    {
    	String className = "";
		int count=8;
		try
		{
			BufferedReader in = new BufferedReader(new FileReader("load/Pcs 0.txt"));
			String str = in.readLine();
			className = str;
			str = in.readLine();
			StringTokenizer st = new StringTokenizer(str,"<");
			String strnew = st.nextToken();
			StringTokenizer st2 = new StringTokenizer(strnew, " ");
			count = st2.countTokens();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		
	 	
		numNodes = count;
		proceed(className,Symbols.LOAD);
    	
    }
    public void start()
    { 
    	
  //HERE is the BEGINNING START!
    	
    	String className = "";
    	
		
		
		JFileChooser fileDialog;
    	fileDialog = new JFileChooser();
        fileDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fileDialog.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileDialog.getSelectedFile();
            className= file.getName();
            className= className.substring(0, className.length()-6);
            System.out.println("The class to be loaded is " + className);
        }
        
		String input = JOptionPane.showInputDialog("How many nodes for " + className +"? [2-20]");
    	numNodes = Integer.parseInt(input);        	
    	proceed(className,Symbols.SAVE);
    	
    }
    
    private void proceed(String className, int mode){
    	if (numNodes < 2 || numNodes > 20)
    		numNodes = 8;
    	
    	
    	Util.println("Start!");
    	db = new Dashboard(className,numNodes,mode);    	
    	db.readInput();
    	appWindow =  new MainWindow();
        appWindow.show();
        getContentPane().remove(jpCurrent);
        jpCurrent = jpStart;
        getContentPane().add(jpStart);
        pack();
        
    
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        getContentPane().setLayout(new java.awt.FlowLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulation Wizard");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setName("Simulation Wizard");
        setResizable(false);
        jLabel4.setText("    ");
        getContentPane().add(jLabel4);

        getContentPane().add(jPanel1);

        pack();
    }//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigForm().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}