package main_package;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


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
			System.out.println("Le fichier \"config_bot.ini\" a été trouvé.");
		}
		else {
			System.out.println("Le fichier \"config_bot.ini\" n'a pas été trouvé.");
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
	
	public ArrayList<Commande> setupCommands(){
	ArrayList<Commande> returnCommand = new ArrayList<Commande>();
		
		JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader("d:\\TRAVAIL\\Perso\\Jar_BOT\\command_list.json");
            JSONObject json = (JSONObject) parser.parse(fileReader);
            JSONArray commands = (JSONArray) json.get("commands");
            
            for(int j=0; j<commands.size();j++){
            	JSONObject tempoCommande = (JSONObject) commands.get(j);
            	
            	String nom = tempoCommande.get("nomCommande").toString();
            	String resultat = tempoCommande.get("resultatCommande").toString();
            	boolean activated = Boolean.parseBoolean(tempoCommande.get("activated").toString());
            	boolean regexp = Boolean.parseBoolean(tempoCommande.get("isRegExp").toString());
            	
//            	if(nom == null){
//            		System.out.println("ERREUR : nomCommande("+j+") == null dans e JSON");
//            	}
//            	if(resultat == null){
//            		System.out.println("ERREUR : nomCommande("+j+") == null dans e JSON");
//            	}
//            	if(activated == null){
//            		System.out.println("ERREUR : nomCommande("+j+") == null dans e JSON");
//            	}
//            	if(regexp == null){
//            		System.out.println("ERREUR : nomCommande("+j+") == null dans e JSON");
//            	}
            	
            	Commande c = new Commande(nom, resultat, activated, regexp);
            	System.out.println(c.getNomCommande()+c.getResultatCommande()+c.isActivated()+c.isRegExp());
            	returnCommand.add(c);
            	
            }
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		return returnCommand;
	}
	
}
