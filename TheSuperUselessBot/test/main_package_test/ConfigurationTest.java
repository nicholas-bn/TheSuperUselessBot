package main_package_test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import main_package.Configuration;

public class ConfigurationTest {
	
	Configuration c;
	
	@Before
	public void initialize() {
			c = new Configuration();
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
			c.setupCommands("ressources_test/json_test_file.json");
		} catch (FileNotFoundException e) {
			fail("Le test aurait du trouver le fichier");
		}
	    
	    assertTrue(c.getListCommandes().get(0).getNomCommande().equals("frost c un peu un noob"));
	    assertTrue(c.getListCommandes().get(0).getResultatCommande().equals("J'avoue @sender Kappa"));
	    assertTrue(c.getListCommandes().get(0).isActivated()==true);
	    assertTrue(c.getListCommandes().get(0).isRegExp()==false);
	    assertTrue(c.getListCommandes().get(0).isModOnly()==false);
	    
	    assertTrue(c.getListCommandes().get(1).getNomCommande().equals("!Dynoxyz"));
	    assertTrue(c.getListCommandes().get(1).getResultatCommande().equals("Dynoxyz ? Celui qui arrive pas a defuse les !plant ? Kappa "));
	    assertTrue(c.getListCommandes().get(1).isActivated()==true);
	    assertTrue(c.getListCommandes().get(1).isRegExp()==false);
	    assertTrue(c.getListCommandes().get(1).isModOnly()==false);
	    
	    assertTrue(c.getListCommandes().get(2).getNomCommande().equals(".*ananas.*"));
	    assertTrue(c.getListCommandes().get(2).getResultatCommande().equals("NotLikeThis Comment oses-tu parler des Ananas @sender ?! NotLikeThis"));
	    assertTrue(c.getListCommandes().get(2).isActivated()==true);
	    assertTrue(c.getListCommandes().get(2).isRegExp()==true);
	    assertTrue(c.getListCommandes().get(2).isModOnly()==false);
	    
	    assertTrue(c.getListCommandes().get(3).getNomCommande().equals("!testMode"));
	    assertTrue(c.getListCommandes().get(3).getResultatCommande().equals("Tu es un mod GG"));
	    assertTrue(c.getListCommandes().get(3).isActivated()==true);
	    assertTrue(c.getListCommandes().get(3).isRegExp()==false);
	    assertTrue(c.getListCommandes().get(3).isModOnly()==true);
	    
	}

}
