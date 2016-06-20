public class GlobalFuncTester implements FuncUser {
    public int func(int x, int y) {
        return x + y;
    }
    public static void main(String[] args) throws Exception {
	Linker comm = new Linker(args);
        GlobalFunc g = new GlobalFunc(comm, (comm.myId == 0));
	//g.startListening();
        int myValue = Integer.parseInt(args[3]);
        GlobalFuncTester h = new GlobalFuncTester();
        g.initialize(myValue, h);
        int globalSum = g.computeGlobal();
        System.out.println("The global sum is " + globalSum);
    }
}
