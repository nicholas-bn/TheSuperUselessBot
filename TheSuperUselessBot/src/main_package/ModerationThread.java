package main_package;

import java.util.ArrayList;

public class ModerationThread implements Runnable {

	private String messageSender;
	private ArrayList<String> dictionnaire;
	
	public ModerationThread(String messageSender, ArrayList<String> dictionnaire) {
		super();
		this.messageSender 	= messageSender;
		this.dictionnaire 	= dictionnaire;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public String getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}
		
}
