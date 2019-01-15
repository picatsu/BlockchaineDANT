
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ClientScreen extends JFrame {
	
	String s;
	Socket sk;
	BufferedReader sin;
	PrintStream sout;
	BufferedReader stdin;
	String nom;
	String prenom;
	public String nomsource= "";
	public String mdpsource ="";
	
	Block moi = null;
	
	public String solde= "100";
	
	
	public ClientScreen() throws Exception{
		super("SorEx");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().add(new ClientScreenPanel(this));
		
		start();
	    this.setVisible(true);
	    
//	    this.solde = Sorex.server.getsolde(nomsource, mdpsource);
	    
	}
	
	public String envoyerAuServeur(String string, int i) throws Exception {
//			s=compteCree;
			String  s="";
			
			switch(i) {
			
				case 1: //// CREER COMPTE
					Sorex.server.CreationCompte(string);
					break;
					
					
				case 2: //// AFFICHER BLOCKCHAINE
					return Sorex.server.toString();

					
				case 3://  LOGIN 
					String trueString = "connexion;" + string;
					sout.println(trueString);
					return sin.readLine();
					
				case 4://////   VIREMENT
					
					if(nomsource == null || mdpsource == null )
						break;
					String[] tab1 = string.split(";");
//					sout.println("transaction;"+string);
					//Sorex.server.Transaction(tab1[0], tab1[1], nomsource, mdpsource, Long.parseLong( tab1[2]));
					//sout.println("transaction;"+string);
//					s = sin.readLine();
//				    this.solde = s;
					
					break;
	
			}

			
			System.out.print("Server : "+s+"\n");
			return s;
  			
		}
 		
 		
	
	public void start() throws Exception {
		sk=new Socket("127.0.0.1",5000);
		sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		sout=new PrintStream(sk.getOutputStream());
		stdin=new BufferedReader(new InputStreamReader(System.in));
		
	}


	
	
	
	public String getNomsource() {
		return nomsource;
	}

	public void setNomsource(String nomsource) {
		this.nomsource = nomsource;
	}

	public String getMdpsource() {
		return mdpsource;
	}

	public void setMdpsource(String prenomsource) {
		this.mdpsource = prenomsource;
	}

	
}
