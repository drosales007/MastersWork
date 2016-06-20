/*
 * Simulator.java
  * @author  Vinit
 */


import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.lang.reflect.*;
import java.net.*;

public class Simulator extends JApplet {
	
	static public boolean isApplet = true; 
    
	
	public Simulator () 
	{
		    	
	}
	
	static Dashboard db;
	//OS: i think these are for applet as well
//    static CommModule c ; 
    public static MainWindow  appWindow;   
    public Vector classList; 
//    NoClassSelector classSelector; //TODO OS
    static int numNodes = 8;    
    //OS: will look at the applet version later
    // Code for Applet version 
    public void init(){
        System.out.println("Testing !!!\n\n\n");
        URL main_file= null;
        InputStreamReader ioRead =null;
        try{
        main_file = new URL(this.getDocumentBase (),"Simulator.config.txt");
//        classSelector = new NoClassSelector(this);//TODO OS 
        Object content  = main_file.getContent();
        if(content instanceof InputStream){
                ioRead = new InputStreamReader((InputStream)content);
            
        }
        else{
            System.out.println(" The url input is not of type InputStream !!!\n\n\n");
        }
//        getContentPane ().add(classSelector);//TODO OS
        try{
        //StringReader file_str = new StringReader((String)main_file.getContent());
        BufferedReader in   = new BufferedReader(ioRead);
        classList = new Vector();
        while(in.ready() ){
            String line = in.readLine ();
            System.out.println("Read Line"+line);
//            Object cl = new ConfigFileReader(this.getDocumentBase (),line); //TODO OS
//            classList.add(cl);
        }

        }catch (Exception e){e.printStackTrace ();}
//        classSelector.setData(classList);//TODO OS
        }catch (Exception e){
            e.printStackTrace ();
            System.out.println("Could not read remote file at "+main_file+" Exiting ...... \n\n\n");
            return;
            
        }

    }
    public void simStart(){
        
//        ConfigFileReader selectedClassReader = (ConfigFileReader)classList.get(classSelector.selectedValue);
//        ClassDetails cd[][]= selectedClassReader.go();
//        db = new Dashboard("LamportMutex",numNodes,Symbols.SAVE, cd);
//        appWindow =  new MainWindow(); 
//        appWindow.show();
//        
        
        
    }
    /*Code for Applet version ends
     */
    
    
    
    /*Code for Application version of the program
     * Vinit Feb 23 05
     *The idea is to to have two configs for the simulator
     *a)The applet version (above)
     *b)A java Application ver with the foll added features
     *  1)Load existing config files ... call them init scripts ?
     *  2)Have a wizard to start a new simulation
     *  3)Save settings and load settings using binary dumps of seialized ClassDetails ?
     */
    public static void main(String args[]){
        
        ConfigForm config = new ConfigForm(); 
        isApplet = false;
        config.setVisible(true);
    }
}
