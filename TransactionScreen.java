
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class TransactionScreen extends JFrame {
	ClientScreen launcher;
	
	
	public TransactionScreen(ClientScreen launcher) {
		super("Transfer de credit");
		this.launcher = launcher;
		this.setSize(200, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextField nom = new JTextField(10);
		JTextField prenom = new JTextField(10);
		JTextField Montant = new JTextField(10);
		

		JPanel panel = new JPanel(new BorderLayout(3, 3));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(panel);

		JPanel labels = new JPanel(new GridLayout(0, 1));
		JPanel controls = new JPanel(new GridLayout(0, 1));
		panel.add(labels, BorderLayout.WEST);
		panel.add(controls, BorderLayout.CENTER);

		
		labels.add(new JLabel("Nom du Destinataire : "));
		controls.add(nom);
		labels.add(new JLabel("Prenom : "));
		controls.add(prenom);
		labels.add(new JLabel("Montant : "));
		controls.add(Montant);
		JButton send = new JButton("Envoyer");
		
		
		
		
		
		send.addActionListener(new ActionListener() //// FAIRE  UN VIREMENT 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	
	        	try {
	        		if(controls != null) {
	        			new SwingWorker<Void, Void>() {
	        				  
	        				  @Override public Void doInBackground() {
	        				  
	        				  
	        				  try { 
	        					  String transaction = "transaction;";
// public static boolean Transaction(String nomDestinataire, String prenomDestinataire, String nomSource, String MdpSource, Long montant)
	        					  transaction+=nom.getText()+";"
	        							  			+prenom.getText()+";"+launcher.nomsource+";"+launcher.getMdpsource()+";"+Montant.getText();
	        							  			
	        							  			
	        					  launcher.envoyerAuServeur(transaction, 4);
	        					  
	        				  
	        				  } catch(Exception ex) {} return null; }
	        				  
	        				  }.execute();
	        			//launcher.solde = Long.toString(Long.parseLong(launcher.solde) - Long.parseLong(Montant.getText()));

	        		}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
	        	//code pour implementer la fonctionnalite
	        }
	    });

		panel.add(send, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}
	
}
