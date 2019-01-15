

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AccountScreen extends JPanel{
    ClientScreen launcher;
	JLabel credit;
	JLabel nomPrenom;
	// public TransactionScreen(AccountScreen launcher) 
	
	public AccountScreen(ClientScreen launcher) {
		this.launcher = launcher;
		this.buildGUI();
	}
	
	
	private void buildGUI() {
		this.setBorder(BorderFactory.createEmptyBorder(100, 100, 200, 100));
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    nomPrenom = new JLabel(launcher.nom + " " + launcher.prenom);
	    credit = new JLabel("Solde :"+ launcher.solde); // a remplacer montant
	    	    												//par montant du compte
	    JButton transfer = new JButton("Effectuer un virement");
	    //JButton showBlockChain = new JButton("Afficher tous les transactions");
	    JButton disconnect = new JButton("Deconnexion");
	    JButton refresh = new JButton("Refresh");
	    
	    transfer.addActionListener(new ActionListener() 
	    {//public TransactionScreen(AccountScreen launcher) 
	        public void actionPerformed(ActionEvent e) {
//	        	credit.setText("Solde :"+ launcher.solde);
	        	TransactionScreen a = new TransactionScreen(launcher);
	        	a.setVisible(true);
	        }
	    });
	    refresh.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		credit.setText("Solde :"+ launcher.solde);
	    	}
	    });
	    disconnect.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	launcher.getContentPane().removeAll();
	        	launcher.getContentPane().add(new ClientScreenPanel(launcher));
	        	launcher.validate();
	        }
	    });
	    this.add(nomPrenom);
	    this.add(credit);
	    this.add(transfer);
	    this.add(refresh);
	    //this.add(showBlockChain);
	    //this.add(blockChainContenu);
	    this.add(disconnect);
	}
	 public void paintComponent(java.awt.Graphics g)
     {
         super.paintComponent(g);
         BufferedImage image = null;
         try
         {
             image = ImageIO.read(new File("C:\\Users\\m6d\\eclipse-workspace\\block\\src\\background.jpg"));
         }
         catch (IOException e)
         {

             e.printStackTrace();
         }
         g.drawImage(image, 0, 0, 1000, 600, this);
     }
}
