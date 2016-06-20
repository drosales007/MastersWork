import java.net.*;
import java.util.LinkedList;
public class N5 {
    public static void main(String args[]) {
        try {
            NameService2 r = (NameService2) 
//                  java.rmi.Naming.lookup("rmi://mario.ece.utexas.edu/MyNameServer");
                  java.rmi.Naming.lookup("rmi://localhost/MyNameServer2");
            if (r == null) 
               System.out.println("Could not find the service.");
            else {
               r.insert("p3", "blocktick.ece", 2059);
               InetSocketAddress entry = r.search("p3");
               if (entry != null)
                  System.out.println(entry.toString());
		else 
                  System.out.println("null entry");
               LinkedList<Integer> CL =  new LinkedList<Integer>();
               LinkedList<Integer> DL;
	       CL.add(1); CL.add(2); CL.add(3); CL.add(4); CL.add(5);
	       //System.out.println(CL);
	       //System.out.println(CL.getFirst());
	       //System.out.println(CL.getFirst());
               DL = CL;
	       r.handleList(DL);
	       //System.out.println(CL.getFirst());
	       //System.out.println(CL);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
