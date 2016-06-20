public interface SensorUser extends MsgHandler {
    void globalPredicateTrue(int G[]);
    void globalPredicateFalse(int pid);
    //public void initiateChecker(Sensor s);
}
