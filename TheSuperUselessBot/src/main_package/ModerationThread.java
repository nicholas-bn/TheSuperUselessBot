package main_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModerationThread implements Runnable {

	private String sender;
	private String messageSender;
	private ArrayList<String> dictionnaire;
	private TwitchBot tb;
	
	public ModerationThread(TwitchBot tb, String sender, String messageSender, ArrayList<String> dictionnaire) {
		super();
		this.tb = tb;
		this.sender 		= sender;
		this.messageSender 	= messageSender;
		this.dictionnaire 	= dictionnaire;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Pattern p;
		Matcher m;
		
		for(String element : this.getDictionnaire()){
			p = Pattern.compile(".*"+element+".*");
			m = p.matcher(this.getMessageSender());
			if(m.matches()){
				System.out.println("gtfo fgt");
				return;
			}
		}
		
	}

	public static ArrayList<String> remplirListeDico(String path){
		ArrayList<String> dico = new ArrayList<String>();
		
		File f = new File(path);
		Scanner source;
		try {
			source = new Scanner(f);
			while(source.hasNextLine()){
				String mot = source.nextLine().replaceAll("^\\s+", "").replaceAll("\\s+$", "");
				if(!mot.startsWith("#") && !mot.equals(""))
					System.out.println("AAA-"+mot+"-AAA");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Problème lors de l'initialisation du scanner // Fichier non trouvé : "+f.toString());
			e.printStackTrace();
		}
		return dico ;
	}
	
	// MAIN
	public static void main(String[] args) {
		remplirListeDico("ressources/liste_mots_moderation.txt");
		ArrayList<String> dictionnaire = new ArrayList<String>();
		for(int i=0; i<200; i++){
			if(i==198)
				dictionnaire.add("GROSMOT");
			else
				dictionnaire.add(Integer.toString(i));
		}
		ModerationThread mt = new ModerationThread(null, "USER", "hola que tal GROSMOT!!!", dictionnaire);
		Thread t = new Thread(mt);
		t.start();
	}
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
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
}
