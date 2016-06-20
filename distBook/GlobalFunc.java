import java.util.*;
public class GlobalFunc extends Process implements GlobalService {
    FuncUser prog;
    SpanTree tree = null;
    LinkedList<Integer> pending = null;
    int myValue;
    int answer;
    boolean answerRecvd;
    public GlobalFunc(MsgHandler initComm, boolean isRoot) {
        super(initComm);
        tree = new SpanTree(comm, isRoot);
    }
    public void initialize(int myValue, FuncUser prog) {
        this.myValue = myValue;
        this.prog = prog;
        tree.waitForDone();
        Util.println(myId + ":" + tree.children.toString());
    }
    public synchronized int computeGlobal() {
        pending = new LinkedList<Integer>();
        pending.addAll(tree.children);
        notifyAll();
        while (!pending.isEmpty()) myWait();
        if (tree.parent == myId) { // root node
            answer = myValue;
        } else { //non-root node
            sendMsg(tree.parent, "subTreeVal", myValue);
            answerRecvd = false;
            while (!answerRecvd) myWait();
        }
        sendChildren(answer);
        return answer;
    }
    void sendChildren(int value) {
	for (int child : tree.children)
            sendMsg(child, "globalFunc", value);
    }
    public synchronized void handleMsg(Msg m, int src, String tag) {
        tree.handleMsg(m, src, tag);
        if (tag.equals("subTreeVal")) {
            while (pending == null) myWait();
            pending.remove(src);
            myValue = prog.func(myValue, m.getMessageInt());
        } else if (tag.equals("globalFunc")) {
            answer = m.getMessageInt();
            answerRecvd = true;
        }
    }
}

