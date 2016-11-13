package Serveur;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String contenu;
	private String auteur;
	private Date date;
	
	/**
	 * @brief Constructeur d'un nouveau message
	 * @param contenu, le texte du message
	 * @param auteur, l'utilisateur qui poste le message
	 */
	public Message(String text, String auteur, Date date) {
		this.auteur = auteur;
		this.contenu = text;
		this.date = date;

	}
	/**
	 * 
	 * @return
	 */
	public String toString() {
		String msg="";
		msg += "("+getDate()+") "+auteur + " : " + contenu +"\n";
		return msg;
	}
	/**
	 * 
	 * @return
	 */
	public String getAuteur() {
		return auteur;
	}
	/**
	 * 
	 * @return
	 */
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}
}
