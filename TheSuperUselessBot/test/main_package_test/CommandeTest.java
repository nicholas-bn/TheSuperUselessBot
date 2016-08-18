package main_package_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import main_package.Commande;

public class CommandeTest {

	ArrayList<Commande> listComm;
	
	@Before
	public void initialize() {
			listComm = new ArrayList<Commande>();
			listComm.add(new Commande("!command1", "resultCommand1", true, false, false));
			listComm.add(new Commande("!command2", "resultCommand2", false, false, false));
			listComm.add(new Commande("!command3.*", "resultCommand3", true, true, false));
			listComm.add(new Commande("!command4", "resultCommand4 @sender", true, false, false));
			listComm.add(new Commande("!command5.*", "resultCommand5 @sender", true, true, false));
	}
	
	@Test
	public void testCheckCommandAvailableAndNotRegEx() {
		assertTrue(listComm.get(0).checkCommand("", "", "", "", "!command1", false).equals("resultCommand1"));
	}
	
	@Test
	public void testCheckCommandNotAvailableAndNotRegEx() {
		assertFalse(listComm.get(1).checkCommand("", "", "", "", "!command2", false).equals("resultCommand2"));
		
	}
	
	@Test
	public void testCheckCommandAvailableAndRegEx() {
		assertTrue(listComm.get(2).checkCommand("", "", "", "", "!command3 TEST ET TESTTTTT", false).equals("resultCommand3"));	
	}
	
	@Test
	public void testCheckCommandAvailableAndNotRegExWithSender() {
		assertTrue(listComm.get(3).checkCommand("", "Utilisateur", "", "", "!command4", false).equals("resultCommand4 Utilisateur"));
	}

	@Test
	public void testCheckCommandAvailableAndRegExWithSender() {
		assertTrue(listComm.get(4).checkCommand("", "Utilisateur", "", "", "!command5  TEST ET TESTTTTT", false).equals("resultCommand5 Utilisateur"));
	}
}
