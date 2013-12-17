package ena;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class PersistentChannel {
	private static final String FILENAME = "channels";
	private ArrayList<Channel> channelList = new ArrayList<Channel>();
	private TvElectronics electronics;

	public PersistentChannel(TvElectronics electronics) {
		this.electronics = electronics;
		if (!(new File(FILENAME).exists()) || new File(FILENAME).length() == 0)
			scanChannel();
		else
			readChannel();
	}

	public void scanChannel() {
		ArrayList<Channel> list = electronics.scanChannels();
		writeChannel(list);
		readChannel();
	}

	public void readChannel() {
		channelList.clear();
		String line;
		int i = 1;
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILENAME));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String splitLine[] = line.split(";");
				channelList.add(new Channel(Integer.parseInt(splitLine[0]), splitLine[1], Integer.parseInt(splitLine[2]), splitLine[3], splitLine[4], getImage(splitLine[3]), i));
				++i;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeChannel(ArrayList<Channel> list) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME));
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i).printCSV());
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Channel> getChannelList() {
		return channelList;
	}

	private String getImage(String channel) {
		if (new File("src/screens/" + channel.toLowerCase()).exists())
			return "src/screens/" + channel.toLowerCase();
		else
			return "src/screens/testbild";
	}
}
