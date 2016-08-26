package GUI_package;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import main_package.TwitchBot;

public class WindowLauncher  extends JFrame { 
	
	TwitchBot tb;

	public WindowLauncher() {
		super("TheSuperUselessBot");
		
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				tb.deconnexion();
				System.exit(0);
			}
		};
		
		JMenuBar menuBar = new JMenuBar();
		JMenu commandesMenu = new JMenu("Commandes");
		JMenu jeuxMenu = new JMenu("Jeux");
		JMenu outilsMenu = new JMenu("Outils");
		
		commandesMenu.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("HUEHEUHEU");
				
			}
		});
		
		menuBar.add(commandesMenu);
		menuBar.add(jeuxMenu);
		menuBar.add(outilsMenu);
		this.setJMenuBar(menuBar);
		  
		System.out.println("test");
		
		addWindowListener(l);
		
		ImageIcon img = new ImageIcon("tips.gif");
		JButton bouton = new JButton("Mon bouton",img);
		
		JPanel panneau = new JPanel();
		panneau.add(bouton);
		setContentPane(panneau);
		setSize(200,100);
		setVisible(true);
		
		tb = new TwitchBot();
	}
	
	public static void main(String [] args){
		WindowLauncher frame = new WindowLauncher();
	} 

}
