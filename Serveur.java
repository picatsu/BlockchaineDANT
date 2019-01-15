import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;




public class Serveur {

	private int 			    port=0;
	private ServerSocket 	    server=null;
	private Socket 			    client=null;
	private ExecutorService     executorService = null;
	private int 			    clientcount=0;
	public static Blockchaine   blockchaine= new Blockchaine(4);
	static List<Block> builder = new ArrayList<Block>();

	public static void main(String[] args) throws IOException {
		Serveur serverobj=new Serveur(5000);
		serverobj.startServer();
	}

	public Serveur(int port){

		this.port=port;
		executorService = Executors.newFixedThreadPool(5);
	}

	public void startServer() throws IOException {

		server=new ServerSocket(5000);
		System.out.println("Serveur vient de demarrer, Envoi -1 pour arreter le serveur");

		while(true)
		{
			client=server.accept();
			clientcount++;
			ServerThread runnable= new ServerThread(client,clientcount,this);
			executorService.execute(runnable);
		}


	}



	private static class ServerThread implements Runnable {

		Serveur serveur=null;
		Socket client=null;
		BufferedReader cin;
		PrintStream cout;
		Scanner sc=new Scanner(System.in);
		int id=0;
		String s = "";

		ServerThread(Socket client, int count ,Serveur server ) throws IOException {

			this.client=client;
			this.serveur=server;
			this.id=count;
			System.out.println("Connection numero => "+id+" etablie avec le client => "+client);

			cin=new BufferedReader(new InputStreamReader(client.getInputStream()));
			cout=new PrintStream(client.getOutputStream());
//			Lecture();
		}


