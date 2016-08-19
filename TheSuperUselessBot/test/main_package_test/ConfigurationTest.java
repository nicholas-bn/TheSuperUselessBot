package main_package_test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main_package.Commande;
import main_package.Configuration;

public class ConfigurationTest {
	
	Configuration c;
	ArrayList<Commande> returnCommand;
	
	@Before
	public void initialize() {
			c = new Configuration();
			returnCommand = new ArrayList<Commande>();
	}

	// CONFIG
	
	@Test
	public void testSetupConfigFichierExistePas() {

	    try {
			c.setupConfig("ressources_test/fichierQuiNExistePas.txt");
			fail("Le test aurait dû lancer une exception FileNotFound");
		} catch (FileNotFoundException e) {
			assert(e.getMessage().contains("Le fichier \"config_bot.ini\" n'a pas été trouvé."));
		}
	}
	
	@Test
	public void testSetupConfigFichierExiste() {

	    try {
			c.setupConfig("ressources_test/ini_test_file.ini");
		} catch (FileNotFoundException e) {
			fail("Le test aurait du trouver le fichier");
		}
	}
	
	@Test
	public void testSetupConfigFichierExisteEt() {

	    try {
			c.setupConfig("ressources_test/ini_test_file.ini");
		} catch (FileNotFoundException e) {
			fail("Le test aurait du trouver le fichier");
		}
	    assertTrue(c.getBotName().equals("testBotName"));
	    assertTrue(c.getChannelToJoin().equals("testChannelName"));
	    assertTrue(c.getoAuth().equals("oauth:random1337"));
	    assertTrue(c.getPort()==1234);
	    assertTrue(c.getUrl().equals("test.irc.irc.dot.com"));
	}
	
	// COMMANDE
	
	@Test
	public void testSetupCommandeFichierExistePas() {

	    try {
			c.setupCommands("ressources_test/fichierQuiNExistePas.txt");
			fail("Le test aurait dû lancer une exception FileNotFound");
		} catch (FileNotFoundException e) {
			assert(e.getMessage().contains("Le fichier \"command_list.json\" n'a pas été trouvé."));
		}
	}
	
	@Test
	public void testSetupCommandeFichierExiste() {

	    try {
			c.setupCommands("ressources_test/json_test_file.json");
		} catch (FileNotFoundException e) {
			fail("Le test aurait du trouver le fichier");
		}
	}
	
	@Test
	public void testSetupCommandeFichierExisteEt() {

	    try {
			returnCommand = c.setupCommands("ressources_test/json_test_file.json");
		} catch (FileNotFoundException e) {
			fail("Le test aurait du trouver le fichier");
		}
	    
	    assertTrue(returnCommand.get(0).getNomCommande().equals("frost c un peu un noob"));
	    assertTrue(returnCommand.get(0).getResultatCommande().equals("J'avoue @sender Kappa"));
	    assertTrue(returnCommand.get(0).isActivated()==true);
	    assertTrue(returnCommand.get(0).isRegExp()==false);
	    assertTrue(returnCommand.get(0).isModOnly()==false);
	    
	    assertTrue(returnCommand.get(1).getNomCommande().equals("!Dynoxyz"));
	    assertTrue(returnCommand.get(1).getResultatCommande().equals("Dynoxyz ? Celui qui arrive pas a defuse les !plant ? Kappa "));
	    assertTrue(returnCommand.get(1).isActivated()==true);
	    assertTrue(returnCommand.get(1).isRegExp()==false);
	    assertTrue(returnCommand.get(1).isModOnly()==false);
	    
	    assertTrue(returnCommand.get(2).getNomCommande().equals(".*ananas.*"));
	    assertTrue(returnCommand.get(2).getResultatCommande().equals("NotLikeThis Comment oses-tu parler des Ananas @sender ?! NotLikeThis"));
	    assertTrue(returnCommand.get(2).isActivated()==true);
	    assertTrue(returnCommand.get(2).isRegExp()==true);
	    assertTrue(returnCommand.get(2).isModOnly()==false);
	    
	    assertTrue(returnCommand.get(3).getNomCommande().equals("!testMode"));
	    assertTrue(returnCommand.get(3).getResultatCommande().equals("Tu es un mod GG"));
	    assertTrue(returnCommand.get(3).isActivated()==true);
	    assertTrue(returnCommand.get(3).isRegExp()==false);
	    assertTrue(returnCommand.get(3).isModOnly()==true);
	    
	}

}
