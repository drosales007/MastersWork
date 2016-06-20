import java.lang.reflect.*;
import java.util.*;
import java.io.*;
public abstract class Process implements MsgHandler {
	int myId, N;
    VectorClock vc;
    Linker linker;
    Vector eventQueue;
    Vector rejectedEvents;
	int currentLoadLine;
	public int color;
    public Process(Linker _linker) {
    	currentLoadLine = 1;
    	eventQueue = new Vector();
    	rejectedEvents = new Vector();
    	linker = _linker;
    	myId = linker.getMyId();
        N = _linker.getN();
        vc = new VectorClock(N,myId);
        Util.createEmptyFile("save/Pcs "+myId+".txt");
        Util.appendFile("save/Pcs "+myId+".txt", Util.getDashboard().getPcsName()+"\n");
        Util.createEmptyFile("trace/Pcs "+myId+".txt");
    }
    public void sendMsg(int destId, String tag, int msg) {
        sendMsg(destId, tag, String.valueOf(msg)+" ");
    }
    public int getN() { return N; }
    public void receiveMsg(Msg m) {
		vc.receiveAction(m.getVector());
   //		wakeUp(); //the event will always be in future
   		Util.appendFile("trace/Pcs "+myId+".txt","Recv (" + linker.getSimTime() + ") " + vc + " "+ m.srcId + ": (" +m.tag + ") " + m.msg + "\n");
//   		Util.println("Recv (" + linker.getSimTime() + ") " + vc + " "+ m.srcId + ": (" +m.tag + ") " + m.msg + "\n");
   		handleMsg(m);
    }
    public void sendMsg(int destId, String tag, String msg) {
    	vc.sendAction();
    	Util.println("[" + myId + "->" + destId + "]: (" +tag + ") " + msg);
        int vec[] = Util.returnValues(vc.getVector()); //copy by value otherwise we have the reference and it updates all the values and thats bad, everybody has the last updated value for that simTime
        Util.appendFile("trace/Pcs "+myId+".txt", "Send (" + linker.getSimTime() + ") " + vc + " "+ destId + ": (" +tag + ") " + msg + "\n");
        linker.sendMsg(new Msg(myId, destId, tag, msg, vec));        
    }
//    public void sendMsg(int srcid, int destId, String tag, String msg) { //check if its working ok, handling srcid ok, MOST PROBABLY NOT, the vector should be src vector, also when appending file
//    	vc.sendAction();
//    	Util.println("[" + srcid + "->" + destId + "]: (" +tag + ") " + msg);
//        int vec[] = Util.returnValues(vc.getVector()); //copy by value otherwise we have the reference and it updates all the values and thats bad, everybody has the last updated value for that simTime
//        Util.appendFile("trace/Pcs "+myId+".txt", "Send (" + linker.getSimTime() + ") " + vc + " "+ destId + ": (" +tag + ") " + msg + "\n");
//        linker.sendMsg(new Msg(srcid, destId, tag, msg, vec));        
//    }
    public void broadcastMsg(String tag, String msg) { //should be same as sendtoneighbors, linker will only send on connected links
        for (int i = 0; i < N; i++)
            if (i != myId) sendMsg(i, tag, msg);
    }
    
