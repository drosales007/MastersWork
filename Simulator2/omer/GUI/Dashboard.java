/**
 * @author omer
 *
 */
import java.lang.reflect.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
public class Dashboard {
	private int MODE; // 0 = save, 1 = load, 2= nothing    
	private Linker[] linker; //linker for intercommunication
    private Process [] pcses; //all the processes
    private ProcessThread[] threads; //thread for each process
    private String algorithm; //name of the processes
    public boolean LOADSTART;
    double simTime;
    public MainWindow mainWindow;
    public void setAppWindow(MainWindow m){
        mainWindow = m;
    }
    public int N;
    public String getPcsName()
    {
    	return algorithm;
    }
    public Dashboard(String _string, int _N, int _M)
	{
    	LOADSTART = false;
    	Util.println("DB Constructor");
    	Util.setDashboard(this);
    	N=_N;
        MODE = _M;
    	algorithm = new String(_string);
    	pcses = new Process[N];
    	linker = new Linker[N];
    	simTime = 0;
    	Util.println("Creating Linker");
    	for (int i = 0; i < N; i++) {
        	linker[i] = new Linker("",i,N);
        }
    	Util.println("Creating ProcessThread");
        threads = new ProcessThread[N];
        Util.println("Done with ProcessThread");
        try 
		{
            Class cls = Class.forName(algorithm); 
            Class partypes[] = new Class[1];
            partypes[0] = Linker.class;
            Constructor ctor = cls.getConstructor(partypes); //assuming we have an int, linker constructor
            Object arglist[] = new Object[1];
            Util.println("Creating Instances");            
            for (int i = 0; i < N; i++)
            {
            	arglist[0] = linker[i];
            	pcses[i] = (Process)ctor.newInstance(arglist);
            	threads[i] = new ProcessThread(algorithm,pcses[i]);
            	threads[i].start();
            }
        }
        catch (Throwable e) {
        	System.err.println(e);
        }
        Util.println("DB Constructor End");
    }
    private void process_menu() //not used, will be passed to the gui as a string later on
    {
		int i;
		Class cls;
		Method [] methods;
		try 
		{
            cls = Class.forName(algorithm);
            methods = cls.getDeclaredMethods();
            for (i = 0; i < methods.length; i++)
            {
            	String temp = "\t"+i+": " + methods[i];
            	if (temp.contains("()"))                
            		System.out.println(temp);
            }
		}
		catch (Throwable e) {
			System.err.println(e);
		}    	
    }
    private void linker_menu() //not used, will be passed to the gui as a string later on
    {
    	int i;
    	Class cls;
    	Method [] methods;
    	try {
            cls = Class.forName("Linker");
            methods = cls.getDeclaredMethods();
            for (i = 0; i < methods.length; i++)
            {
            	String temp = "\t"+i+": " + methods[i];
            	if (temp.contains("()"))                
            		System.out.println(temp);
            }
        
          }
          catch (Throwable e) {
             System.err.println(e);
          }    	
    }
    private void class_menu() //not used, will be passed to the gui as a string later on
    {
    	int i;
    	Class cls;
    	Method [] methods;
    	try {
            cls = Class.forName("Process");
            methods = cls.getDeclaredMethods();
            for (i = 0; i < methods.length; i++)
            {
            	String temp = "\t"+i+": " + methods[i];
            	if (temp.contains("()"))                
            		System.out.println(temp);
            }
          }
          catch (Throwable e) {
             System.err.println(e);
          }    
    }
    public static Method[] getAllMethods(String name) //gets all the methods for the specified class
    {
    	Class cls;
    	Method [] methods = new Method[0];
    	try {
            cls = Class.forName(name);
            methods = cls.getDeclaredMethods();
          }
          catch (Throwable e) {
             System.err.println(e);
          }
         return methods;
    }
    public static Method getSpecificMethod(String classname, String functionname) //get the specified method for that class, 
    																			// wont work with overloaded methods
    {
    	Method[] methods = getAllMethods(classname);
    	for (int i = 0; i < methods.length; i++){
    		if (methods[i].getName().equals(functionname))
    			return methods[i];
    	}
    	return null;    	
    }
    private void menu() //not used, will be passed to the gui as a string later on
    {
    	System.out.println("Menu Options");
    	System.out.println("------------");
    	System.out.println("1. Process Level Functions");
    	process_menu();
    	System.out.println("2. "+algorithm+" Level Functions");
    	class_menu();
    	System.out.println("3. Linker Level Functions");
    	linker_menu();
    }
    public void readInput() //read the input script file
    {
    	try
		{
    		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("input")));
    		String line = "";
    		while((line = br.readLine()) != null) {
    			 StringTokenizer st = new StringTokenizer(line);
    			 int thisdelay = Integer.parseInt(st.nextToken());
    			 int thispcs = Integer.parseInt(st.nextToken());
    			 String thisclass = st.nextToken();
    			 String thisfunction = st.nextToken();
    			 int thisargs = Integer.parseInt(st.nextToken());
    			 //Method  method = getSpecificMethod(thisclass,thisfunction);
    			 //Class[] types = method.getParameterTypes();
    			 Object arglist[] = new Object[thisargs];
    			 for (int i = 0; i < thisargs; i++)
    			 	arglist[i] = st.nextToken();
    			 Event e; 
    			 Class cls = Class.forName(thisclass);
    			 Method mthds [] = cls.getDeclaredMethods();
    			 Method method = null;
    			 for (int i = 0; i < mthds.length; i ++)
    			 	if (mthds[i].getParameterTypes().length == thisargs && (""+mthds[i]).contains(thisfunction))
    			 	{
    			 		method = mthds[i];
    			 		break;
    			 	}
				 if (thisclass.equals("Linker"))
				 	e = new Event(thisdelay,linker[thispcs],cls,method,arglist);
				 else
				 	e = new Event(thisdelay,pcses[thispcs],cls,method,arglist);
				 threads[thispcs].addEvent(e);
    			 System.out.println(line);
    		}
		}
    	catch (Throwable e) {
            System.err.println(e);
        }    
    }
    public void invokeMethod(int thispcs, String thisfunction)
    {
//    	if (thisfunction.equals("Set Connectivity"))
//    	{
//    		String dest = JOptionPane.showInputDialog("Dest Process?");
//    		String value = JOptionPane.showInputDialog("Value?");
//    		pcses[thispcs].linker.setConnectivity(dest,value);
//    		return;
//    	}
    	if (thisfunction.equals("NEXT"))
    	{
    		LOADSTART = true;
    		simNextTime();
    		return;
    	}
    	if (thisfunction.equals("STOP"))
    	{
    		MODE = Symbols.SAVE;
    		return;
    	}
    	String className = thisfunction.substring(0,thisfunction.indexOf("."));
    	String fnName = thisfunction.substring(thisfunction.indexOf(".")+1, thisfunction.indexOf("("));
    	String args = thisfunction.substring(thisfunction.indexOf("(")+1, thisfunction.indexOf(")"));
    	StringTokenizer st = new StringTokenizer(args,",");
    	Class c_args[] = new Class[st.countTokens()];
    	Object o_args[] = new Object[st.countTokens()];
    	int ind = 0;
    	try
		{
	    	while (st.hasMoreTokens())
	    	{
	    		String token = st.nextToken();
	    		String arginput = JOptionPane.showInputDialog("Argument #" + (ind+1) + " [" + token.replaceAll("java.lang.","") + "] ?");
	    		if (token.contains("nt")) //for int and integer
	    		{
	    			o_args[ind]=new Integer(Integer.parseInt(arginput));
	    			c_args[ind]=Class.forName("java.lang.Integer");
	    		}
	    		else if (token.contains("ouble"))
	    		{
	    			o_args[ind]=new Double(Double.parseDouble(arginput));
	    			c_args[ind]=Class.forName("java.lang.Double");
	    		}
	    		else if (token.contains("oolean"))
	    		{
	    			o_args[ind]=new Boolean(Boolean.parseBoolean(arginput));
	    			c_args[ind]=Class.forName("java.lang.Boolean");
	    		}
	    		else 
	    		{
	    			o_args[ind]=new String(arginput);
	    			c_args[ind]=Class.forName("java.lang.String");
	    		}
	    		ind++;
	    	}
	    	Class cls = Class.forName(className);
	    	Method method = Util.getMethod(cls,fnName,c_args);            
            Event e = null;
//    		if (algorithm.equals("GenericClass"))
//    			e = new Event(0,,((GenericClass)pcses[thispcs]).getGenericObject().getClass().getName(),fnName,o_args);
//    		else
            if (algorithm.equals("GenericClass"))
    		{
            	GenericClass g = (GenericClass)pcses[thispcs];
            	if (className.equals(g.className) || className.equals("Process"))
            		e = new Event(0,g.getGenericObject(),cls,method,o_args);
    		}
            else if (className.equals(algorithm) || className.equals("Process"))    		
    			e = new Event(0,pcses[thispcs],cls,method,o_args);            
            
            
            if (className.equals("Linker"))
    			e = new Event(0,pcses[thispcs].linker,cls,method,o_args);
            else if (className.equals("Dashboard"))
    			e = new Event(0,this,cls,method,o_args);
            
        	System.out.println("Created Event");
        	threads[thispcs].addEvent(e);	
        	threads[thispcs].wakeUp();
        	System.out.println("Added Event");
		
		} catch (Exception e)
		{
			System.err.println("Dashboard.invokeMethod: " + e);
		}

//    	System.out.println("[" + thispcs + "] Adding Event: " + thisfunction);
//    	if (thisfunction.contains("()"))
//    	{
//    		Object arglist[]  = new Object[0];
//    		thisfunction = thisfunction.substring(0,thisfunction.indexOf('('));
//    		Event e = null;
//    		if (algorithm.equals("GenericClass"))
//    			e = new Event(0,((GenericClass)pcses[thispcs]).getGenericObject(),((GenericClass)pcses[thispcs]).getGenericObject().getClass().getName(),thisfunction,arglist);
//    		else
//    			e = new Event(0,pcses[thispcs],algorithm,thisfunction,arglist);
//    		
//        	System.out.println("Created Event");
//        	threads[thispcs].addEvent(e);	
//        	threads[thispcs].wakeUp();
//        	System.out.println("Added Event");
//        	return;
//    	}
//    	//else one argument
//    	Object arglist[]  = new Object[1];    	
//    	String arginput = JOptionPane.showInputDialog("Argument Value?");
//    	if (thisfunction.contains("(Integer)"))
//        	arglist[0] = new Integer(Integer.parseInt(arginput));
//    	else if (thisfunction.contains("(Double)"))
//        	arglist[0] = new Double(Double.parseDouble(arginput));
//    	else
//    		arglist[0] = new String (arginput);
// 
//        Class clstemp = arglist[0].getClass();
//        thisfunction = thisfunction.substring(0,thisfunction.indexOf('('));
//
//		Event e = null;
//		if (algorithm.equals("GenericClass"))
//			e = new Event(0,((GenericClass)pcses[thispcs]).getGenericObject(),((GenericClass)pcses[thispcs]).getGenericObject().getClass().getName(),thisfunction,arglist);
//		else
//			e = new Event(0,pcses[thispcs],algorithm,thisfunction,arglist);
//
////        Event e = new Event(0,pcses[thispcs],string,thisfunction,arglist);
//    	System.out.println("Created Event");
//    	threads[thispcs].addEvent(e);	
//    	threads[thispcs].wakeUp();
//    	System.out.println("Added Event");
    	
    }
    private boolean simNextTime()
    {
		double best_time = Symbols.DONE;
		for (int i = 0; i < pcses.length; i++)
		{
			double temp = pcses[i].nextTime(); //will it wake up if its sleeping? i think if the fn is not synchronized it can always be called even if the thread is waiting there
			//Util.println("Process [" +i+ "]: " + temp + " Best: [" + best_time + "]");				
			if (temp < best_time)
				best_time = temp;
		}
		
		if (best_time == Symbols.DONE)
			simTime = simTime++;
		else
			simTime = best_time;
		
		Util.println("Dashboard.simTime " + simTime);
		for (int i = 0; i < pcses.length; i++)
		{
			linker[i].simThisTime(simTime);
			pcses[i].wakeUp(); //wake all of em
		}
		if (best_time == Symbols.DONE)
			return false;
		return true;
    }
    private void simulate() //simulate the complete thing
    {
    	Util.println("SIMULATING");
		
    	MyUtil.sleep(2000);
    	readInput();
    	Util.println("Input Read!!");
		boolean falseOnce = false;
    	for (int i = 0; i <20; i ++) //provide interface for the user here, to stop pause of keep running
    	{
    		if (!simNextTime())
    		{
    			if (falseOnce)
    				break;
    			falseOnce = true;
    		}
    		else
    			falseOnce = false;
    		MyUtil.sleep(2000);
    	}   	
    }
    public Process getPcs(int no){
    	return pcses[no];
    }
    public int getMode(){
    	return MODE;
    }
    public boolean isAlive(int id)
    {
    	return linker[id].getConnectivity(id) != Symbols.DEAD;
    }
    public void makeDead(int id)
    {
    	linker[id].setConnectivity(id,Symbols.DEAD);
    }
    public void makeAlive(int id, double speed)
    {
    	linker[id].setConnectivity(id,speed);
    }
