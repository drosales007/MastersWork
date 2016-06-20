class Bakery{
    int N;
    boolean[] choosing; // inside doorway
    int[] number;
    public Bakery()
    {
    	System.out.println("default" );
    }
    public Bakery(String s)
    {
    	System.out.println("string " + s );
    }
    public Bakery(int a, double b)
    {
    	System.out.println("int and double " + a + " " + b);
    	
    }
    public Bakery(double A, int a, String b){
    	System.out.println("three stuffs " + " " + A + " " + a+" " +b);
    	}
    public Bakery(Integer numProc) {
        N = numProc.intValue();
        choosing = new boolean[N];
        number = new int[N];
        for (int j = 0; j < N; j++) {
            choosing[j] = false;
            number[j] = 0;
        }
    }
    public void doit()
    {
    	System.out.println("doit");
    }
    public void requestCS(Integer ii) {
    	System.out.println("requestCS!!");
        	int i = ii.intValue();
        // step 1: doorway: choose a number
        choosing[i] = true;
        for (int j = 0; j < N; j++)
            if (number[j] > number[i])
                number[i] = number[j];
        number[i]++;
        choosing[i] = false;

        // step 2: check if my number is the smallest
        for (int j = 0; j < N; j++) {
            while (choosing[j]) 
            	; // process j in doorway
            while ((number[j] != 0) &&
                    ((number[j] < number[i]) ||
                    ((number[j] == number[i]) && j < i)))
            	;  // busy wait
        }
        Util.println("" + i + ": Got CS!");
    }
    public void releaseCS(Integer ii) { // exit protocol
    	int i = ii.intValue();
    	number[i] = 0;
    	Util.println("" + i + ": Left CS!");
    }
    public String toString()
    {
    	String temp = "choosing: ";
    	for (int i = 0;i < N; i++)
    	{
    		temp += choosing[i] + " ";
    	}
    	//temp += " ";
    	for (int i = 0;i < N; i++)
    	{
    		temp += number[i] + " ";
    	}
    	//temp += " ";
    	return temp;
    }
}


/*class Bakery extends Process implements Lock {
    boolean[] choosing; // inside doorway
    int[] number;
    public Bakery(Linker _linker) {
    	super(_linker);
        choosing = new boolean[N];
        number = new int[N];
        for (int j = 0; j < N; j++) {
            choosing[j] = false;
            number[j] = 0;
        }
    }
    public void requestCS1(int i) {
        // step 1: doorway: choose a number
        choosing[i] = true;
        for (int j = 0; j < N; j++)
            if (number[j] > number[i])
                number[i] = number[j];
        number[i]++;
        choosing[i] = false;

        // step 2: check if my number is the smallest
        for (int j = 0; j < N; j++) {
            while (choosing[j]) ; // process j in doorway
            while ((number[j] != 0) &&
                    ((number[j] < number[i]) ||
                    ((number[j] == number[i]) && j < i)))
                myWait(); // busy wait
        }
        Util.println("" + myId + ": Got CS!");
    }
    public void releaseCS1(int i) { // exit protocol
        number[i] = 0;
        Util.println("" + myId + ": Left CS!");
    }
    public void requestCS()
    {
    	requestCS1(myId);    
    }
    public void releaseCS(){
    	releaseCS1(myId);
    }
    public synchronized void handleMsg(Msg m) {
    	
    }
}*/