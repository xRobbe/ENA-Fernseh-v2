package ena;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JPanel;

/**
 * Diese Klasse kapselt und simuliert die Audio- und Video-Elektronik des
 * Fernsehers. Steuerbefehle werden simuliert durch Ausgabe auf die Konsole.
 * 
 * @author Bernhard Kreling
 * @version 1.0
 * @version 1.1 public now()
 */
public class TvElectronics {
	private static final String CHANNELSCAN = "Kanalscan.csv";

	protected JPanel mainDisplay;
	protected JPanel pipDisplay;
	private Screen screen;
	private RemoteControl remote;
	private boolean isRecording; // der TimeShift-Recorder nimmt momentan auf
	private long recordingStartTime; // zu diesem Zeitpunkt hat die
										// TimeShift-Aufnahme begonnen (in
										// Sekunden seit 1.1.1970)

	/**
	 * Der Konstruktur �bernimmt Referenzen auf die beiden JPanel-Objekte, die
	 * die Displays repr�sentieren.
	 * 
	 * @param mainDisplay
	 *            dieses Panel repr�sentiert das Haupt-Display
	 * @param pipDisplay
	 *            dieses Panel repr�sentiert das PictureInPicture-Display
	 */
	TvElectronics(Screen screen, RemoteControl remote) {
		this.remote = remote;
		this.screen = screen;
		this.mainDisplay = screen.getScreen();
		this.pipDisplay = screen.getPiPScreen();
		this.isRecording = false;
		this.recordingStartTime = 0;
	}

	/**
	 * Liefert den aktuellen Zeitpunkt.
	 * 
	 * @return die aktuelle Zeit in Sekunden seit 1.1.1970
	 */
	public long now() {
		return Calendar.getInstance().getTimeInMillis() / 1000;
	}

	/**
	 * F�hrt den Kanalscan aus und liefert die verf�gbaren Kan�le.
	 * 
	 * @return die Daten aus Kanalscan.csv
	 */
	public ArrayList<Channel> scanChannels() {
		ArrayList<Channel> channels = new ArrayList<Channel>();
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(CHANNELSCAN));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String splitLine[] = line.split(";");
				channels.add(new Channel(Integer.parseInt(splitLine[0]), splitLine[1], Integer.parseInt(splitLine[2]), splitLine[3], splitLine[4], null, 0));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("All channels scanned");
		return channels;
	}

	/**
	 * W�hlt einen Kanal f�r die Wiedergabe aus.
	 * 
	 * @param channel
	 *            Kanalnummer als Zahl im Bereich 1..99 gefolgt von einem
	 *            Buchstaben a..d (vgl. Kanalscan.csv)
	 * @param forPiP
	 *            true: Wiedergabe im PictureInPicture-Display; false:
	 *            Wiedergabe im Haupt-Display
	 * @throws Exception
	 *             wenn der Wert von "channel" nicht g�ltig ist
	 */
	public void setChannel(String channel, boolean forPiP, String picturePath) throws Exception {
		String errmsg = "Illegal format for channel: " + channel;
		int channelNumber;
		try {
			channelNumber = Integer.parseInt(channel.substring(0, channel.length() - 1));
		} catch (NumberFormatException n) {
			throw new Exception(errmsg);
		}
		String subChannel = channel.substring(channel.length() - 1, channel.length());
		if (channelNumber < 1 || channelNumber > 99 || new String("abcd").indexOf(subChannel) < 0)
			throw new Exception(errmsg);
		System.out.println((forPiP ? "PiP" : "Main") + " channel = " + channel);

		screen.setChannelPicture(picturePath, forPiP);
		remote.getConfig().setProgramm(remote.getSelectedChannel());

	}

	/**
	 * Stellt die Lautst�rke des Fernsehers ein.
	 * 
	 * @param volume
	 *            Einstellwert f�r die Lautst�rke im Bereich 0..100 (0 = aus,
	 *            100 = volle Lautst�rke)
	 * @throws Exception
	 *             wenn der Wert von "volume" au�erhalb des zul�ssigen Bereichs
	 *             ist
	 */
	public void setVolume(int volume) throws Exception {
		if (volume < 0 || volume > 100)
			throw new Exception("Volume out of range 0..100: " + volume);
		System.out.println("Volume = " + volume);
	}

	/**
	 * Vergr��ert bei Aktivierung das aktuelle Bild des Main-Display auf 133%
	 * und stellt es zentriert dar, d.h. die R�nder des vergr��erten Bildes
	 * werden abgeschnitten. Dadurch verschwinden die schwarzen Balken rechts
	 * und links bei 4:3 Sendungen, bzw. die schwarzen Balken oben und unten bei
	 * Cinemascope Filmen.
	 * 
	 * @param on
	 *            true: Vergr��erung auf 133%; false: Normalgr��e 100%
	 */
	public void setZoom(boolean on) {
		System.out.println("Zoom = " + (on ? "133%" : "100%"));
		if (on)
			screen.setChannelPicture(remote.getChannel().getChannelList().get(remote.getSelectedChannel()).getChannelPicturePath(), false, on);
		else
			screen.setChannelPicture(remote.getChannel().getChannelList().get(remote.getSelectedChannel()).getChannelPicturePath(), false, on);
	}

	/**
	 * Aktiviert bzw. deaktiviert die PictureInPicture-Darstellung.
	 * 
	 * @param show
	 *            true: macht das kleine Bild sichtbar; false: macht das kleine
	 *            Bild unsichtbar
	 */
	public void setPictureInPicture(boolean show) {
		System.out.println("PiP = " + (show ? "visible" : "hidden"));
		pipDisplay.setVisible(show);
	}

	/**
	 * Startet die Aufnahme auf den TimeShift-Recorder bzw. beendet sie wieder.
	 * Das Beenden der Aufnahme beendet gleichzeitig eine eventuell laufende
	 * Wiedergabe.
	 * 
	 * @param start
	 *            true: Start; false: Stopp
	 * @throws Exception
	 *             wenn der Wert von "start" nicht zum aktuellen Zustand passt
	 */
	public void recordTimeShift(boolean start) throws Exception {
		if (this.isRecording == start)
			throw new Exception("TimeShift is already " + (this.isRecording ? "recording" : "stopped"));
		if (!start)
			this.playTimeShift(false, 0);
		this.isRecording = start;
		this.recordingStartTime = now();
		System.out.println((start ? "Start" : "Stop") + " timeshift recording");
	}

	/**
	 * Startet die Wiedergabe vom TimeShift-Recorder bzw. beendet sie wieder.
	 * 
	 * @param start
	 *            true: Start; false: Stopp
	 * @param offset
	 *            der Zeitversatz gegen�ber der Aufnahme in Sekunden (nur
	 *            relevant bei Start)
	 * @throws Exception
	 *             wenn keine Aufzeichnung l�uft oder noch nicht genug gepuffert
	 *             ist
	 */
	public void playTimeShift(boolean start, int offset) throws Exception {
		if (start && !this.isRecording)
			throw new Exception("TimeShift is not recording");
		if (start &&
				this.recordingStartTime + offset > now())
			throw new Exception("TimeShift has not yet buffered " + offset + " seconds");
		System.out.println((start ? "Start" : "Stop") + " timeshift playing" + (start ? " (offset " + offset + " seconds)" : ""));
	}
}
