import java.lang.reflect.*;
public class SensorTester {
	public static void main(String[] args) throws Exception {
		MsgHandler comm = new Linker (args);		
		VCTagger vcTagger = new VCTagger(comm);
		
		Class classLoaded = Class.forName(args[3]);
		Constructor mainCons = classLoaded.getConstructor(MsgHandler.class);
		Sensor sensor = (Sensor) mainCons.newInstance(vcTagger);
		
		classLoaded = Class.forName(args[4]);
		mainCons = classLoaded.getConstructor(Sensor.class);
		SensorUser app = (SensorUser) mainCons.newInstance(sensor);	
		
		app.init(null);
		
	}
}

