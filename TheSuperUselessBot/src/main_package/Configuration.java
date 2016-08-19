package main_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Configuration {

	public String 	channelToJoin;
	public String 	oAuth;
	public String 	botName;
	public int 		port;
	public String 	url;

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

	public void setupConfig(String pathToINI) throws FileNotFoundException{
		
		File f = new File(pathToINI);
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("Le fichier \"config_bot.ini\" a été trouvé.");
		}
		else {
			throw new FileNotFoundException("Le fichier \"config_bot.ini\" n'a pas été trouvé.");
		}
		
		Wini ini;
		try {
			ini = new Wini(f);
			this.setBotName(ini.get("config", "botName"));
			this.setChannelToJoin(ini.get("config", "channel"));
			this.setoAuth(ini.get("config", "oauth"));
			this.setPort(ini.get("config", "port", int.class));
			this.setUrl(ini.get("config", "url"));
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			System.err.println("InvalidFileFormatException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IOException");
			e.printStackTrace();
		}
	}
	
	public ArrayList<Commande> setupCommands(String pathToJSON){
		
		ArrayList<Commande> returnCommand = new ArrayList<Commande>();		
		JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(pathToJSON);
        	System.out.println("Le fichier \"command_list.json\" a été trouvé.");
            JSONObject json = (JSONObject) parser.parse(fileReader);
            JSONArray commands = (JSONArray) json.get("commands");
            
            for(int j=0; j<commands.size();j++){
            	JSONObject tempoCommande = (JSONObject) commands.get(j);
            	
            	String nom = tempoCommande.get("nomCommande").toString();
            	String resultat = tempoCommande.get("resultatCommande").toString();
            	boolean activated = Boolean.parseBoolean(tempoCommande.get("activated").toString());
            	boolean regexp = Boolean.parseBoolean(tempoCommande.get("isRegExp").toString());
            	boolean modonly = Boolean.parseBoolean(tempoCommande.get("isModOnly").toString());
            	
            	Commande c = new Commande(nom, resultat, activated, regexp, modonly);
            	System.out.println("Commande "+j+" ( NomCommande :\""+c.getNomCommande()+"\"; ResultatCommande :\""+c.getResultatCommande()+"\"; isActivated :\""+c.isActivated()+"\"; isRegExp :\""+c.isRegExp()+"\"; isModOnly :\""+c.isModOnly()+"\")");
            	returnCommand.add(c);
            	
            }
            

        } catch (FileNotFoundException fx) {
        	System.err.println("Le fichier \"command_list.json\" n'a pas été trouvé.");
            fx.printStackTrace();
        } catch (IOException eio) {
			// TODO Auto-generated catch block
			eio.printStackTrace();
		}catch (ParseException pe) {
			// TODO Auto-generated catch block
			pe.printStackTrace();
		}
		
		return returnCommand;
	}
	
}
