

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class CreateAccountScreen extends JPanel{
	ClientScreen launcher;
	
	JTextField nameField;
    JTextField lastNameField;
    JTextField adresseField;
    JTextField telField;
    JTextField login ;
    JPasswordField password;
    
	public CreateAccountScreen(ClientScreen launcher) {
		this.launcher = launcher;
		buildGUI();
		launcher.solde = "100";
	}
	
	private void buildGUI() {
		this.setBorder(BorderFactory.createEmptyBorder(100, 100, 200, 100));
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    login = new JTextField();
	    password = new JPasswordField();
	    JPasswordField confirmPassword = new JPasswordField();
	    nameField = new JTextField();
	    lastNameField = new JTextField();
	    adresseField = new JTextField();
	    telField = new JTextField();
	    
	    JLabel mail = new JLabel("Mail :");
	    JLabel mdp = new JLabel("Password :");
	    JLabel confirMdp = new JLabel("Password verification :");
	    JLabel name = new JLabel("Nom :");
	    JLabel lastName = new JLabel("Prenom :");
	    JLabel adresse = new JLabel("Adresse :");
	    JLabel tel = new JLabel("numero de tel :");
	    JButton goBack = new JButton("Go Back"); 
	    
	    goBack.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	launcher.getContentPane().removeAll();
	        	launcher.getContentPane().add(new ClientScreenPanel(launcher));
	        	launcher.validate();
	        }
	    });
	    mail.setLabelFor(login);
	    mdp.setLabelFor(password);
	    confirMdp.setLabelFor(confirmPassword);
	    name.setLabelFor(nameField);
	    lastName.setLabelFor(lastNameField);
	    adresse.setLabelFor(adresse);
	    tel.setLabelFor(telField);

	    
	    JButton create = new JButton("Creer Compte"); 
	    create.setPreferredSize(new Dimension(20,20));
	    create.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	if(Arrays.equals(password.getPassword(), confirmPassword.getPassword())) {
	        		//ajouter ici du code qui creer et stocke le compte
//	        		Compte c = new Compte();
//	        		Sorex.server.;
	        		try {
	        			new SwingWorker<Void, Void>() {
	                        @Override
	                        public Void doInBackground() {
	                            try {
	                	        	Iniit();                      
	                            } catch(Exception ex) {}
	                            return null;
	                            }
	                            
	                        }.execute();
	                        launcher.nom = nameField.getText();
	                        launcher.prenom = lastNameField.getText();
	        		} catch(Exception ex) {}
	        		
		        	JOptionPane.showMessageDialog(launcher,
		        		    "Account Created");
		        	
		        	
		        	launcher.setNomsource(lastNameField.getText());
		        	launcher.setMdpsource(new String(password.getPassword()));
		        	
		        	launcher.getContentPane().removeAll();
		        	launcher.getContentPane().add(new AccountScreen(launcher));
		        	launcher.validate();
	        	} else JOptionPane.showMessageDialog(launcher,
	        		    "Password doesnt match");
	        }
	    });
	    
	    
	    this.add(mail);
	    this.add(login);
	    this.add(mdp);
	    this.add(password);
	    this.add(confirMdp);
	    this.add(confirmPassword);
	    this.add(name);
	    this.add(nameField);
	    this.add(lastName);
	    this.add(lastNameField);
	    this.add(adresse);
	    this.add(adresseField);
	    this.add(tel);
	    this.add(telField);
	    this.add(create);
	    this.add(goBack);
	    this.setVisible(true);
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
	public void Iniit() throws Exception {
		        Compte c = new Compte();	  
		            	c = new Compte(nameField.getText(),
		            			lastNameField.getText(),adresseField.getText(),
		            			telField.getText(),login.getText(),
		            			new String(password.getPassword()));
		            	launcher.envoyerAuServeur(c.toString(),1);	           
	}
	
	public void envoyerAuServeur() {
		
	}
}
