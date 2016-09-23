package main_package;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AntiSpamThread implements Runnable {

	// La clef sera le pseudo unique du joueur
	// et l'arraylist contiendra les heures des derniers messages
	private HashMap<String, ArrayList<Date>> informationsSpam;

	public AntiSpamThread() {
		informationsSpam = new HashMap<String, ArrayList<Date>>();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
