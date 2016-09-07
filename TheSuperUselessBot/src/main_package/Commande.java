package main_package;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commande {

	private String nomCommande;
	private String resultatCommande;
	private boolean activated;
	private boolean isRegExp;
	private boolean isModOnly;
	
	public Commande(String nomCommande, String resultatCommande, boolean activated, boolean isRegExp, boolean isModOnly) {
		this.setNomCommande(nomCommande);
		this.setResultatCommande(resultatCommande);
		this.setActivated(activated);
		this.setRegExp(isRegExp);
		this.setModOnly(isModOnly);
	}
	
	public String checkCommand(String channel, String sender, String login, String hostname, String message, boolean isSenderAMod){
		
		if(this.isModOnly() && !isSenderAMod){
			return "";
		}
		
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
	
	public boolean isModOnly() {
		return isModOnly;
	}

	public void setModOnly(boolean isModOnly) {
		this.isModOnly = isModOnly;
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

	public String toString(){
		return  "NomCommande :\""+this.getNomCommande()+"\"; ResultatCommande :\""+this.getResultatCommande()+"\"; isActivated :\""+this.isActivated()+"\"; isRegExp :\""+this.isRegExp()+"\"; isModOnly :\""+this.isModOnly()+"\"";
	}
	
}
