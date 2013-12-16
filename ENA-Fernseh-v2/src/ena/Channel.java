package ena;

public class Channel {
	private String channel;
	private String channelName;
	private String channelPicturePath;
	private int channelNumber;
	private int channelFrequency;
	private int channelQuality;
	private String channelProvider;

	Channel(int channelFrequency, String channel, int channelQuality, String channelName, String channelProvider, String channelPicturePath, int channelNumber) {
		this.channel = channel;
		this.channelName = channelName;
		this.channelPicturePath = channelPicturePath;
		this.channelFrequency = channelFrequency;
		this.channelQuality = channelQuality;
		this.channelProvider = channelProvider;
		this.channelNumber = channelNumber;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public int getChannelNumber() {
		return channelNumber;
	}

	public void setChannel(int channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelPicturePath() {
		return channelPicturePath;
	}

	public void setChannelPicturePath(String channelPicturePath) {
		this.channelPicturePath = channelPicturePath;
	}

	public int getChannelFrequency() {
		return channelFrequency;
	}

	public void setChannelFrequency(int channelFrequency) {
		this.channelFrequency = channelFrequency;
	}

	public int getChannelQuality() {
		return channelQuality;
	}

	public void setChannelQuality(int channelQuality) {
		this.channelQuality = channelQuality;
	}

	public String getChannelProvider() {
		return channelProvider;
	}

	public void setChannelProvider(String channelProvider) {
		this.channelProvider = channelProvider;
	}
	
	public String printCSV() {
		return String.valueOf(channelFrequency) + ";" + channelNumber + ";" +  String.valueOf(channelQuality) + ";" +  channelName + ";" +  channelProvider;
	}
}
