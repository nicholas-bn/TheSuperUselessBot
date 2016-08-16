package main_package;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitchBot extends PircBot  {
	
	
	
	//////////
	// PARAM
	//////////
	
	public boolean CHIFFRERANDOM = false;
	public boolean checkModo = false;
	public int chiffreCHIFFRERANDOM = 0;
	public String channelToJoin;
	public ArrayList<String> listModo;
	public ArrayList<Commande> listCommandes;

	////////////////
	// CONSTRUCTEUR
	////////////////
	
	public TwitchBot(Configuration config) throws NickAlreadyInUseException, IOException, IrcException {
		this.channelToJoin = new String(config.getChannelToJoin());
		CHIFFRERANDOM = false;
		checkModo = true;
		listModo=new ArrayList<String>();
		listCommandes=config.setupCommands();
		this.setName(config.getBotName());
		this.isConnected();
		this.setVerbose(true);
		this.connect(config.getUrl(), config.getPort(), config.getoAuth());
		this.joinChannel("#"+config.getChannelToJoin());
		this.sendAction("#"+config.getChannelToJoin(), " vient de se connecter !");
	}
	////////////
	// METHODES
	////////////
	@Override
	protected void onConnect() {
		
		
		this.sendRawLine("CAP REQ :twitch.tv/membership");
		this.sendRawLine("CAP REQ :twitch.tv/commands");
		this.sendMessage("#"+channelToJoin, ".mods"); // Commande pour afficher la liste des modérateur et récupéré par onNotice
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message) {

		// On vérifie dans la liste si le message envoyé correspond ou pas à un message dans le JSON.
		for(Commande c : listCommandes){
			c.checkCommand(this, channel, sender, login, hostname, message);
		}
		
		System.out.println(message);
		

		if (message.equalsIgnoreCase("info")) {
			sendMessage(channel, listModo.toString());
		}
	
		// POLL
		Pattern pattern = Pattern.compile("!poll *//.*//.*//.*" ,Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(message);
		if(m.matches()){
			try {
				if(isMod(sender)){
					requestAPIStrawpoll(channel, sender, login, hostname, message);
				}else{
					sendMessage(channel, "NotLikeThis Vous n'avez pas les droits pour creer un strawpoll NotLikeThis");
				}
					
			} catch (JSONException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// DISCONNECT
		if (message.equalsIgnoreCase("disconnect") && (sender.equalsIgnoreCase(channelToJoin) || sender.equalsIgnoreCase("thronghar"))) {
			sendMessage(channel, "bye bb");
			sendAction("#"+channelToJoin, " vient de se deconnecter ! ");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.disconnect();
			System.exit(0);
		}
		
		// SUICIDE
		if (message.equalsIgnoreCase("!suicide")) {
			sendMessage(channel, "rip " + sender);
			sendMessage(channel, ".timeout " + sender + " 10");
		}

		//CHIFFRE RANDOM debut		
		if (message.equalsIgnoreCase("!random") && CHIFFRERANDOM == false) {
			sendMessage(channel, "C'est l'heure du random les petits fruits ! Choisissez un nombre entre 1 et 10.");
			CHIFFRERANDOM = true;
			Random rn = new Random();
			chiffreCHIFFRERANDOM = rn.nextInt(10 - 0 + 1) + 0;
		}
		
		//CHIFFRE RANDOM fin
		if (NumberUtils.isNumber(message) && CHIFFRERANDOM == true) {
			if (Integer.parseInt(message) == chiffreCHIFFRERANDOM) {
				sendMessage(channel, sender + " a trouve le bon chiffre ! (" + chiffreCHIFFRERANDOM + ")");
				CHIFFRERANDOM=false;
			}
		}
	}
	
	
	// Méthode temporaire créée car on ne peut définir directement sur la méthode onMessage qu'elle peut "throws" des exceptions
	public void requestAPIStrawpoll(String channel, String sender, String login, String hostname, String message) throws JSONException, MalformedURLException, IOException {
		
		// On définit les arguments JSON
		List<String> options = new ArrayList<>();
		List<String> tempo = new ArrayList<>();
		tempo=Arrays.asList((message.split("//")));
		
		
		
		// On ajoute les options à part le !poll et le titre
		int i=0;
		for(String s : tempo){
			if(i>1)
				options.add(s);
			i++;
		}
		
		// On configure le json
		JSONObject json = new JSONObject().put("title", tempo.get(1)).put("options", options);

		// On ouvre la connection avec l'API strawpoll 
        HttpURLConnection con;
        con = (HttpURLConnection) new URL("http://strawpoll.me/api/v2/polls").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        
        // On met dans un buffer
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()))) {
            writer.write(json.toString());
        }
        
        // On vérifie le code de retour
        int code = con.getResponseCode();
        if (code != 201 && code != 200) {
            System.out.println("La création du poll a retourné un code (" + code + ") différent de 200 ou 201");
            return;
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String input;
            while ((input = reader.readLine()) != null) {
                response.append(input).append("\n");
            }
        }
        sendMessage(channel, "www.strawpoll.me/"+new JSONObject(response.toString()).getInt("id"));
	}
	
	public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice){
		
		if(checkModo){
			Pattern pattern = Pattern.compile(".*The moderators of this room are.*" ,Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(notice);
			if(m.matches()){
				this.listModo=getListMods(notice);
				checkModo=false;
			}
		}
		
	}
	
	public ArrayList<String> getListMods(String sample){
		String sampleAfterReplace = sample.replaceFirst(".*The moderators of this room are: ", "");
		return new ArrayList<String>(Arrays.asList(sampleAfterReplace.split(",")));
	}
	
	public boolean isMod (String userToTest){
		for(String s:this.listModo){
			if(s.replaceAll(" ", "").equalsIgnoreCase(userToTest.replaceAll(" ", ""))) // On enleve les espaces restant grace aux replaceAll et on compare en ignorant la casse
				return true;
		}
		return false;
	}
	
}
