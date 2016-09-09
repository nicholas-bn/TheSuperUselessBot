package GUI_package;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import main_package.Commande;
import main_package.Configuration;
import main_package.TwitchBot;

public class WindowLauncher  extends JFrame { 
	
	TwitchBot tb;
	Configuration config;
	ArrayList<Commande_JPanel> listPanelCommande;
	JPanel parent;

	public WindowLauncher() {
		super("TheSuperUselessBot");
		listPanelCommande = new ArrayList<Commande_JPanel>();
		
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

		
		parent = new JPanel();
	    parent.setLayout(new BoxLayout(parent,BoxLayout.PAGE_AXIS));
	    
	    
	    // Permet d'avoir une list des JPanel pour pouvoir les remttre en list de Commande à donner au bot
	    for(Commande c : config.getListCommandes()){
	    	listPanelCommande.add(new Commande_JPanel(c, this));
	    }
	    for(Commande_JPanel cjp : listPanelCommande){
	    	parent.add(cjp);
	    }
	    
	    
	   	// Ce JPanel permet d'éviter l'espacement entre les Commande_JPanel 
	   	JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(parent, BorderLayout.PAGE_START);
		
		
		JButton ajoutCommande = new JButton("Ajouter une commande", new ImageIcon("ressources/images/Bouton_Ajout.png"));
		ajoutCommande.setToolTipText("Permet d'ajouter une commande");
		ajoutCommande.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Commande c = new Commande("nomCommande", "resultatCommande", false, false, false);
				Commande_JPanel cjp  = new Commande_JPanel(c, WindowLauncher.this);
				WindowLauncher.this.listPanelCommande.add(cjp);
				WindowLauncher.this.parent.add(cjp);
				//WindowLauncher.this.updateListCommand();		
				WindowLauncher.this.invalidate();
				WindowLauncher.this.validate();
				WindowLauncher.this.repaint();
			}
		});
		mainPanel.add(ajoutCommande, BorderLayout.PAGE_END);
		
		
		this.add(mainPanel);  
			  
		
		//this.setSize(960,1080);
		this.setSize(1060,1080);
		this.setLocation(1920, 0);
		this.setVisible(true);
		

		// UTILISER CA POUR SWAP ENTRE COMMANDE OUTILS MENUS
		//mainPanel.setVisible(false);

		System.out.println("THREAD window: "+Thread.currentThread().getId());
		tb = new TwitchBot(config);
		Thread t = new Thread(tb);
		t.start();
		

		
	}
	
	public void updateListCommand(){
		ArrayList<Commande> tempo = new ArrayList<Commande>();
		for (Commande_JPanel c : listPanelCommande){
			tempo.add(c.getCommande());
		}
		tb.config.setListCommandes(tempo);
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
