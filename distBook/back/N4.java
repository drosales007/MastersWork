import java.net.*;
public class N4 {
    public static void main(String args[]) {
        try {
            NameService r = (NameService) 
//                  java.rmi.Naming.lookup("rmi://mario.ece.utexas.edu/MyNameServer");
                  java.rmi.Naming.lookup("rmi://localhost/MyNameServer");
            if (r == null) 
               System.out.println("Could not find the service.");
            else {
               r.insert("p3", "blocktick.ece", 2059);
               InetSocketAddress entry = r.search("p3");
               if (entry != null)
                  System.out.println(entry.toString());
		else 
                  System.out.println("null entry");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
