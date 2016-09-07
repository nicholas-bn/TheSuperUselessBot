package GUI_package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import main_package.Commande;

public class Commande_JPanel extends JPanel {
	
	private JTextArea nomCommande;
	private JTextArea resultatCommande;
	private JCheckBox activated;
	private JCheckBox modOnly;
	private JCheckBox isRegExp;
	private Commande commande;
	
	

	public Commande_JPanel(Commande c) {
		super();
		nomCommande = new JTextArea(c.getNomCommande(), 1, 10);
		resultatCommande = new JTextArea(c.getResultatCommande(), 1, 50);
		activated = new JCheckBox("Actif", c.isActivated());
		modOnly = new JCheckBox("modOnly", c.isModOnly());
		isRegExp = new JCheckBox("isRegExp", c.isRegExp());
		commande = c;
	    //this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		this.addListeners();
		
		this.add(activated);
		this.add(nomCommande);
		this.add(resultatCommande);
		this.add(modOnly);
		this.add(isRegExp);
		
	}
	
	public void addListeners(){
		//ACTIVATED
		activated.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					commande.setActivated(true);
				}else if (e.getStateChange()==ItemEvent.DESELECTED){
					commande.setActivated(false);
				}
				System.out.println(Commande_JPanel.this.getCommande().toString());
			}
		});
		
		//MODONLY
		modOnly.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					commande.setModOnly(true);
				}else if (e.getStateChange()==ItemEvent.DESELECTED){
					commande.setModOnly(false);
				}
				System.out.println(Commande_JPanel.this.getCommande().toString());
			}
		});
		
		//REGEXP
		isRegExp.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					commande.setRegExp(true);
				}else if (e.getStateChange()==ItemEvent.DESELECTED){
					commande.setRegExp(false);
				}
				System.out.println(Commande_JPanel.this.getCommande().toString());
			}
		});
		
		// NOM COMMANDE
		DocumentListener documentListenerNom = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("removeUpdate nomCommande");
				textChanged(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("insertUpdate nomCommande");
				textChanged(e);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("changedUpdate nomCommande");
				
			}
			
			private void textChanged(DocumentEvent e) {
				commande.setNomCommande(nomCommande.getText());
				System.out.println(Commande_JPanel.this.getCommande().toString());				
			}
			
		};
		nomCommande.getDocument().addDocumentListener(documentListenerNom);
		
		// RESULTAT COMMANDE
		DocumentListener documentListenerResultat = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("removeUpdate resultatCommande");
				textChanged(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("insertUpdate resultatCommande");
				textChanged(e);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println("changedUpdate resultatCommande");
				
			}
			
			private void textChanged(DocumentEvent e) {
				commande.setResultatCommande(resultatCommande.getText());
				System.out.println(Commande_JPanel.this.getCommande().toString());				
			}
			
		};
		resultatCommande.getDocument().addDocumentListener(documentListenerResultat);
	}



	public JTextArea getNomCommande() {
		return nomCommande;
	}



	public void setNomCommande(JTextArea nomCommande) {
		this.nomCommande = nomCommande;
	}



	public JTextArea getResultatCommande() {
		return resultatCommande;
	}



	public void setResultatCommande(JTextArea resultatCommande) {
		this.resultatCommande = resultatCommande;
	}



	public JCheckBox getActivated() {
		return activated;
	}



	public void setActivated(JCheckBox activated) {
		this.activated = activated;
	}



	public JCheckBox getModOnly() {
		return modOnly;
	}



	public void setModOnly(JCheckBox modOnly) {
		this.modOnly = modOnly;
	}



	public JCheckBox getIsRegExp() {
		return isRegExp;
	}



	public void setIsRegExp(JCheckBox isRegExp) {
		this.isRegExp = isRegExp;
	}



	public Commande getCommande() {
		return commande;
	}



	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	
	
}
