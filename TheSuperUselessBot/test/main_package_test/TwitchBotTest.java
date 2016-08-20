package main_package_test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main_package.Commande;
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

	@Test
	public void testOnMessageWhenCommandNotExisting() {
		
		t.onMessage("#thronghar", "userTest", "", "", "command inconnue");
		assertTrue(t.getBufferMessage().equals(""));
	}
	
	@Test
	public void testOnMessageWhenCommandExistAndFromJSON() {
		for(Commande tempo : t.getListCommandes()){
			
			t.onMessage("#thronghar", "userTest", "", "", tempo.getNomCommande());
			System.out.println("Commande : "+tempo.getNomCommande()+" // Buffer : "+t.getBufferMessage() );
			// Si commande désactivé OU modonly et user non mod
			if(!tempo.isActivated() || (tempo.isModOnly() && !t.isMod("userTest"))) 
				assertTrue(t.getBufferMessage().equals(""));
			else {
				// Replace lorsque une commande a le @sender pour remplacer par le pseudo dans la fonction checkCommand
				if(tempo.getResultatCommande().contains("@sender"))
					assertTrue(t.getBufferMessage().equals( tempo.getResultatCommande().replace("@sender", "userTest")));
				else
					assertTrue(t.getBufferMessage().equals( tempo.getResultatCommande()));
			}
		}
	}
	
	@After
	public void disconnectBot() {
		t.disconnect();
	}
	
}
