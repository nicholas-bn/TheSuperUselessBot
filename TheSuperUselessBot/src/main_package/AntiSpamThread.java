package main_package;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AntiSpamThread implements Runnable {

	// La clef sera le pseudo unique du joueur
	// et l'arraylist contiendra les heures des derniers messages
	private HashMap<String, ArrayList<Date>> informationsSpam;
	private TwitchBot tb;

	public AntiSpamThread(TwitchBot tb) {
		informationsSpam = new HashMap<String, ArrayList<Date>>();
		this.setTb(tb);
	}

	@Override
	public void run() {
	}

	public void add(String sender) {
		System.out.println("THREAD ID SPAM: " + Thread.currentThread().getId());

		ArrayList<Date> tempoDate;

		if ((tempoDate = this.getInformationsSpam().get(sender)) == null) {
			tempoDate = new ArrayList<Date>();
			tempoDate.add(new Date());
			this.getInformationsSpam().put(sender, tempoDate);
		} else {
			tempoDate.add(new Date());
			this.getInformationsSpam().put(sender, tempoDate);
			if (this.getInformationsSpam().get(sender).size() >= 5) {
				if (new Date().getTime() - this.getInformationsSpam().get(sender)
						.get(this.getInformationsSpam().get(sender).size() - 4).getTime() < 10000) {
					this.getTb().sendMessage("#" + this.getTb().getChannelToJoin(),
							"@" + sender + " TO pour spam 60s");
					this.getTb().sendMessage("#" + this.getTb().getChannelToJoin(), ".timeout " + sender + " 60");
				}
			}
		}

	}

	public HashMap<String, ArrayList<Date>> getInformationsSpam() {
		return informationsSpam;
	}

	public void setInformationsSpam(HashMap<String, ArrayList<Date>> informationsSpam) {
		this.informationsSpam = informationsSpam;
	}

	public TwitchBot getTb() {
		return tb;
	}

	public void setTb(TwitchBot tb) {
		this.tb = tb;
	}

}
