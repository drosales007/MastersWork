public class SensorCircToken extends CircToken
implements MsgHandler, SensorUser {  
    Sensor checker;    
    public SensorCircToken(VCTagger comm){
        super(comm);        
    }
    public void init(MsgHandler app) {        
        super.init(app);
        if (!haveToken) checker.localPredicateTrue(((VCTagger) comm).vc);
    }
    public synchronized void sendToken() {
        super.sendToken();
        if (!haveToken) checker.localPredicateTrue(((VCTagger) comm).vc);
    }    
    public void globalPredicateTrue(int v[]){        
        System.out.println("******* Predicate true at:" + v.toString());
    }
    public void globalPredicateFalse(int pid){       
        System.out.println("--------Predicate false. Proc " + pid + " finished");
    }
}
