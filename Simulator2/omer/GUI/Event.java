/*
 * Created on Oct 8, 2005
 *
 */

/**
 * @author omer
 *
 */
import java.util.*;
import java.lang.reflect.*;
public class Event {
	double time;
	Object object;
	Method method;
	Object[] args;
	Class cls;
	
	public Event(String tmp)
	{
		StringTokenizer st = new StringTokenizer(tmp, "<");
	}
	//i think we can do object.getClass() to get class, but mayyyyyyyyybe it will be different in
	// some cases, for example, if its a process object, it can have both the algorithm (LamportMutex for eg)
	// and the process functions that can be executed, to keep things straightforward we just get it directly
	// it may be redundant but i need to check in all cases first whether it is redundant before removing it from here
	public Event(double _time, Object _object, Class _class, Method _method, Object[] _args)
	{
		time = _time;
		object = _object;
		cls = _class;
		method = _method;
		args = _args;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double t) {
		time = t;
	}
	public String toString(){		
		String argu = "";
		for (int i = 0; i < args.length; i++)
		{
			argu += args[i] + "["+args[i].getClass().toString()+"]";
			 if (i != args.length-1)
			 	argu += ",";
		}
		return "<" + time + "> " + cls.getName() +"."+ method.getName() +"("+ argu + ")";
	}
//	public String getClassName(){
//		return cls.getName();
//	}
	public Object getObject(){
		return object;
	}
	public String getMethodName(){
		return method.getName();
	}
	public Object[] getArgs(){
		return args;
	}
	public void addTime(double currTime){
		time += currTime;
	}
}