//	public static void main(String[] args) 
//	{
//	//	Dashboard db = new Dashboard("Arrow",4,Symbols.SAVE); //five lamport mutexes will run
//	//	db.simulate();		
//	}
    public void refreshGui(){
        mainWindow.refreshAll (); 
        
    }
    public Vector getFuncNames(int node_num){
    	Vector v = new Vector();
    	
    	Class cls;
    	String tstring = algorithm;
    	if (algorithm.equals("GenericClass"))
    		tstring = ((GenericClass)pcses[0]).getGenericObject().getClass().getName();
    	try {
            cls = Class.forName(tstring);
            Method [] methods = cls.getDeclaredMethods();
            for (int i = 0; i < methods.length; i ++)
            {
            	String temp = ""+methods[i];
            	System.out.println("METHODSSSS: " + temp);
            	if (temp.contains("void "+tstring) && temp.contains("public "))
            	{

            		if (temp.contains("(java.lang.Integer)"))
            			v.add(methods[i].getName() + "(Integer)");
            		if (temp.contains("(java.lang.String)"))
            			v.add(methods[i].getName() + "(String)");
            		if (temp.contains("(java.lang.Double)"))
            			v.add(methods[i].getName() + "(Double)");
            		if (temp.contains("()"))
            			v.add(methods[i].getName() + "()");
            	}
            }
          }
          catch (Throwable e) {
             System.err.println(e);
          }
          v.add(new String("Set Connectivity"));
      	  v.add(new String("NEXT"));
      	if (MODE == Symbols.LOAD)
    		v.add(new String("STOP"));
    	System.out.println("DB.getFuncNames");
        return v;
    }
    public Vector getAllFuncNames(int node_num){
    	Vector v = new Vector();    	
    	Class cls;
    	String tstring = algorithm;
    	if (algorithm.equals("GenericClass"))
    		tstring = ((GenericClass)pcses[0]).getGenericObject().getClass().getName();
    	try {
    		for (int k = 0; k < 4; k++)
    		{
    			String kstring = "";
    			if (k == 0) kstring = tstring;
    			if (k == 1) kstring = "Process";
    			if (k == 2) kstring = "Linker";
    			if (k == 3) kstring = "Dashboard";
    			
    			
	            cls = Class.forName(kstring);
	            Method [] methods = cls.getDeclaredMethods();
	            for (int i = 0; i < methods.length; i ++)
	            {
	            	String temp = ""+methods[i];
	            	System.out.println("METHODSSSSS: " + temp);
	            	Class ret = methods[i].getReturnType();
	            	Class par[] = methods[i].getParameterTypes();
	            	if (!("void".equals(""+ret)) || temp.contains("native") || temp.contains("throws") || !temp.contains("public")) //return is not void
	            		continue;
	            	int m;
	            	for (m = 0; m < par.length; m++){
	            		String parm = ""+par[m];
	            		if (parm.equals("int") || parm.equals("double") || parm.equals("boolean") || parm.equals("class java.lang.String") || parm.equals("class java.lang.Integer") || parm.equals("class java.lang.Boolean") || parm.equals("class java.lang.Double")  )
	            			continue;
	            		else
	            			break;
	            	}
	            	if (m!=par.length)
	            		continue;
	            	String hold = kstring + "." + methods[i].getName()+"(";
	            	for (int j = 0; j < par.length; j++){
	            		hold+=par[j].getName();
	            		if (j<par.length-1)
	            			hold+=",";
	            	}
	            	hold += ")";
	            	v.add(hold);
	            	System.out.println("Added");
	            }
    		}
          }
          catch (Throwable e) {
             System.err.println(e);
          }
        v.add(new String("NEXT"));
      	if (MODE == Symbols.LOAD)
    		v.add(new String("STOP"));
    	System.out.println("DB.getFuncNames");
        return v;
    }
    public String  getVarDetails(int node_num){
    	if (algorithm.equals("GenericClass"))
    	{
    		return ""+pcses[0];//((GenericClass)pcses[0]).toString();
    	}
    	String v = "";
    	
    	Class cls;
    	try {
            cls = Class.forName(algorithm);
            Field [] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i ++)
            {
            	String temp = ""+fields[i];
//            	System.out.println(temp);
            	if (temp.contains("public"))                
            		v += fields[i].getName() + ": " + fields[i].get(pcses[node_num]) + "\n";
            }
          }
          catch (Throwable e) {
             System.err.println(e);
          }
    	System.out.println("DB.getVarDetails");
        return v;
    }
    public void hello(int i){
    	System.out.println("helloooo " + i);
    }
}
