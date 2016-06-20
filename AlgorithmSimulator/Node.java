/*
 * Node.java
 *
 * Created on November 2, 2003, 12:58 AM
 */

/**
 *
 * @author  Vinit
 */
import java.util.*;
import java.lang.reflect.* ;
public class Node {
    public CommModule commModule;
    /*
    public String classNames[][];
    public int numClasses[]; //In each hierarchy
    public Class classesLoaded[][];
    public Object programsLoaded[][];
    public String methodNames[][][];
    public Method methods[][][];
    public int numMethods [][];
    public String fieldNames[][][];
    public Field fields[][][];
    public int numFields;
    public Field localPredicates[];
    public int numLocalPredicates;
    */
    public ClassDetails classDetails[][];
    public Object object[][];
    public int width;
    public int height[];
    public int max_height = 0;
    public int myId;
    
    
    /** Creates a new instance of Node */
    public Node (int _myId, ClassDetails _classDetails[][]) {
        
        myId = _myId;
        classDetails = _classDetails;
        width = classDetails.length;
        height = new int[width];
        for(int i =0; i< width ; i +=1){
            int j =0;
            for( j =0; (j < classDetails[i].length )&& (classDetails[i][j] != null) ; j+=1);
            height[i] = j;
            //height[i] = classDetails[i].length;
            //if(max_height < height[i]) max_height = height[i];
        }
        max_height = classDetails[0].length;
        initNode();
    }
    
    
    public void initNode(){
        object = new Object[width][max_height];
        for (int w=0; w <width; ++w){
            classDetails[w][0].load();
            object[w][0] = classDetails[w][0].instantiate(myId) ;
            for(int h=1; h< height[w] ; ++h){
                classDetails[w][h].load();
                object[w][h] = classDetails[w][h].instantiate(myId, object[w][h-1]) ;   //constructor.newInstance(arguments);
            }
            System.out.println("Node " + myId+ "Instantiation  Done");
        }
        
        
    }
    
    public int getNodeColor(){
        return (classDetails[0][0].getNodeColor(object[0][0]));
        
    }
    public String getVarDetails(){
        
        String str= new String();
        for (int w=0; w <width; ++w){
            for(int h=0; h< height[w] ; ++h){
                for(int i =0;i<classDetails[w][h].fieldNames.length; i+=1){
                    try{
                    str += "\n" + classDetails[w][h].fieldNames[i] + ": " + classDetails[w][h].fields[i].get(object[w][h]);
                    }catch( java.lang.Exception e){
                        e.printStackTrace ();
                    }
                }
            }
        }
        return str;
    }
    
    public Vector getFuncNames(){
        Vector v = new Vector();
        
        //String[][][] str =  new String[width][max_height][classDetails[0][0];
        for (int w=0; w <width; ++w){
            for(int h=0; h< height[w] ; ++h){
                for(int i =0;i<classDetails[w][h].numMethods ; i+=1){
                    v.add(new SingleFunction(w,h,i,classDetails[w][h].methodNames[i]));// + ": " + classDetails[w][h].fields[i].get(object[w][h]);
                }
            }
        }
        return v;
    }        
        
        
    
    public void invokeFunction(int i, int j, int func1){ 
        
        classDetails[i][j].invokeFunction(object[i][j],func1); 
    }
     public void recvdMessage (SlicingToken t) {
        /**@todo Implement this method*/
        
        for(int w=0;w<width;++w){
            //Util.println ("Recvd message : Sending message to w = " + w+ " level  " + (height[w]-1)) ;
            classDetails[w][height[w]-1].myL.receiveToken(t) ;
        }
        
    }  
    
    public void recvdMessage (SingleMessage param1) {
        /**@todo Implement this method*/
        
        for(int w=0;w<width;++w){
            //Util.println ("Recvd message : Sending message to w = " + w+ " level  " + (height[w]-1)) ;
            classDetails[w][height[w]-1].handleMessage(object[w][height[w]-1] , param1) ;
        }
        
    }
    
}

