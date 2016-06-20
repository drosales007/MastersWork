/**
 * Util.java
 *
 */
import java.awt.*;
import java.util.*;
public class MyUtil {
    public static int max(int i, int j){
        if(i>j)return i ;
        return j;
    }
    public static int min(int i, int j){
        if(i<j)return i ;
        return j;
    }
    
    static CommModule commModule ;
   public static CommModule getCommModule (){
       return commModule;
   }
   public static void setCommModule(CommModule c){
       commModule =c;
   }
  static Simulator sim;
  
    public MyUtil () {
        
    } // Util constructor
    
    public static void sleep(long delay){
        
        try{
            Thread.sleep(delay);
        }catch (Exception e){}
    }
    
    public static void mySleep(int delay){
        
        try{
            Thread.sleep(delay);
        }catch (Exception e){}
    }
    public static void mySleep(long delay){
        
        try{
            Thread.sleep(delay);
        }catch (Exception e){}
    }
    
    public static int maxArray(int [] a , int size){
        int max = 0;
        for(int i =0  ; i<size  && i < a.length;i++)
            if( a[i] > max) max =a[i];
        
        return max;
    }
    
   public static String getValue(Object o){
        String className = new String();
        String retString = new String();
        
        
        className = (o.getClass()).getName();
        if(className.startsWith("[" )){
             for(int i = 0; i< ((Object [])o).length;i++){
                retString = retString + " " + MyUtil.getValue(((Object [])o)[i]);
             }
        }
         else retString = o.toString ();
              //className.substring (1);
    return retString;    
   }
             
       
    public static Polygon getArrow(int x1, int y1, int x2, int y2, int headsize, int difference, double factor) {
        int[] crosslinebase = getArrowHeadLine(x1, y1, x2, y2, headsize);
        int[] headbase = getArrowHeadLine(x1, y1, x2, y2, headsize - difference);
        int[] crossline = getArrowHeadCrossLine(crosslinebase[0], crosslinebase[1], x2, y2, factor);
        
        Polygon head = new Polygon();
        
        head.addPoint(headbase[0], headbase[1]);
        head.addPoint(crossline[0], crossline[1]);
        head.addPoint(x2, y2);
        head.addPoint(crossline[2], crossline[3]);
        head.addPoint(headbase[0], headbase[1]);
        head.addPoint(x1, y1);
        
        return head;
    }
    public static int[] getArrowHeadLine(int xsource, int ysource,int xdest,int ydest, int distance) {
        int[] arrowhead = new int[2];
        int headsize = distance;
        
        double stretchfactor = 0;
        stretchfactor = 1 - (headsize/(Math.sqrt(((xdest-xsource)*(xdest-xsource))+((ydest-ysource)*(ydest-ysource)))));
        
        arrowhead[0] = (int) (stretchfactor*(xdest-xsource))+xsource;
        arrowhead[1] = (int) (stretchfactor*(ydest-ysource))+ysource;
        
        return arrowhead;
    }
    
    public static int[] getArrowHeadCrossLine(int x1, int x2, int b1, int b2, double factor) {
        int [] crossline = new int[4];
        
        int x_dest = (int) (((b1-x1)*factor)+x1);
        int y_dest = (int) (((b2-x2)*factor)+x2);
        
        crossline[0] = (int) ((x1+x2-y_dest));
        crossline[1] = (int) ((x2+x_dest-x1));
        crossline[2] = crossline[0]+(x1-crossline[0])*2;
        crossline[3] = crossline[1]+(x2-crossline[1])*2;
        return crossline;
    }
    /** TODO 
     *This method has hardcoded messages !!! :-( Must change
     *
     **/
    static Color translateColorMessages(String s){
        if(s.equalsIgnoreCase("request")|| s.equalsIgnoreCase("token")|| s.equalsIgnoreCase("signal")
            ||s.equalsIgnoreCase("election")||s.equalsIgnoreCase("termToken")||s.equalsIgnoreCase("invite")
            ||s.equalsIgnoreCase("proposal")||s.equalsIgnoreCase("phase1")
            ) return (Color.blue);
        if(s.equalsIgnoreCase("reply")|| s.equalsIgnoreCase("ack")||s.equalsIgnoreCase("parent")
            ||s.equalsIgnoreCase("accept")||s.equalsIgnoreCase("king")
        ) return (Color.red);
        if(s.equalsIgnoreCase("release")|| s.equalsIgnoreCase("marker")||s.equalsIgnoreCase("fork")||
        s.equalsIgnoreCase("path")||s.equalsIgnoreCase("reject")
        ) return (Color.magenta);
        
        return Color.black;
        
    }
    public static void myWait(Object obj) {
        try {
            obj.wait();
        } catch (InterruptedException e) {
        }
    }
      public static boolean lessThan(int A[], int B[]) {
        for (int j = 0; j < A.length; j++)
            if (A[j] > B[j]) return false;
        for (int j = 0; j < A.length; j++)
            if (A[j] < B[j]) return true;
        return false;
    }
    public static void readArray(String s, int A[]) {
        StringTokenizer st = new StringTokenizer(s);
        for (int j = 0; j < A.length; j++)
            A[j] = Integer.parseInt(st.nextToken());
    }
    public static void readMatrix(String s, int A[][]) {
        StringTokenizer st = new StringTokenizer(s);
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[i].length; j++)
                A[i][j] = Integer.parseInt(st.nextToken());
    }
    public static void setZero(int A[][]) {
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[i].length; j++)
                A[i][j] = 0;
    }
    public static void setMax(int A[][], int B[][]) {
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[i].length; j++)
                A[i][j] = max(A[i][j], B[i][j]);
    }
    
    static Color translateColor(int i){

        switch(i){
            case 0:
                //return Color.black;
                return (Color.white);
            case 1:
                //return (Color.black);
               return (Color.yellow);
            case 2:
                return (Color.blue);
            case 3:
                return (Color.green);
            case 4:
                return (Color.cyan);
                
            case 5:
                return (Color.pink);
            case 6:
                return (Color.lightGray);
            case 7:
                return (Color.orange);
        }
        return Color.red;
   }
    
} // Util