		@Override
		public void run() {
			int x=1;  // 1 = client tjr connect .... 0 = client decide de partir
			try{
				while(true){

					s=cin.readLine();
					if(s.contains("creation")) {////////////// CASE 1 => CREATION COMPTE 
						s = s.replace("creation;", "");
						serveur.CreationCompte(s);
						
						System.out.println(" Client Creer ! ");
					}
					
					if(s.contains("affiche")) {/////////////// CASE 2 => AFFICHER LES BLOCKS
						System.out.println(Sorex.server.blockchaine.toString());
						cout.println(Sorex.server.blockchaine.toString());
					}

					if(s.contains("transaction")) {//////////// CASE 3 => EFFECTUER TRANSACTION 
						System.out.println(s);
						 s = s.replace("transaction;", "");
						String[] recu = s.split(";");
						System.out.println(" J'ai recu =>"+recu[0]+ recu[1]+ recu[2]+ recu[3]+ Long.parseLong(recu[4]) );
						System.out.println(" Transaction effectue? =>"+
								Transaction(recu[0],recu[1], recu[2], recu[3], Long.parseLong(recu[4]) )
								);
						//transaction;nomDestinataire;prenomDestinataire;nomSource;MdpSource;montant
						// "Client;"+index+";"+nom+";"+prenom+";"+adresse+";"+numeroTEL+";"+email+";"+mdp+";"+balance;
						String email ="";
						String mdp ="";
						
						for(Block a : Sorex.server.blockchaine.getBlocks()) {
							if(a.getData().contains("client") && a.getData().contains(recu[4])){
							
								email= a.getData().split(";")[6];
								mdp = a.getData().split(";")[7];
									
								
							}

						}
						
						cout.println( Serveur.getsolde(email,mdp) );
						
						
						break;
					}
					if(s.contains("connexion")) {/////////////// CASE 2 => AFFICHER LES BLOCKS
						System.out.println("Trying to search for the account " + s);
						cout.println(seConnecter(s.split(";")[1],s.split(";")[2]));
					}
					
					//s=sc.nextLine();
					if (s.equalsIgnoreCase("bye"))
					{
						cout.println("BYE");
						x=0;
						System.out.println("Connection terminer !");
						break;
					}
					if(s.equalsIgnoreCase("show")) {
						System.out.println(blockchaine.toString());

					}

					cout.println(s);
				}
//				Lecture();

				cin.close();
				client.close();
				cout.close();
				if(x==0) {
					System.out.println( "My Job here is done #CTRL + 6 #MASTERIES #YASUO" );
					System.exit(0);
				}
			} 
			catch(IOException ex){
				System.out.println("Exception erreur =>  : "+ex);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		
	}
		public static void Lecture() throws FileNotFoundException {///// LECTURE DE LA BLOCKCHAINE DEPUIS FICHIER et actualisation
// C:\Users\m6d\eclipse-workspace\block\src\Compte.java
			FileWriter f = null;

			try {
				f = new FileWriter("C:\\Users\\m6d\\eclipse-workspace\\block\\src\\test.txt",true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			Scanner scan = new Scanner(new File("C:\\Users\\m6d\\eclipse-workspace\\block\\src\\test.txt"));
			
			while (scan.hasNextInt())
			{

				String[] dataa= Integer.toString(scan.nextInt() ).split(";");
				int index  = Integer.valueOf(dataa[0]);
				Long timestamp = Long.parseLong(dataa[1]);
				String previousHash = dataa[2];
				String data = dataa[3];
				builder.add(new Block(index,timestamp,previousHash,data));     
			}
			scan.close();
			Sorex.server.blockchaine = new Blockchaine(builder);
			System.out.println("Goodbye!");
			
		}



		public static boolean JePeuxdonneA(Block destination, Block source, Long montant) {
			if(destination == null || source == null)
				return false;
			return Long.valueOf(source.getBalance()) >= montant;
		}



		public static Pair<Block, Block> TrouverLeCouple(String nomDestinataire, String prenomDestinataire, String nomSource, String MdpSource){
			// transaction;nomDestinataire;prenomDestinataire;nomSource;prenomSource;montant
						//   0			1				   2				3				4       5  

						Block source = null;
						Block destinataire = null;
						/// JE RECHERCHE LES BLOCKS CORRESP AU SOURCE ET DESTINATAIRE   
						for(Block a : Sorex.server.blockchaine.getBlocks()) {
							if(a.getData().contains("client")){
								if( a.getData().contains(nomDestinataire)&& a.getData().contains(prenomDestinataire)) {
									destinataire= a;
								}
								if( a.getData().contains(nomSource)&& a.getData().contains(MdpSource)) {
									source= a;
								}
							}

						}
				return new Pair<>(destinataire, source);
		}
		
		
		public static Pair<Boolean, Block> trouverCompte(String email, String mdp) throws FileNotFoundException {
			////////////// ICI SI LE CLIENT POSSEDE DEJA UN COMPTE

			
			
			for(Block a : Sorex.server.blockchaine.getBlocks()) {
				if(a.getData().contains(email) && 
						a.getData().contains(mdp) && a.getData().contains("Client;") ) {
					// On Trouve le compte source
					// => "Client;"+index+";"+nom+";"+prenom+";"+adresse+";"+numeroTEL+";"+email+";"+mdp+";"+balance;

					return new Pair<>(true, a);
				}

			}
			
			return new Pair<>(false, null);
		}
		
		
		
		
		


		public static boolean Transaction(String nomDestinataire, String prenomDestinataire, String nomSource, String MdpSource, Long montant) throws FileNotFoundException{
/////// JECRIS LA TRANSACTION DANS LA BLOCKCHAINE
			
			FileWriter f = null;
			try {
				f = new FileWriter("C:\\Users\\m6d\\eclipse-workspace\\block\\src\\test.txt",true);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			PrintWriter writer = new PrintWriter(f);

			

			// public static boolean JePeuxdonneA(Block destination, Block source, Long montant) {
			Pair<Block, Block> couple = TrouverLeCouple( nomDestinataire,  prenomDestinataire,  nomSource, MdpSource);
			//if( ! JePeuxdonneA(couple.fst(), couple.snd(), montant) ) {
			////	writer.close();
			//	return false;
			////}
			
			Block bbb = Sorex.server.blockchaine.newBlock("transaction;"+nomDestinataire+";"+prenomDestinataire+";"+nomSource+";"+MdpSource+";"+montant);
			
			Sorex.server.blockchaine.addBlock( bbb );
			
			writer.println(Sorex.server.blockchaine.latestBlock()); 
			System.out.println(Sorex.server.blockchaine.latestBlock().toString());
			writer.close();
			
//			Lecture();
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
			
		}

		
		
		
		
		
		
		public static boolean CreationCompte(String texte) throws FileNotFoundException{
		/////// JECRIS LA TRANSACTION DANS LA BLOCKCHAINE
					
				FileWriter f = null;
				try {
					f = new FileWriter("C:\\Users\\m6d\\eclipse-workspace\\block\\src\\test.txt",true);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					
					PrintWriter writer = new PrintWriter(f);
			// public static boolean JePeuxdonneA(Block destination, Block source, Long montant) {
					
					
					Block bbb = Sorex.server.blockchaine.newBlock(texte);
					Sorex.server.blockchaine.addBlock(bbb);
					
					
					writer.println(texte);
//					Lecture();
					
					
					writer.close();
					
					
					
					return true;
					
				}
		
		public static String getsolde(String email, String mdp) throws Exception { 
			// ICI JE RECUPERE LE SOLDE TOTALE DU COMPTE PASSER EN PARAMETRE
			if( ! trouverCompte(email,mdp).fst() )
				return "NO COMPTE";


			String block1  = seConnecter(email,mdp); // block qui contient les details du compte
			String[] detailsCompte = ((block1.split(","))[2]).split(";");
			
			System.out.println(detailsCompte[0]+detailsCompte[1]+detailsCompte[2]);
			
			//transaction;nomDestinataire;prenomDestinataire;nomSource;MdpSource;montant
			// "Client;"+index+";"+nom+";"+prenom+";"+adresse+";"+numeroTEL+";"+email+";"+mdp+";"+balance;
			Long solde = Long.parseLong(detailsCompte[8]);
			
			String[] detailstransaction ;

			
			for(Block a : Sorex.server.blockchaine.getBlocks()) {
				// transaction;nomDestinataire;prenomDestinataire;nomSource;prenomSource;montant
				//   0			1				   2				3				4       5  

				if(a.getData().contains("transaction") && a.getData().contains(detailsCompte[3])  && a.getData().contains(detailsCompte[4])  ) {
					detailstransaction = a.getData().split(";");
					solde += Long.parseLong(detailstransaction[5]);// je retire le montant 
				}
			}
			
			return Long.toString(solde);// ICI JE RECUPERE LE SOLDE TOTALE DU COMPTE PASSER EN PARAMETRE
		}
		
		
		

		public static String seConnecter(String mail, String mdp) throws Exception {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\m6d\\eclipse-workspace\\block\\src\\test.txt"));
			String line = "";
			while ((line = reader.readLine()) != null) {     
		            if(line.contains(mail) & line.contains(mdp)) return line;
		        }
			return "";
		}
		
		

		
		
		
}////// FIN SERVER




