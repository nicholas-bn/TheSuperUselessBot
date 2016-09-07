package GUI_package;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import main_package.Commande;
import main_package.Configuration;
import main_package.TwitchBot;

public class WindowLauncher  extends JFrame { 
	
	TwitchBot tb;
	Configuration config;

	public WindowLauncher() {
		super("TheSuperUselessBot");
		
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				if(tb!=null) // Si il n'a pas été instancié
					tb.deconnexion();
				System.exit(0);
			}
		};
		
		this.addWindowListener(l);
		
		remplirMenu();
		loadConfig();

		JPanel parent = new JPanel();
	    parent.setLayout(new BoxLayout(parent,BoxLayout.PAGE_AXIS));
	    
	    for(Commande c : config.getListCommandes()){
	    	parent.add(new Commande_JPanel(c));
	    }
	   	
	   	// Ce JPanel permet d'éviter l'espacement entre les Commande_JPanel 
	   	JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(parent, BorderLayout.PAGE_START);
		
		this.add(mainPanel);  
			   	
		this.setSize(960,1080);
		this.setLocation(1920, 0);
		this.setVisible(true);
		

		

//		System.out.println("THREAD window: "+Thread.currentThread().getId());
//		tb = new TwitchBot(config);
//		Thread t = new Thread(tb);
//		t.start();
	}
	
	public void loadConfig(){
		this.config = new Configuration();
		
		try {
			this.config.setupConfig("ressources/config_bot.ini");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			this.config.setupCommands("ressources/command_list.json");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void remplirMenu(){
		JMenuBar menuBar = new JMenuBar();
		JMenu commandesMenu = new JMenu("Commandes");
		JMenu jeuxMenu = new JMenu("Jeux");
		JMenu outilsMenu = new JMenu("Outils");
		
		menuBar.add(commandesMenu);
		menuBar.add(jeuxMenu);
		menuBar.add(outilsMenu);
		this.setJMenuBar(menuBar);
	}
	
	public static void main(String [] args){
		WindowLauncher frame = new WindowLauncher();
	} 

}
