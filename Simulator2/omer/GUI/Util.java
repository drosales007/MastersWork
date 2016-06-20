import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.lang.reflect.*;


public class Util {
	
	public static Method getMethod(Class cls, String methodName, Class type[]) throws Exception
	{
		Method mth = null;
		Method mthds [] = cls.getDeclaredMethods();
    	int k;
    	for (k = 0; k < mthds.length; k++)
    	{
    		if (!(mthds[k].getName().equals(methodName)))
    			continue;
    		Type t[] = mthds[k].getParameterTypes();
    		if (t.length!=type.length)
    			continue;
    		int j;
    		for (j = 0; j < t.length; j++)
    		{
    			String orig = type[j].toString().toLowerCase();
    			String now = t[j].toString().toLowerCase();
    			if (!(orig.contains(now)))
    					break;
    		}
    		if (j==t.length)
    		{	
    			mth = mthds[k];
    			break;
    		}			
    	}
    	if (k==mthds.length)
    		throw new Exception("Cant find method!"); // cant find method!!
    	System.err.println("Return method: " + mth);
    	return mth;	
	}
	public static Object getObj(Type t, String s)
	{
		
		String type = ""+t;
		System.out.println("Type is: " + type);
		if (type.equals("int") || type.contains("java.lang.Integer")) //exact is "class java.lang.Integer"
			return new Integer(Integer.parseInt(s));
		if (type.equals("double") || type.contains("java.lang.Double"))
			return new Double(Double.parseDouble(s));	
		return s; //also in case of class java.lang.String
	
	}
    public static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }
    public static void mySleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    public static void createEmptyFile(String fname){
    	try{
        	BufferedWriter out1 = new BufferedWriter(new FileWriter(fname));
        	out1.close();
        }
    	catch (Exception e){
    		System.err.println(e);
    	}  
    }
    public static int[] returnValues(int[] init)
    {
    	int[] dest  =  new int [init.length];
        for (int i = 0; i < init.length; i ++)
        	dest[i]=init[i];
        return dest;
    }
    public static void appendFile(String fname, String line){
    	try
		{
    		BufferedWriter out = new BufferedWriter(new FileWriter(fname, true));
    		out.write(line);
    		out.close();
		}
    	catch (Exception e){
    		System.err.println(e);
    	}
    }
    public static void myWait(Object obj) {
        println("waiting");
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
    public static int maxArray(int A[]) {
        int v = A[0];
        for (int i=0; i<A.length; i++)
            if (A[i] > v) v = A[i];
        return v;
    }
    public static String writeArray(int A[]){
        StringBuffer s = new StringBuffer();
        for (int j = 0; j < A.length; j++)
        {
            s.append(String.valueOf(A[j]));
            if (j!=A.length-1)
            	s.append(" ");
        }
        return new String(s.toString());
    }
    public static void readArray(String s, int A[]) {
        StringTokenizer st = new StringTokenizer(s);
        for (int j = 0; j < A.length; j++)
            A[j] = Integer.parseInt(st.nextToken());
    }
    public static int searchArray(int A[], int x) {
        for (int i = 0; i < A.length; i++)
            if (A[i] == x) return i;
        return -1;
    }
    
    public static int searchArray(boolean A[], boolean x, int pid) {
        if(A[pid] == x) return pid;
        for (int i = 0; i < A.length; i++)
            if (A[i] == x) return i;
        return -1;
    }
    
    
    public static void println(String s){
        if (Symbols.debugFlag) {
            System.out.println(s);
            System.out.flush();
        }
    }
    private static Dashboard currentDB;
    public static void setDashboard(Dashboard db){
    	currentDB = db;
    }
    
    public static Dashboard getDashboard(){
    	return currentDB;
    }
}
