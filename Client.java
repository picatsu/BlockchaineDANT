

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Client {
	

	public static void main(String args[]) throws Exception
	{
		Socket sk=new Socket("127.0.0.1",5000);
		BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		PrintStream sout=new PrintStream(sk.getOutputStream());
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
		String s;
		
		
		while (  true )
		{
			System.out.print("Client : ");
			
			TimeUnit.SECONDS.sleep(1);
			
			///// LES QUESTIONS
			s=Iniit();
			TimeUnit.SECONDS.sleep(1);
			sout.println(s);

			if ( s.equalsIgnoreCase("BYE") ) {
                 System.out.println("Connection ended by client");
                     break;}
				
			s=sin.readLine();
			System.out.print("Server : "+s+"\n");
  			
		}
		   sk.close();
		  sin.close();
		 sout.close();
 		stdin.close();
 		
 		
 		
	}
	
	public static String Iniit() throws InterruptedException {
		
		
	////////////////////////////////////////////  CHOIX DE QUOI FAIRE 
		
				System.out.println(" Que veux-tu ? ");
				System.out.println(" #### 1 : Ajouter un compte \n #### 2 : Afficher blockchaine \n #### 3 : Retirer ton argent \n #### 4 : Sortir \\n");
		    	  
		        int choix=0;
		        Compte c = new Compte();
		        Scanner sc ;
		        
		        
		        while(true){
		        		
		        	    sc =new Scanner(System.in);
		        	    choix = sc.nextInt();
		          		if(( choix != 1 || choix != 2 || choix != 3 ||  choix != 4  ))
		          			break;
		         }
		        
		        switch (choix)
		          {
		            case 1:    //CREATION COMPTE 
		            	
		            	System.out.println(" ## Ecrit avec la bonne synthaxe :\n nom;prenom;adresse;numeroDeTelephone;Email;mdp \n");
		            	sc =new Scanner(System.in);
		            	String a = (sc.nextLine());
		            	String[] reponses = a.split(";");		  
		            	c = new Compte(reponses[0],reponses[1],reponses[2],reponses[3],reponses[4],reponses[5]);
		            	return "creation;"+c.toString() ;
		            	
		            case 2:   // AFFICHER LES COMPTES
		            	return "affiche;"+Serveur.blockchaine.toString();
		            	
		            case 3:
		            	System.out.println(" ## Ecrit avec la bonne synthaxe :\n nomDESTINATAIRE;prenomDESTINATAIRE;nomSOURCE;prenomSOURCE;montant \n");
		            	sc = new Scanner(System.in); 
		            	String envoi = sc.nextLine();
		                return "transaction;"+envoi;
		              
		            case 4:
		            	return "BYE";
		             
		            default:

		            	System.out.println("ERREUR ");
		          }
			
		     return "";
		
	}
	
	
	
    
    
}
