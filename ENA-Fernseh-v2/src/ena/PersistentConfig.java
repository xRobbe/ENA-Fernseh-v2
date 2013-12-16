package ena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PersistentConfig {
	private final static String FILENAME = "config.properties";
	private final static String STATION = "Station";
	private final static String VOLUME = "Volume";
	private final static String RATIO = "Ratio";
	private final static String USERMODE = "Usermode";
	private final static int MAX_VOLUME = 100;
	
	private final static int MAX_USERMODE = 2;
	// 0 "Easy", 1 "Normal", 2 "Expert" 
	private final static int MAX_RATIO = 2;
	// 0 "16:9", 1 "4:3", 2 "2.35:1"

	private Properties properties = new Properties();
	private FileInputStream in;

	public PersistentConfig() {
		try {
			// check if configfile exists, create if not
			if (!((new File(FILENAME)).exists()))
				new FileOutputStream(FILENAME);
			
			in = new FileInputStream(FILENAME);
			properties.load(in);
			
			// check if config is valid, if not restore default 
			if (!(checkConfig())) {
				properties.setProperty(VOLUME, "50");
				properties.setProperty(STATION, "1");
				properties.setProperty(USERMODE, "0");
				properties.setProperty(RATIO, "0");
				properties.store(out(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setVolume(int volume) throws Exception {
		properties.setProperty(VOLUME, String.valueOf(volume));
		properties.store(out(), null);
	}

	public int getVolume() throws Exception {
		return Integer.parseInt(properties.getProperty(VOLUME));
	}

	public void setProgramm(int programm) throws Exception {
		properties.setProperty(STATION, String.valueOf(programm));
		properties.store(out(), null);
	}

	public int getProgramm() throws Exception {
		return Integer.parseInt(properties.getProperty(STATION));
	}

	public void setUsermode(int usermode) throws Exception {
		properties.setProperty(USERMODE, String.valueOf(usermode));
		properties.store(out(), null);
	}

	public int getUsermode() throws Exception {
		return Integer.parseInt(properties.getProperty(USERMODE));
	}

	public void setRatio(int ratio) throws Exception {
		properties.setProperty(RATIO, String.valueOf(ratio));
		properties.store(out(), null);
	}

	public int getRatio() throws Exception {
		return Integer.parseInt(properties.getProperty(RATIO));
	}

	// check if config is valid
	private boolean checkConfig() {
		if (!(properties.containsKey(VOLUME)))
			return false;
		if (Integer.parseInt(properties.getProperty(VOLUME)) > MAX_VOLUME)
			return false;
		if (Integer.parseInt(properties.getProperty(VOLUME)) < 0)
			return false;

		if (!(properties.containsKey(STATION)))
			return false;
		if (Integer.parseInt(properties.getProperty(STATION)) < 0)
			return false;

		if (!(properties.containsKey(USERMODE)))
			return false;
		if (Integer.parseInt(properties.getProperty(USERMODE)) > MAX_USERMODE)
			return false;
		if (Integer.parseInt(properties.getProperty(USERMODE)) < 0)
			return false;

		if (!(properties.containsKey(RATIO)))
			return false;
		if (Integer.parseInt(properties.getProperty(RATIO)) > MAX_RATIO)
			return false;
		if (Integer.parseInt(properties.getProperty(RATIO)) < 0)
			return false;
		return true;
	}
	
	// returns new outputstream
	private FileOutputStream out() throws Exception {
		return new FileOutputStream(FILENAME);
	}
}
