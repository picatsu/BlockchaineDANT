
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class LoginScreen extends JPanel {
	ClientScreen launcher;
	String accountData;
	JTextField login ;
	JPasswordField password;
	
	public LoginScreen(	ClientScreen launcher) {
		this.launcher = launcher;
		buildGUI();
		
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
	public void buildGUI() {
		this.setBorder(BorderFactory.createEmptyBorder(200, 100, 200, 100));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		login = new JTextField();
		password = new JPasswordField();
		JLabel user = new JLabel("Login :");
		JLabel mdp = new JLabel("Password :");
		user.setLabelFor(login);
		mdp.setLabelFor(password);

		JButton logIn = new JButton("Login"); 
		JButton goBack = new JButton("Go Back"); 

		this.add(user);
		this.add(login);
		this.add(mdp);
		this.add(password);
		this.add(logIn);
		this.add(goBack);
		
		logIn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				try {
				String s = compteExisteValide();
				if(compteExiste(s)) {//methode a implementer
					launcher.getContentPane().removeAll();
					launcher.setNomsource(login.getText());
					launcher.setMdpsource(mdp.getText());
					launcher.solde = s.split(";")[8];
					launcher.nomsource = s.split(";")[2];
					launcher.nomsource = s.split(";")[3];
					launcher.getContentPane().add(new AccountScreen(launcher));
					launcher.validate();
					
				} else JOptionPane.showMessageDialog(launcher,
						"Nom de compte ou mot de pass incorrect");
			
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
			}
		});

		goBack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				launcher.getContentPane().removeAll();
				launcher.getContentPane().add(new ClientScreenPanel(launcher));
				launcher.validate();
			}
		});
	}
	public boolean compteExiste(String mail){
		return !(mail.equals(""));
		
	}
	public String compteExisteValide() throws Exception {
		String mailMdp = login.getText() + ";" + new String(password.getPassword());
		
		return launcher.envoyerAuServeur(mailMdp,3);
	}
}
