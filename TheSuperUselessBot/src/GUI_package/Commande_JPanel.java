package GUI_package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main_package.Commande;

public class Commande_JPanel extends JPanel {
	
	private JTextArea nomCommande;
	private JTextArea resultatCommande;
	private JCheckBox activated;
	private JCheckBox modOnly;
	private JCheckBox isRegExp;
	private JButton supprimer;
	private Commande commande;
	private WindowLauncher anchor;
	
	

	public Commande_JPanel(Commande c, WindowLauncher anchor) {
		super();
		this.setAnchor(anchor);
		nomCommande = new JTextArea(c.getNomCommande(), 1, 10);
		nomCommande.setToolTipText("Commande");
		resultatCommande = new JTextArea(c.getResultatCommande(), 1, 50);
		resultatCommande.setToolTipText("Résultat de la commande");
		activated = new JCheckBox("Actif", c.isActivated());
		activated.setToolTipText("Activer la commande");
		modOnly = new JCheckBox("modOnly", c.isModOnly());
		modOnly.setToolTipText("Commande utilisable uniquement pour les mods");
		isRegExp = new JCheckBox("isRegExp", c.isRegExp());
		isRegExp.setToolTipText("Utilise les expressions régulieres pour la commande");


		supprimer = new JButton(new ImageIcon("ressources/images/Bouton_Fermeture.png"));
		supprimer.setBorder(BorderFactory.createEmptyBorder());
		supprimer.setContentAreaFilled(false);
		supprimer.setToolTipText("Supprimer la commande");
		
		commande = c;
		
		this.addListeners();
		
		this.add(activated);
		this.add(nomCommande);
		this.add(resultatCommande);
		this.add(modOnly);
		this.add(isRegExp);
		this.add(supprimer);
		
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
				anchor.updateListCommand();
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
				anchor.updateListCommand();
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
				anchor.updateListCommand();				
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
				anchor.updateListCommand();				
			}
			
		};
		resultatCommande.getDocument().addDocumentListener(documentListenerResultat);
		
		supprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Suppresion d'un objet \"Commande_JPanel\": "+Commande_JPanel.this.commande.toString());
				anchor.parent.remove(Commande_JPanel.this);
				anchor.listPanelCommande.remove(Commande_JPanel.this);
				anchor.updateListCommand();		
				anchor.invalidate();
				anchor.validate();
				anchor.repaint();
				
			}
		});
		
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

	
	public WindowLauncher getAnchor() {
		return anchor;
	}

	
	public void setAnchor(WindowLauncher anchor) {
		this.anchor = anchor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activated == null) ? 0 : activated.hashCode());
		result = prime * result + ((anchor == null) ? 0 : anchor.hashCode());
		result = prime * result + ((commande == null) ? 0 : commande.hashCode());
		result = prime * result + ((isRegExp == null) ? 0 : isRegExp.hashCode());
		result = prime * result + ((modOnly == null) ? 0 : modOnly.hashCode());
		result = prime * result + ((nomCommande == null) ? 0 : nomCommande.hashCode());
		result = prime * result + ((resultatCommande == null) ? 0 : resultatCommande.hashCode());
		result = prime * result + ((supprimer == null) ? 0 : supprimer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande_JPanel other = (Commande_JPanel) obj;
		if (activated == null) {
			if (other.activated != null)
				return false;
		} else if (!activated.equals(other.activated))
			return false;
		if (anchor == null) {
			if (other.anchor != null)
				return false;
		} else if (!anchor.equals(other.anchor))
			return false;
		if (commande == null) {
			if (other.commande != null)
				return false;
		} else if (!commande.equals(other.commande))
			return false;
		if (isRegExp == null) {
			if (other.isRegExp != null)
				return false;
		} else if (!isRegExp.equals(other.isRegExp))
			return false;
		if (modOnly == null) {
			if (other.modOnly != null)
				return false;
		} else if (!modOnly.equals(other.modOnly))
			return false;
		if (nomCommande == null) {
			if (other.nomCommande != null)
				return false;
		} else if (!nomCommande.equals(other.nomCommande))
			return false;
		if (resultatCommande == null) {
			if (other.resultatCommande != null)
				return false;
		} else if (!resultatCommande.equals(other.resultatCommande))
			return false;
		if (supprimer == null) {
			if (other.supprimer != null)
				return false;
		} else if (!supprimer.equals(other.supprimer))
			return false;
		return true;
	}

	
	
}
