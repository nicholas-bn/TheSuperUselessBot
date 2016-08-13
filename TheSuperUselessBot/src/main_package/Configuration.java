package main_package;

public class Configuration {

	public String 	channelToJoin;
	public String 	oAuth;
	public String 	botName;
	public int 		port;
	public String 	url;


	public Configuration(){
		this.setup();
	}
	
	public String getoAuth() {
		return oAuth;
	}

	public void setoAuth(String oAuth) {
		this.oAuth = oAuth;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getChannelToJoin() {
		return channelToJoin;
	}

	public void setChannelToJoin(String channelToJoin) {
		this.channelToJoin = channelToJoin;
	}

	public String getBotName() {
		return botName;
	}

	public void setBotName(String botName) {
		this.botName = botName;
	}

	public void setup() {
		
		// TODO : allez chercher ces infos dans un fichier externe
		this.setBotName("TheSuperUselessBot");
		this.setChannelToJoin("thronghar");
		this.setoAuth("oauth:i1x9cplwbxbf0gay6hwhecajw2qei6");
		this.setPort(6667);
		this.setUrl("irc.twitch.tv");
	}
	
}
