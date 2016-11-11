package Serveur;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	
	private String contenu;
	private Utilisateur auteur;
	private Date date;
	
	/**
	 * @brief Constructeur d'un nouveau message
	 * @param contenu, le texte du message
	 * @param auteur, l'utilisateur qui poste le message
	 */
	public Message(String text, Utilisateur auteur) {
		this.auteur = auteur;
		this.contenu = text;
		//todo date
	}
	
	public String getContenu() {
		return contenu;
	}

	public Utilisateur getAuteur() {
		return auteur;
	}

	public Date getDate() {
		return date;
	}
}
