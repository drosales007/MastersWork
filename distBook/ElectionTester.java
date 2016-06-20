public class ElectionTester {
    public static void main(String[] args) throws Exception {
	Linker comm = new Linker(args);
        Election  g = new RingLeader(comm, comm.myId);
	//g.startListening();
	g.startElection();
        int leader = g.getLeader();
        System.out.println("The leader is " + leader);
    }
}

