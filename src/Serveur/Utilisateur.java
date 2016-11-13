package Serveur;
import java.io.Serializable;

/**
 * Utilisateur : utilisateur du chat
 * @author Jérémy Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class Utilisateur implements Serializable{

	//Variables 
	private static final long serialVersionUID = 1L;
	private String pseudo;
	private String mdp;
	
	/**
	 * @brief Constructeur d'un nouvel utilisateur
	 * @param String pseudo : le login de l'utilisateur
	 * @param String mdp : le mot de passe de l'utilisateur
	 */
	public Utilisateur(String pseudo, String mdp) {
		this.pseudo = pseudo;
		this.mdp = mdp;
	}
	
	
	/**
	 * @brief Getters et setters
	 */
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
