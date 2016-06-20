import java.lang.reflect.*;
public class Linker {
    double [] connectivity; //the connectivity matrix -> my connectivity array now!
    double simTime; //this is the current simtime, dashboard updates it
    int myId, N;
    public Linker(String bn, int id, int numProc) {
    	//bn not used
    	myId = id;
    	N = numProc;
    	simTime = 0;
        connectivity = new double[N];		
		for (int i = 0; i < N; i++)
			connectivity[i] = Symbols.Default_Connectivity;
    }
    public double getConnectivity(int receiver) {
        return connectivity[receiver];
    }
    public void setConnectivity(String receiver, String value) { //for the script file interface
       	setConnectivity(Integer.parseInt(receiver),Double.parseDouble(value));
    }
	
    public void setConnectivity(int receiver, double value) {
    	Util.println("Linker.setConnectivity " + receiver + " " +value);
        connectivity[receiver] = value;;
    }
	public int getN() { 
		return N; 
	}
	public void displayConnectivity(){
		Util.println("*** Connectivity Matrix ***\n");
		for (int i = 0; i < N; i++)
			Util.println(" " + connectivity[i]);
	}
	public int getMyId() {
		return myId;
	}
	public void simThisTime(double time) {
		simTime = time;		
	}
	public double getSimTime(){
		return simTime;
	}
	
    public void sendMsg(Msg m) {
    	Util.println("Linker.sendMsg " + m);    	
    	Object arglist[] = new Object[1];
    	arglist[0] = m;
    	double delay = connectivity[m.destId];
    	if (delay != Symbols.DEAD) //the link is dead!
    	{
    		try
			{
    			Class cls = Class.forName("Process");
    			Class arg1 [] = new Class[1];
    			arg1[0] = Class.forName("Msg");
    			Method mth = cls.getDeclaredMethod("receiveMsg", arg1);
    			Util.getDashboard().getPcs(m.destId).addEvent(new Event(delay, Util.getDashboard().getPcs(m.destId), cls, mth, arglist));
    			Util.getDashboard().mainWindow.nodeView.addMessageForDisplay(m);
			}
    		catch(Exception e)
			{
    			System.out.println(e);
			}
    	}
    }
}
