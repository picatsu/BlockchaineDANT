

public class Compte {
	public static Compte CompteDanass = new Compte("El kar","an-ass","Sidi Abderrahman","0669696969","ass@an.ass","myass");//// POUR TESTERb
	
	public static int  cpt   =0;
	private int        index;
	private String 	   nom;
	private String 	   prenom;
	private String 	   adresse;
	private String 	   numeroTEL;
	private String 	   email;
	private String 	   mdp;
	private Long       balance ;
	
	public Compte() {
		
	}
	public Compte(String nom, String prenom,String adresse, String numeroTEL, String email, String mdp){
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.numeroTEL = numeroTEL;
		this.email = email;
		this.mdp = mdp;
		this.balance = (long) 100.00;
		index=cpt;
		cpt++;
	}
	
	
	public String toString() {
		return "Client;"+index+";"+nom+";"+prenom+";"+adresse+";"+numeroTEL+";"+email+";"+mdp+";"+balance;
	}
	
	
	
	
	
	
	
	///GET SET
    
	
	public String getNom() {
		return nom;
	}
	
	public void setMdp(String mdp) {
		this.mdp=mdp;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}



	public String getNumeroTEL() {
		return numeroTEL;
	}



	public void setNumeroTEL(String numeroTEL) {
		this.numeroTEL = numeroTEL;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	public Long getBalance() {
		return balance;
	}



	public void setBalance(Long balance) {
		this.balance = balance;
	}



	

}
