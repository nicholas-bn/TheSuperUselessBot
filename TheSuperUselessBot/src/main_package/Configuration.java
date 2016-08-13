package main_package;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

public class Configuration {

	public String 	channelToJoin;
	public String 	oAuth;
	public String 	botName;
	public int 		port;
	public String 	url;


	public Configuration(){
		this.setupConfig();
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

	public void setupConfig(){
		
		File f = new File("d:\\TRAVAIL\\Perso\\Jar_BOT\\config_bot.ini");
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("EXISTTTTTEEEEEEEE");
		}
		else {
			System.out.println("EXISTTTTTEEEEEEEE PAAAAAAAAAAAAS");
		}
		
		Wini ini;
		try {
			ini = new Wini(f);
			System.out.println();
			this.setBotName(ini.get("config", "botName"));
			this.setChannelToJoin(ini.get("config", "channel"));
			this.setoAuth(ini.get("config", "oauth"));
			this.setPort(ini.get("config", "port", int.class));
			this.setUrl(ini.get("config", "url"));
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("InvalidFileFormatException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException");
			e.printStackTrace();
		}
	}
	
}
