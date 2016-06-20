import java.net.*;
public class N2 {
    public static void main(String args[]) {
        try {
            NameService r = (NameService) 
//                  java.rmi.Naming.lookup("rmi://mario.ece.utexas.edu/MyNameServer");
                  java.rmi.Naming.lookup("rmi://localhost/MyNameServer");
            if (r == null) 
               System.out.println("Could not find the service.");
            else {
               r.insert("p2", "tick.ece", 2059);
               InetSocketAddress entry = r.search("p2");
               if (entry != null)
                  System.out.println(entry.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