    public void broadcastMsg(String tag, int msg) {
        for (int i = 0; i < N; i++)
            if (i != myId) sendMsg(i, tag, msg);
    }
    public void sendToNeighbors(String tag, String msg) { //should be same as broadcastmsg, cuz linker checks this condition anyway
        for (int i = 0; i < N; i++)
            if (isNeighbor(i)) sendMsg(i, tag, msg);     
    }
//    public void relayToNeighbors(int srcid, String tag, String msg) {
//        for (int i = 0; i < N; i++)
//            if (isNeighbor(i) && srcid != i && i!= myId) sendMsg(srcid, i, tag, msg);     
//    }
    public boolean isNeighbor(int i) {
        if (linker.getConnectivity(i) != 0) 
        	return true;
        return false;
    }    
    public synchronized void myWait() {
    	try
		{
    		wait();
		}
    	catch (Throwable e) {
         	 System.err.println(e);
         }
    }
    public synchronized void wakeUp() {
//    	Util.println("["+myId+"] Got Notified");
        notifyAll();
    }
    public void addEvent(Event e) {
//    	Util.println("["+myId+"] Got Event:" +e);
        e.addTime(linker.getSimTime());
    	
    	eventQueue.add(e);
//    	Util.println("["+myId+"] Added Event:" +e);
        
    }
    public double nextTime() {
    	double best_time = Symbols.DONE;
    	for (int i = 0; i < eventQueue.size(); i++)
    	{
    		Event temp = (Event)eventQueue.get(i);
    		//Util.println("\t\t[" + myId + "] Eval nextTime: "+temp);
    		if (temp.getTime()<best_time  && temp.getTime()>linker.getSimTime())
    			best_time=temp.getTime();
    	}
    	return best_time;
    }
    public void sendMsg(int destId, String tag) {
        sendMsg(destId, tag, " 0 ");
    }
    public Event getEvent()  {
    	double best_time = linker.getSimTime()+0.01;
    	int best_index = -1;
    	for (int i = 0; i < eventQueue.size(); i++)
    	{
    		Event temp = (Event)eventQueue.get(i);
    		
    		if (temp.getMethodName().equals("receiveMsg"))
    		{
   				continue;
    		}
    		if (temp.getTime()<=best_time)
    		{
    			best_index = i;
    			best_time=temp.getTime();
    		}
    	}
    	if (best_index != -1 && best_time <= linker.getSimTime())
    	{
    		Event temp = (Event)eventQueue.get(best_index);
    		eventQueue.remove(best_index);
    		return temp;
    	}
    	return null;    	
    }
    public Event getMsgEvent(int src)  {
        double best_time = linker.getSimTime()+0.01;
    	int best_index = -1;
    	for (int i = 0; i < eventQueue.size(); i++)
    	{
    		Event temp = (Event)eventQueue.get(i);
    		
    		if (temp.getMethodName().equals("receiveMsg"))
    		{
    			Object args[] = temp.getArgs();
    			Msg m = (Msg)args[0];
    			if (m.srcId != src)
    				continue;
    		}
    		else
    			continue;
    			
    		if (temp.getTime()<=best_time)
    		{
    			best_index = i;
    			best_time=temp.getTime();
    		}
    	}
    	if (best_index != -1 && best_time <= linker.getSimTime())
    	{
    		Event temp = (Event)eventQueue.get(best_index);
    		eventQueue.remove(best_index);
    		return temp;
    	}
    	return null;    	

    }
    private String getNextLine() throws Exception
    {
    	BufferedReader in = new BufferedReader(new FileReader("load/Pcs "+myId+".txt"));
	 	String str = new String ("");
        for (int i = 0; i < currentLoadLine; i++){
        	in.readLine(); // waste Em
        }
        str = in.readLine();
        in.close();
        return str;
    }
    private void execSpEvent(String specialEventString) throws Exception
    {
    	try
		{
	    	Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + "] BEFORE executing special event: " + specialEventString + "vc: " + vc); //just for printout
	    	StringTokenizer st = new StringTokenizer(specialEventString,">");
			String str = st.nextToken(">");
			str = st.nextToken(" ");
			String className = st.nextToken(".").substring(1);
			String methodName = st.nextToken("(").substring(1);
			String rest = st.nextToken(")").substring(1);
			Object argument[] = new Object[0];
			Class type[] = new Class[0];
			String argu = "";
			if (!rest.equals("")) //has args
			{
				StringTokenizer st3 = new StringTokenizer(rest,",");
				argument = new Object[st3.countTokens()];
				type = new Class[st3.countTokens()];
				int ind = 0;
				while (st3.hasMoreTokens())
				{
					String thisarg = st3.nextToken();
					StringTokenizer st2 = new StringTokenizer(thisarg,"[");
					String hold = st2.nextToken("[");
					st2.nextToken(" ");  //waste
					String typestr = st2.nextToken("]").substring(1);
					type[ind] = Class.forName(typestr);
					if (typestr.contains("nt")) // for int and Integer
						argument[ind]=new Integer (Integer.parseInt(hold));
					else if (typestr.contains("ouble")) // for double
						argument[ind]=new Double(Double.parseDouble(hold));
					else if (typestr.contains("oolean")) // bool
						argument[ind]=new Boolean(Boolean.parseBoolean(hold));
					else
						argument[ind] = new String(hold);
					ind++;
				}
				
				for (int i = 0; i < argument.length; i++)
				{
					argu += argument[i] + "["+type[i]+"]";
					 if (i != argument.length-1)
					 	argu += ",";
				}
			}
			Util.println("5\n");
		        Util.appendFile("save/Pcs "+myId+".txt", "" + vc + " " + 
					"<" + Util.getDashboard().simTime + "> " + className+"."+methodName+"("+ argu + ")"
					+ "\n");
		        
		    Class cls = Class.forName(className);
		    Method mth =  Util.getMethod(cls,methodName,type);
		    
	   		if (className.equals(Util.getDashboard().getPcsName()) || className.equals("Process"))
	   			mth.invoke(Util.getDashboard().getPcs(myId), argument);
	   		else if (className.equals("Linker"))
	   			mth.invoke(Util.getDashboard().getPcs(myId).linker, argument);
	   		else if (className.equals("Dashboard"))
	   			mth.invoke(Util.getDashboard(), argument);
	   		Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + "] AFTER executing special event: " + specialEventString + "vc: " + vc); //just for printout
    	}
    	catch(Exception e)
		{
    		System.err.println("Exception in ExecSpEvent!!" + e);
		}
    }
    public synchronized void executeNextEvent(int src) 
    {
    	try 
		{
    		if (linker.getConnectivity(myId) == Symbols.DEAD) //make sure i am not dead
        		return;
	    	Event event = src == myId? getEvent(): getMsgEvent(src); //whether to get the msg or internal event 

	    	//Util.println("\t\t\t\t\t\t[" + myId + "] execNext src: "+src + " "+event);
	    	//Thread.sleep((long)linker.getConnectivity(myId,myId)); //disable this for now.. i.e. diagonal values are only for yes or no, no extra delay
	    	
	    	if (event == null)
	    	{
		    	if(Util.getDashboard().getMode()== Symbols.LOAD && src == myId && Util.getDashboard().LOADSTART) //if there is no new event but i am loading and its my internal event thread and LOADSTART is set, that is i am good to go
		    	{
		    		String str = getNextLine();
		    		if (str == null || str.contains("receiveMsg")) //i only execute own messages , non receive msg src == myid
			        {											// i just have to wait till somebody else executes it
			        	wait(); 
			        	return;
			        }
			        vc.tick();					//going to execute this statement
			        currentLoadLine++;
		        	execSpEvent(str);
		        	System.err.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + ", src=" + src + "] AUTO str: " + str); //just for printout
		        	checkRejectedEvents();
		    	}
		    	else //the rest of the stuff is false and i dont have an event, so nothing to do, no load happening either 
		    	{
		    		Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + ", src=" + src + "] null event"); //just for printout	        	
		    		wait();
		    		return;
		    	}
	    	}
    		else //new event from gui!! -- hmm this is disabled now, u cannot have more function calls when it is being loaded
    		{
    			Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + ", src=" + src + "] non null event"); //just for printout	        	
    			vc.tick();
    			if (Util.getDashboard().getMode()== Symbols.LOAD) //yeap i am loading stuff as well as there is a new event, maybe a msg event
    			{
   				 	String str = getNextLine();
   			        if (str == null || !str.equals(""+vc+" "+event))
   			        {
   			        	rejectedEvents.add(event);
   			        	vc.untick(); //undo the tick!
   			        	Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + ", src=" + src + "] NO! str: " + str + " vs. " + vc + " " + event); //bypass invokeMethod and save
   			        	checkRejectedEvents();
   			        	return;
   			        }
   			        else
   			        {
   			        	currentLoadLine++;
   			        	Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + ", src=" + src + "] YES str: " + str + " vs. " + vc + " " + event); //just for printout
   			        	Util.appendFile("save/Pcs "+myId+".txt", "" + vc + " " + event + "\n");        			
   			    		event.method.invoke(event.getObject(),event.getArgs());
   			    		checkRejectedEvents();
   			    		return;
    		        }    			            			        
    			}
    			//normal event coming, no load, just execute it    			
    			else 
    			{
    				Util.println("SSSSSSSSSSSSSSSSSSSTATUSSSSSSSSSSSSSSS: [myid=" +myId + ", src=" + src + "] Normal Event " + vc + " " + event); //just for printout
			        Util.appendFile("save/Pcs "+myId+".txt", "" + vc + " " + event + "\n");        			
			    	event.method.invoke(event.getObject(),event.getArgs());
			    	checkRejectedEvents();
			    	return;
    			}
    		}    		
    	}
        catch (Throwable e) {
          	 System.err.println("EXCEPTION!!!!!!!!!!!!!! 2" + e);
        }
    }
    private void checkRejectedEvents() throws Exception
    {
    	Event event = null;
    	for (int j =0; j < rejectedEvents.size(); j++) //only positive in case of LOAD, no effect in case of SAVE
  		{	   		        
	     	Util.println("******************** CHECKING REJECTED!");
	     	vc.tick();
	     	event = (Event)rejectedEvents.get(j);
	     	String str = getNextLine();
	        if (str == null || !str.equals(""+vc+" "+event))
	        {
        		vc.untick(); //undo the tick!
        		Util.println("[" +myId +"] NO MATCH in PENDING! str: " + str + " vs. " + vc + " " + event); //bypass invokeMethod and save
	        }
	        else
	        {
        		currentLoadLine++;
				Util.appendFile("save/Pcs "+myId+".txt", "" + vc + " " + event + "\n");
   		        event.method.invoke(event.getObject(),event.getArgs());
   		        rejectedEvents.remove(j);
   		        j = 0; //restart checking
        		Util.println("[" +myId +"] YES MATCH IN PENDING! str: " + str + " vs. " + vc + " " + event); //just for printout
	        }	
  		}
    }
    public static void printMethods() { //not used right now, i think delete it altogether
    	Class cls;
    	Method [] methods;
    	try {
            cls = Class.forName("Process");
            methods = cls.getDeclaredMethods();            
            for (int i = 0; i < methods.length; i++)
                System.out.println(""+i+": " + methods[i]);
          }
          catch (Throwable e) {
             System.err.println(e);
          }	
    }
//    public void setColor(int c){
//    	color = c;
//    }
//    public int getColor(){
//    	return color;
//    }
}
