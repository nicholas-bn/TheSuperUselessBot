package main_package;

public class Launcher {
	
	 public static final String OAUTH = "oauth:i1x9cplwbxbf0gay6hwhecajw2qei6";
	
	 public static void main (String [] args) throws Exception {
		 
		 String channelToJoin ="thronghar";
		 TwitchBot tb = new TwitchBot(channelToJoin);
		 tb.setVerbose(true);
		 tb.connect("irc.twitch.tv", 6667, OAUTH);
		 tb.joinChannel("#"+channelToJoin);
		 tb.sendAction("#"+channelToJoin, " vient de se connecter !");
	 }
	 
}
