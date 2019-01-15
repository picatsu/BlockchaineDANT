
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ClientScreenPanel extends JPanel{
	ClientScreen launcher;
	public ClientScreenPanel(ClientScreen launcher) {
		this.launcher = launcher;
		this.setBorder(BorderFactory.createEmptyBorder(0, 80, 20, 80));
	    this.setLayout(new GridBagLayout());
	    GridBagConstraints constraints = new GridBagConstraints();
//	    constraints.weightx = 1;
//	    constraints.weighty = .25;
	    constraints.insets = new Insets(5, 0, 5, 0);
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.fill = GridBagConstraints.BOTH;
	    JLabel createAccount = new JLabel("Creation");
	    createAccount.setText("<html><h1>Bienvenu Dans SorEx, l'application innovante d'echange en blockchain de Sorbonne Universite. Que Souhaitez-vous faire ? :</h1></html><br>");
//	    this.add(createAccount);
	    
	    
	    JButton createAccountButton = new JButton("Creer un Compte");
	    JButton readMeButton = new JButton("README");
	    JButton SignInButton = new JButton("Se connecter");
	    JButton showBlockChain = new JButton("Afficher la blockchain");
	    
	    
	    showBlockChain.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	try {
	        		 JOptionPane.showMessageDialog(launcher,
	        				 Sorex.server.blockchaine.toString());
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }
	    });
	    
	    
	    createAccountButton.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	launcher.getContentPane().removeAll();
	        	launcher.getContentPane().add(new CreateAccountScreen(launcher));
	        	launcher.validate();
	        }
	    });
	    readMeButton.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	//a remplacer par le lien de la page web
	        	openWebpage("http://sorex.dx.am/");
	        }
	    });
	    SignInButton.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	launcher.getContentPane().removeAll();
	        	launcher.getContentPane().add(new LoginScreen(launcher));
	        	launcher.validate();
	        }
	    });

	    //	    login.setPreferredSize( new Dimension(20,20));

//	    password.setPreferredSize(new Dimension(20,20));
	    
//	    mainPanel.add(createAccount);
	    this.add(createAccountButton,constraints);
	    this.add(SignInButton,constraints);
	    this.add(readMeButton,constraints);
	    this.add(showBlockChain,constraints);

	    
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
	 private void openWebpage(String url)
     {
         try
             {
                 URI urI = new URL(url).toURI();
                 Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                 if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
                     desktop.browse(urI);
             }
         catch (Exception e)
             {
                 e.printStackTrace();

                 
             }
     }
}
