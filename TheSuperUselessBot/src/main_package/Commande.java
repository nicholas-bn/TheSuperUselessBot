package main_package;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commande {

	private String nomCommande;
	private String resultatCommande;
	private boolean activated;
	private boolean isRegExp;
	
	public Commande(String nomCommande, String resultatCommande, boolean activated, boolean isRegExp) {
		this.setNomCommande(nomCommande);
		this.setResultatCommande(resultatCommande);
		this.setActivated(activated);
		this.setRegExp(isRegExp);
	}
	
	public String checkCommand(String channel, String sender, String login, String hostname, String message){
		
		Pattern pattern;
		Matcher m;
		
		if(this.isActivated()) {
			if(!this.isRegExp()){ // si non REGEXP
				if(this.getNomCommande().equalsIgnoreCase(message)){
					return this.getResultatCommande().replaceAll("@sender", sender);
				}
			} else { //Si REGEXP
				pattern = Pattern.compile(this.getNomCommande() ,Pattern.CASE_INSENSITIVE);
				m = pattern.matcher(message);
				if(m.matches()){
					return this.getResultatCommande().replaceAll("@sender", sender);
				}
			}
		}
		return "";
	}
	

	public String getNomCommande() {
		return nomCommande;
	}

	public void setNomCommande(String nomCommande) {
		this.nomCommande = nomCommande;
	}

	public String getResultatCommande() {
		return resultatCommande;
	}

	public void setResultatCommande(String resultatCommande) {
		this.resultatCommande = resultatCommande;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isRegExp() {
		return isRegExp;
	}

	public void setRegExp(boolean isRegExp) {
		this.isRegExp = isRegExp;
	}

	
}
