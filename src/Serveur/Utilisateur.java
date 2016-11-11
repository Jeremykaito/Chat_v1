package Serveur;
import java.io.Serializable;

public class Utilisateur implements Serializable{


	private static final long serialVersionUID = 1L;
	private String pseudo;
	private String mdp;
	/**
	 * @brief Constructeur d'un nouvel utilisateur
	 * @param pseudo
	 * @param mdp
	 */
	public Utilisateur(String pseudo, String mdp) {
		this.pseudo = pseudo;
		this.mdp = mdp;
	}
	
	public String toString(){
		return this.pseudo +" ";
	}
	public String getPseudo() {
		return pseudo;
	}

	public String getMdp() {
		return mdp;
	}

}
