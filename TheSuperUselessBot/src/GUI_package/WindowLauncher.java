package GUI_package;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import main_package.Commande;
import main_package.Configuration;
import main_package.TwitchBot;

public class WindowLauncher extends JFrame {

	TwitchBot tb;
	Configuration config;
	ArrayList<Commande_JPanel> listPanelCommande;
	JPanel parent;
	JPanel commandePanel;
	JPanel testPanel;
	public final String PATH_TO_INI = "ressources/config_bot.ini";
	public final String PATH_TO_JSON = "ressources/command_list.json";

	public WindowLauncher() {
		super("TheSuperUselessBot");
		listPanelCommande = new ArrayList<Commande_JPanel>();

		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				WindowLauncher.this.savePreferences();
				WindowLauncher.this.setVisible(false);
				WindowLauncher.this.saveCommands();
				if (tb != null) // Si il n'a pas été instancié
					tb.deconnexion();
				System.exit(0);
			}
		};

		this.addWindowListener(l);

		setListIcon();
		remplirMenu();
		loadConfig();

		parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));

		// Permet d'avoir une list des JPanel pour pouvoir les remttre en list
		// de Commande à donner au bot
		for (Commande c : config.getListCommandes()) {
			listPanelCommande.add(new Commande_JPanel(c, this));
		}
		for (Commande_JPanel cjp : listPanelCommande) {
			parent.add(cjp);
		}

		// Ce JPanel permet d'éviter l'espacement entre les Commande_JPanel
		commandePanel = new JPanel(new BorderLayout());
		commandePanel.add(parent, BorderLayout.PAGE_START);

		JButton ajoutCommande = new JButton("Ajouter une commande",
				new ImageIcon("ressources/images/Bouton_Ajout.png"));
		ajoutCommande.setToolTipText("Permet d'ajouter une commande");
		ajoutCommande.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Commande c = new Commande("nomCommande", "resultatCommande", false, false, false);
				Commande_JPanel cjp = new Commande_JPanel(c, WindowLauncher.this);
				WindowLauncher.this.listPanelCommande.add(cjp);
				WindowLauncher.this.parent.add(cjp);
				// WindowLauncher.this.updateListCommand();
				WindowLauncher.this.invalidate();
				WindowLauncher.this.validate();
				WindowLauncher.this.repaint();
			}
		});
		commandePanel.add(ajoutCommande, BorderLayout.PAGE_END);

		this.add(commandePanel);

		// testPanel = new JPanel();
		// testPanel.add(new JButton("HUEHUE"));
		// testPanel.setVisible(false);
		// this.add(testPanel);

		// this.setSize(960,1080);
		// this.setSize(1060,1080);
		// this.setLocation(1920, 0);
		this.setSize(config.getSizeFrame().width, config.getSizeFrame().height);
		this.setLocation(config.getPositionFrame().x, config.getPositionFrame().y);
		this.setVisible(true);

		// UTILISER CA POUR SWAP ENTRE COMMANDE OUTILS MENUS
		// commandePanel.setVisible(true);

		System.out.println("THREAD window: " + Thread.currentThread().getId());
		tb = new TwitchBot(config);
		Thread t = new Thread(tb);
		t.start();

	}

	private void setListIcon() {
		// TODO Auto-generated method stub
		
        final List<Image> icons = new ArrayList<Image>();
        BufferedImage bi16;
        BufferedImage bi32;
        
        try {
			bi16 = ImageIO.read(new File("D:\\TRAVAIL\\Perso\\Jar_BOT\\icons_bot\\16_16.png"));
			bi32 = ImageIO.read(new File("D:\\TRAVAIL\\Perso\\Jar_BOT\\icons_bot\\32_32.png"));
			icons.add(bi16);
			icons.add(bi32);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setIconImages(icons);
	}

	@SuppressWarnings("unchecked")
	protected void saveCommands() {
		// TODO Auto-generated method stub

		// Collecte des commandes et transformation en JSON
		JSONObject rootJSON = new JSONObject();
		JSONArray listCommande = new JSONArray();
		for (Commande_JPanel c : this.listPanelCommande) {
			JSONObject commande = new JSONObject();
			commande.put("nomCommande", c.getCommande().getNomCommande());
			commande.put("resultatCommande", c.getCommande().getResultatCommande());
			commande.put("activated", c.getCommande().isActivated() + "");
			commande.put("isRegExp", c.getCommande().isRegExp() + "");
			commande.put("isModOnly", c.getCommande().isModOnly() + "");
			listCommande.add(commande);
		}
		rootJSON.put("commands", listCommande);

		// Ecriture dans le fichier JSON
		FileWriter file;
		try {
			file = new FileWriter(PATH_TO_JSON);
			file.write(rootJSON.toJSONString());
			file.close();
			System.out.println("Copie des commandes dans le JSON :\"" + PATH_TO_JSON + "\"");
			System.out.println(rootJSON.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Le fichier \"" + PATH_TO_JSON
					+ "\" n'a pas été trouvé lors de la réécriture des commandes OU le programme ne peut écrire dedans.");
			e.printStackTrace();
		}
	}

	protected void savePreferences() {
		// TODO Auto-generated method stub
		Wini ini;
		try {
			ini = new Wini(new File(PATH_TO_INI));
			ini.put("preferences", "positionX", (int) this.getLocation().getX());
			ini.put("preferences", "positionY", (int) this.getLocation().getY());
			ini.put("preferences", "sizeX", (int) this.getSize().getWidth());
			ini.put("preferences", "sizeY", (int) this.getSize().getHeight());
			ini.store();
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			System.err.println("InvalidFileFormatException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("IOException");
			e.printStackTrace();
		}
	}

	public void updateListCommand() {
		ArrayList<Commande> tempo = new ArrayList<Commande>();
		for (Commande_JPanel c : listPanelCommande) {
			tempo.add(c.getCommande());
		}
		tb.config.setListCommandes(tempo);
	}

	public void loadConfig() {
		this.config = new Configuration();

		try {
			this.config.setupConfig(PATH_TO_INI);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			this.config.setupCommands(PATH_TO_JSON);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void remplirMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenuItem commandesMenu = new JMenuItem("Commandes");
		JMenuItem jeuxMenu = new JMenuItem("Jeux");
		JMenuItem outilsMenu = new JMenuItem("Outils");

		// commandesMenu.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // TODO Auto-generated method stub
		// System.out.println("TESTTTTTTTTTTTTTTT111111111111");
		// WindowLauncher.this.commandePanel.setVisible(true);
		// WindowLauncher.this.testPanel.setVisible(false);
		// }
		// });
		//
		// jeuxMenu.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // TODO Auto-generated method stub
		// System.out.println("TESTTTTTTTTTTTTTTT22222222222");
		// WindowLauncher.this.commandePanel.setVisible(false);
		// WindowLauncher.this.testPanel.setVisible(true);
		// }
		// });

		menuBar.add(commandesMenu);
		menuBar.add(jeuxMenu);
		menuBar.add(outilsMenu);
		this.setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		WindowLauncher frame = new WindowLauncher();
	}

}
