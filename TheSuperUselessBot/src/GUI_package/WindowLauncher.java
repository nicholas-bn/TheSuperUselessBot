package GUI_package;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowLauncher  extends JFrame {

	   public WindowLauncher() {
		     super("titre de l'application");

		      WindowListener l = new WindowAdapter() {
		         public void windowClosing(WindowEvent e){
		            System.exit(0);
		         }
		      };

		      addWindowListener(l);

		      ImageIcon img = new ImageIcon("tips.gif");
		      JButton bouton = new JButton("Mon bouton",img);

		      JPanel panneau = new JPanel();
		      panneau.add(bouton);
		      setContentPane(panneau);
		      setSize(200,100);
		      setVisible(true);
		   }

	   public static void main(String [] args){
		   WindowLauncher frame = new WindowLauncher();
	   } 

}
