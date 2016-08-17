package main_package_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.junit.Before;
import org.junit.Test;

import main_package.Commande;
import main_package.Configuration;
import main_package.TwitchBot;

public class CommandeTest {

	ArrayList<Commande> listComm;
	
	@Before
	public void initialize() {
			listComm = new ArrayList<Commande>();
			listComm.add(new Commande("!command1", "resultCommand1", true, false));
			listComm.add(new Commande("!command2", "resultCommand2", false, false));
			listComm.add(new Commande("!command3.*", "resultCommand3", true, true));
			listComm.add(new Commande("!command4", "resultCommand4 @sender", true, false));
			listComm.add(new Commande("!command5.*", "resultCommand5 @sender", true, true));
	}
	
	@Test
	public void testCheckCommandAvailableAndNotRegEx() {
		assertTrue(listComm.get(0).checkCommand("", "", "", "", "!command1").equals("resultCommand1"));
	}
	
	@Test
	public void testCheckCommandNotAvailableAndNotRegEx() {
		assertFalse(listComm.get(1).checkCommand("", "", "", "", "!command2").equals("resultCommand2"));
		
	}
	
	@Test
	public void testCheckCommandAvailableAndRegEx() {
		assertTrue(listComm.get(2).checkCommand("", "", "", "", "!command3 TEST ET TESTTTTT").equals("resultCommand3"));	
	}
	
	@Test
	public void testCheckCommandAvailableAndNotRegExWithSender() {
		assertTrue(listComm.get(3).checkCommand("", "Utilisateur", "", "", "!command4").equals("resultCommand4 Utilisateur"));
	}

	@Test
	public void testCheckCommandAvailableAndRegExWithSender() {
		assertTrue(listComm.get(4).checkCommand("", "Utilisateur", "", "", "!command5  TEST ET TESTTTTT").equals("resultCommand5 Utilisateur"));
	}
}
