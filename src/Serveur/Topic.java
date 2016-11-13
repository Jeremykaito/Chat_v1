package Serveur;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Topic : sujet de discussion pouvant accueillir plusieurs utilisateurs
 * @author Jérémy Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class Topic implements Serializable{
	
	//Variables
	private static final long serialVersionUID = 1L;
	private String titre;
	private String description;
	private ArrayList<Message> discussion; //la liste des messages contenus dans le topic
	
	/**
	 * @brief Constructeur d'un nouveau topic
	 * @param String titre : nom du topic
	 * @param String description : court énoncé du sujet 
	 */
	public Topic(String titre, String description) {
		this.setTitre(titre);
		this.setDescription(description);
		discussion = new ArrayList<Message>();
		
	}
	
	/**
	 * @brief Ajout d'un message dans le topic
	 * @param Message : message à ajouter
	 */
	public void addComment(Message comment){
		discussion.add(comment);
	}
	
	/**
	 * @brief Getters et setters
	 */
	public ArrayList<Message> getMessages() {
		return discussion;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		return titre + " : " + description;
	}
}
