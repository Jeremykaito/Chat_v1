package Serveur;
import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titre;
	private String description;
	
	private ArrayList<Message> discussion;
	
	/**
	 * @brief Constructeur d'un nouveau topic
	 * @param titre, nom du topic
	 * @param description, court énoncé du sujet 
	 */
	public Topic(String titre, String description) {
		this.setTitre(titre);
		this.setDescription(description);
		//todo date
		discussion = new ArrayList();
		
	}
	public ArrayList<Message> getMessages() {
		return discussion;
	}
	
	public void addComment(Message comment){
		discussion.add(comment);
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
