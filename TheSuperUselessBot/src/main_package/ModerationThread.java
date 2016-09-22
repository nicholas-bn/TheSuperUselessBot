package main_package;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModerationThread implements Runnable {

	private String sender;
	private String message;
	private ArrayList<String> dictionnaire;
	private TwitchBot tb;
	private String channel;
	
	public ModerationThread(TwitchBot tb, String channel, String sender, String message, ArrayList<String> dictionnaire) {
		super();
		this.tb = tb;
		this.channel 		= channel;
		this.sender 		= sender;
		this.message 		= message;
		this.dictionnaire 	= dictionnaire;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Pattern p;
		Matcher m;
		
		for(String element : this.getDictionnaire()){
			// Utilisation des Normalizer pour enlever les accents et autres pour pouvoir comparer les deux mots nature
			p = Pattern.compile(".*"+Normalizer.normalize(element, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "")+".*", Pattern.CASE_INSENSITIVE);
			m = p.matcher(Normalizer.normalize(this.getMessage(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
			if(m.matches()){
				// Si c'est inferieur a 2 on remplace tous les caractéres par *
				if (element.length() > 2) {
					// Autrement on prend le premier caractere et le dernier et tous les caractéres centraux étant une lettre (\\w = [a-zA-Z_0-9]) sont remplacés par un *
					element = element.substring(0, 1)+element.substring(1, element.length() - 1).replaceAll("\\w", "*")+element.substring(element.length() - 1, element.length());
				} else {
					element = element.replaceAll("\\w", "*");
				}
				this.getTb().sendMessage(this.getChannel(), "@"+this.getSender()+" TO 60s pour : \""+element+"\"");
				this.getTb().sendMessage(this.getChannel(), ".timeout " + this.getSender() + " 60");
				return;
			}
		}
	}
		
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<String> getDictionnaire() {
		return dictionnaire;
	}

	public void setDictionnaire(ArrayList<String> dictionnaire) {
		this.dictionnaire = dictionnaire;
	}

	public TwitchBot getTb() {
		return tb;
	}

	public void setTb(TwitchBot tb) {
		this.tb = tb;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
