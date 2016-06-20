import java.lang.reflect.*; import java.io.*;
public class CameraTester {
	public static void main(String[] args) throws Exception {
		MsgHandler comm;
		Camera camera = null;
		CamUser app = null;
		comm = new Linker(args);
		
		Class classLoaded = Class.forName(args[3]);
		Constructor mainCons = classLoaded.getConstructor(MsgHandler.class);
		camera = (Camera) mainCons.newInstance(comm);
		
		classLoaded = Class.forName(args[4]);
		mainCons = classLoaded.getConstructor(Camera.class);
		app = (CamUser) mainCons.newInstance(camera);		
		app.init(null);
		Util.mySleep(200);
		camera.globalState();	
}
}
