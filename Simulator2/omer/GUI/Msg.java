import java.util.*;
public class Msg {
    public int srcId, destId;
    String tag, msg;
    int [] vclock;
    public Msg(int _srcId, int _destId, String _tag, String _msg, int[] _vclock) {
        srcId = _srcId;
        destId = _destId;
        tag = _tag;
        msg = _msg;
        vclock = _vclock;
    }
    public Msg(String msgString) {
    	StringTokenizer st = new StringTokenizer(msgString);
        srcId = Integer.parseInt(st.nextToken());
        destId = Integer.parseInt(st.nextToken());
        tag = st.nextToken();
        msg = st.nextToken("#");
    }
    
    public int getMessageInt() {
    	//Util.println("Message: " + msg);
        return Integer.parseInt(msg);
    }
    public String toString(){
        return String.valueOf(srcId) + " " + String.valueOf(destId) + " " + tag + " " + msg + "#";
    }
    public int getSrcId() {
        return srcId;
    }
    public int getDestId() {
        return destId;
    }
    public String getTag() {
        return tag;
    }
    public String getMessage() {
        return msg;
    }
    public int [] getVector() {
        return vclock;
    }
}

