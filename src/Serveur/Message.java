package Serveur;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Message : message envoyé par un utilisateur
 * @author Jérémy Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class Message implements Serializable{
	
	//Variables
	private static final long serialVersionUID = 1L;
	private String contenu;
	private String auteur;
	private Date date;
	
	/**
	 * @brief Constructeur d'un nouveau message
	 * @param String text : le texte du message
	 * @param String auteur : l'utilisateur qui poste le message
	 * * @param Date date : la date à laquelle le message a été posté
	 */
	public Message(String text, String auteur, Date date) {
		this.auteur = auteur;
		this.contenu = text;
		this.date = date;
	}
	
	/**
	 * @brief Getters et setters
	 */
	public String toString() {
		String msg="";
		msg += "("+getDate()+") "+auteur + " : " + contenu +"\n";
		return msg;
	}

	public String getAuteur() {
		return auteur;
	}

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}
}
