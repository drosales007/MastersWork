import java.lang.reflect.*;
public class ClassDetails {
    
    public String className;
    public Class classLoaded;
    public String myLinkerName;
    public Class myLinker;
    public String methodNames[];
    public Method methods[];
    public Object methodParam[];
    public int numMethods;
    public String fieldNames[];
    public Field fields[];
    public int numFields;
    public Field localPredicates[];
    public int numLocalPredicates;
    public Method handleMsg;
    public String nodeColorName;
    public Method nodeColor;
    public String nodeColorType;
    public String setNames;
    public int setParam;
    public boolean init =false;
    //    public ClassDetails classDetails[][];
    public Linker myL;
    public ClassDetails(){
        //dummy values of classes
        myLinkerName = "Linker";
        nodeColorType= "Boolean";
        
        
    }
    
    public void load(){
        try{
            System.out.println(this);
            
            numMethods = methodNames.length;
            numFields = fieldNames.length;
            classLoaded = Class.forName(className);
            myLinker = Class.forName(myLinkerName);
            if(!init){
                methods = new Method[numMethods];
                fields = new Field[numFields];
                
                for(int i =0; i < methodNames.length ; i+=1){
                    methods[i] = classLoaded.getMethod(methodNames[i],(java.lang.Class[] )null);
                }
                for(int i =0; i < numFields ; i+=1){
                    fields[i] = classLoaded.getField(fieldNames[i]);
                }
                nodeColor =  classLoaded.getMethod(nodeColorName);
            }
            //handleMsg= classLoaded.getMethod("handleMsg", new Class[]{Message.class,int.class,String.class});
            handleMsg= classLoaded.getMethod("handleMsg", new Class[]{Msg.class,int.class,String.class});
        } catch( java.lang.Exception e) {
            e.printStackTrace();
        }
    }
    
    public Object instantiate(int myId){
        try{
            Class[] linkerArgsClass = new Class[] {String.class,int.class,int.class}; // basename, int myId, int numProc};
            Constructor linkerCons = myLinker.getConstructor(linkerArgsClass);
            Object[] linkerArgs = new Object[]{ " ", new Integer(myId), new Integer(MyUtil.getCommModule().num_nodes)};
            Linker myL = (Linker)linkerCons.newInstance(linkerArgs);
            Class[] mainArgsClass;
            Constructor mainCons;
            Object mainArgs[] ;
            if(setNames ==null) {// No parameters to constructor
                mainArgsClass = new Class[] {myLinker};
                mainCons =  classLoaded.getConstructor(mainArgsClass);
                mainArgs = new Object[1];
                mainArgs[0] = myL;
            } else {//Integer parameter
                mainArgsClass = new Class[] {myLinker, Integer.class};
                mainCons =  classLoaded.getConstructor(mainArgsClass);
                mainArgs = new Object[2];
                mainArgs[0] =myL;
                mainArgs[1] = new Integer(setParam);
            }
            System.out.println(myL.N+ " "+myL.numProc);
            Object ret = mainCons.newInstance(mainArgs);
            //setVarValues(ret);
            return ret;
            //return mainCons.newInstance(mainArgs);
        }catch( Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Object instantiate(int myId, Object o){
        try{
            Class[] linkerArgsClass = new Class[] {String.class,int.class,int.class}; // basename, int myId, int numProc};
            Constructor linkerCons = myLinker.getConstructor(linkerArgsClass);
            Object[] linkerArgs = new Object[]{ " ", new Integer(myId), new Integer(MyUtil.getCommModule().num_nodes)};
            //Linker myL = (Linker)linkerCons.newInstance(linkerArgs);
            myL = (Linker)linkerCons.newInstance(linkerArgs);
            //Class[] mainArgsClass = new Class[] {Linker.class, Object.class};
            Class[] mainArgsClass = new Class[] {myLinker, Object.class};
            Constructor mainCons =  classLoaded.getConstructor(mainArgsClass);
            Object mainArgs[] = new Object[]{myL,o};
            Object ret = mainCons.newInstance(mainArgs);
            //setVarValues(ret);
            return ret;
        }catch( Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void invokeFunction(Object obj, int i){
        try{
            methods[i].invoke(obj, new Object[0]);
        }catch( Exception e){
            e.printStackTrace();
        }
        
    }
    public int getNodeColor(Object o){
        // if(nodeColorType.  .class == boolean.class)
        //        try{
        //         Boolean b = (Boolean)nodeColor.invoke (o ,null);
        //
        //         if(b == Boolean.TRUE) return  1;
        //         else return 0;
        //      }catch( Exception e){
        //                        e.printStackTrace ();
        //        }
        try{
            if( nodeColorType == null || nodeColorType.equalsIgnoreCase("Boolean")){ // expect boolean return value
                
                Boolean b = (Boolean) nodeColor.invoke(o ,null);
                
                if(b == Boolean.TRUE) return  1;
                else return 0;
                
            }
            
            else if(nodeColorType.equalsIgnoreCase("Integer")){ // expect Integer return value
                Integer i = (Integer) nodeColor.invoke(o,null);
                return i.intValue();
            }
        }catch( Exception e){
            e.printStackTrace();
        }
        return 10;
    }
    public void handleMessage(java.lang.Object param1, SingleMessage param2) {
        /**@todo Implement this method*/
        try{
            
            handleMsg.invoke(param1, new Object[]{param2.newMsg , new Integer(param2.sourceId) , param2.msg.getTag()});
        }catch( Exception e){
            e.printStackTrace();
        }
        
    }
    public void setVarValues(Object obj) throws  java.lang.NoSuchFieldException,java.lang.IllegalAccessException{
        if(obj ==null) {
            System.out.println("Void object passed tosetParam in classDetails!! ******* Fatal error ??");
            return;
        }
        if(setNames ==null) {
            System.out.println("No Variables to initialize");
            return;
        }
        Field f = classLoaded.getField(setNames);
        f.setInt(obj, setParam);
        
    }
    public String toString(){
        
        return "className = "+ className +" myLinkerName = "+myLinkerName + " nodeColorName = "+nodeColorName + " nodeColorType  = "+nodeColorType;
    }
    
}