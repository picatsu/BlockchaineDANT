
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Sorex extends JFrame{
	public static Serveur server;
	
	
	public Sorex(Serveur server)throws Exception {
		//Serveur en parametre
		super("Sorex");
		this.server = server;
		buildGUI();
	}
	JPanel panel = new JPanel()
    {
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
    };
	private void buildGUI() throws Exception {

		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 80, 20, 80));
	    panel.setLayout(new GridBagLayout());
	    GridBagConstraints constraints = new GridBagConstraints();
//	    constraints.weightx = 1;
//	    constraints.weighty = .25;
	    constraints.insets = new Insets(5, 0, 5, 0);
	    constraints.gridwidth = GridBagConstraints.REMAINDER;
	    constraints.fill = GridBagConstraints.BOTH;
	    JLabel createAccount = new JLabel("Creation");
	    JToggleButton startServer = new JToggleButton("Lancer le serveur");

	    JButton createClient = new JButton("Create Client");
	    panel.add(startServer);
	    panel.add(createClient);    
	    startServer.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {//probleme ici
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                        new SwingWorker<Void, Void>() {
                            @Override
                            public Void doInBackground() {
                                try {                                
                                server.startServer();                           
                               
                                } catch(Exception ex) {}
                                return null;
                                }
                                
                            }.execute();  
                        	
                        	Toast toto = new Toast(" Serveur Active !  ", panel.getWidth() -500  , panel.getHeight() -100);
    						
    						toto.showtoast();
    						
                            startServer.setText("Serveur Active");

                } else {
                	Toast toto = new Toast(" Serveur Desactive !  ", panel.getWidth() -500  , panel.getHeight() -100);
					
					toto.showtoast();
					startServer.setText(" Lancer Serveur");
                   //close server
                }
                
            }
            
        });

	    createClient.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) {
	        	new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        try {
            	        	ClientScreen one = new ClientScreen();
                        	one.setVisible(true);  
                        	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        	one.setLocation(dim.width/2-one.getSize().width/2, dim.height/2-one.getSize().height/2);
                        } catch(Exception ex) {}
                        return null;
                        }
                        
                    }.execute();
	        }
	    });
	    
	    
	    this.add(panel);
	    //this.add(background);
	    this.setVisible(true);
	 
	}
	
	public static void main(String[] args) {
		Serveur server = new Serveur(5000);
		try {
			Sorex achraf = new Sorex(server);
		} catch(Exception ex) {}

	}

}
