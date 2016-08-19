package main_package_test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main_package.TwitchBot;

public class TwitchBotTest {
	
	ArrayList<String> storeCommande;
	TwitchBot t;
	
	@Before
	public void initialize() {
		storeCommande = new ArrayList<String>();
		t = new TwitchBot();
	}

	@Test
	public void testGetListMods() {
		String storeMessage = "The moderators of this room are: mod1, mod2, mod3";
		storeCommande = t.getListMods(storeMessage);
		assertTrue(storeCommande.get(0).equals("mod1"));
		assertTrue(storeCommande.get(1).equals(" mod2")); // L'espace est toujours là aprés le split
		assertTrue(storeCommande.get(2).equals(" mod3")); // Mais il est évité lors de isMod	
	}

	@Test
	public void testIsModWhenUserIsAMod() {
		ArrayList<String> tempo = new ArrayList<String>();
		tempo.add("user0");
		tempo.add(" user1 ");
		tempo.add(" user2");
		
		t.setListModo(tempo);
		assertTrue(t.isMod("user1"));
	}

	@Test
	public void testIsModWhenUserIsNotAMod() {

		ArrayList<String> tempo = new ArrayList<String>();
		tempo.add("user0");
		tempo.add(" user1 ");
		tempo.add(" user2");
		
		t.setListModo(tempo);
		assertFalse(t.isMod("user3"));
	}
	
	@After
	public void disconnectBot() {
		t.disconnect();
	}
	
}
