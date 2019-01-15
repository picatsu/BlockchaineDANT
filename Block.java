import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


// inspiration => https://www.toutsurlebitcoin.fr/blockchain_java_en_action.htm


public class Block {

  private int index;
  private long timestamp;
  private String hash;
  private String previousHash;
  private String data;
  private int nonce;// Le mot nonce peut egalement designer un vecteur d'initialisation. Les nonces peuvent aussi etre utilises dans des fonctions de hachage cryptographiques, en particulier en relation avec des preuves de travail.
public Block() {};
  public static final Block blockos = new Block(00000 ,(long) 00000, "vide", "NOTHING");
  
  public Block(int index, long timestamp, String previousHash, String data) {
	  // Constructeur qui va creer le Block, avec un numero index, Temps de creation, Hash precedent et donnees
    this.index = index;
    this.timestamp = timestamp;
    this.previousHash = previousHash;
    this.data = data;
    nonce = 0;
    hash = Block.calculateHash(this);
  }
  
  
  public Block(Block a) {
	  this.index = a.index;
	    this.timestamp = a.timestamp;
	    this.previousHash = a.previousHash;
	    this.data = a.data;
	    nonce = 0;
	    hash = Block.calculateHash(a);
  }


  public String str() { 	return index + timestamp + previousHash + data + nonce; }

  public String toString() { return new StringBuilder().append("Block #")
    							.append(index)
    							.append(" [previousHash : ")
    							.append(previousHash)
    							.append(", ")
    							.append("timestamp : ")
    							.append(new Date(timestamp))
    							.append(", ")
    							.append("data : ")
    							.append(data)
    							.append(", ")
    							.append("hash : ")
    							.append(hash)
    							.append("]")
    						.toString();
    
  }
// On cree ce qui est DANS le block grace a stringBuilder puis toString
  
  
//The Java MessageDigest class represents a cryptographic hash function which can calculate a message digest from binary data.
// When you receive some encrypted data you cannot see from the data itself whether it was modified during transportation. 
//A message digest can help alleviate that problem. 
  
  public static String calculateHash(Block block) {
	  
	 if(block == null)  return null;
   
	 
      MessageDigest digest = null;
      try {
    	  // SHA-256 Cryptographic Hash Function    https://en.wikipedia.org/wiki/SHA-2 
        digest = MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException e) {
    	  	System.out.println(" erreur calcule hash");
    	  	return null;
      }	
      final byte bytes[] = digest.digest(block.str().getBytes());//digest ici ne sera jamais null
      final StringBuilder builder = new StringBuilder();
			
      for (final byte b : bytes) {
        String hex = Integer.toHexString(0xff & b);
        		if (hex.length() == 1) 
        			builder.append('0');	
        builder.append(hex);
      }
      
      return builder.toString();
  }

  
  
//substring =>  This method returns new String object containing the substring of the given string from specified startIndex (inclusive)
   public void mineBlock(int difficulty) {
     nonce = 0;
     while (!getHash().substring(0,  difficulty).equals(Block.zeros(difficulty))) {
       nonce++;
       hash = Block.calculateHash(this);
     }
  }
   
   
//Our block is practically functional. It only remains to add a method to carry out its mining. The mining process will allow us to solve the enigma posed by the famous eProof of Worke. 
//Given some difficulty passed as input, we will have to find a hash for the block starting with a given number of zeros
   
   
   public static String zeros(int length) {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<length; i++)
			builder.append('0');	
		return builder.toString();
	}
   
   

   public int getIndex() {       	return index;  }

   public long getTimestamp() {  	return timestamp;  }

   public String getHash() {     	return hash;   }

   public String getPreviousHash() { return previousHash; }

   public String getData() { 		return data; }
   
   
   public Long  getBalance() { //=> "Client;"+index+";"+nom+";"+prenom+";"+adresse+";"+numeroTEL+";"+email+";"+mdp+";"+balance;
	   String[] balance = data.split(";");
	   return Long.parseLong(balance[8]);
   }
   
   public String getnomETprenomSource() {
	// transaction;nomDestinataire;prenomDestinataire;nomSource;prenomSource;montant
		//   0			1				   2				3				4       5  
	   if(data.contains("transaction")) {
		   String[] spliit = data.split(";");
		   return spliit[3]+";"+spliit[4];
	   }
	   return "";
   }


   public String getnomETprenomDestinataire() {
		// transaction;nomDestinataire;prenomDestinataire;nomSource;prenomSource;montant
			//   0			1				   2				3				4       5  
		   if(data.contains("transaction")) {
			   String[] spliit = data.split(";");
			   return spliit[1]+";"+spliit[2];
		   }
		   return "";
	   }
   
   
   
   public void setBalance(Long montant) {
	   String[] balance = data.split(";");
	   balance[8] = balance[8] + Long.toString(montant);
	   
	   data = String.join(";", balance);
	   
   }
	
}