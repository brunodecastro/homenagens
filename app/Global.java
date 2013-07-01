


import play.Application;
import play.GlobalSettings;
import util.InitialData;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		
		InitialData.execute();
	}

}
