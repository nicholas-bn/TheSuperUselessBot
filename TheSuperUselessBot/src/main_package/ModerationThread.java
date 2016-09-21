package main_package;

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
			p = Pattern.compile(".*"+element+".*", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
			m = p.matcher(this.getMessage());
			if(m.matches()){
				this.getTb().sendMessage(this.getChannel(), "@"+this.getSender()+" TO pour : \""+element+"\"");
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
