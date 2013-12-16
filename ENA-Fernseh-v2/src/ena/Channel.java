package ena;

public class Channel {
	private String channelNumber;
	private String channelName;
	private String channelPicturePath;
	//TODO
	private int channelFrequency;
	private int channelQuality;
	private String channelProvider;

	Channel(String channelNumber, String channelName, String channelPicturePath) {
		this.setChannelNumber(channelNumber);
		this.setChannelName(channelName);
		this.setChannelPicturePath(channelPicturePath);
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
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
}
