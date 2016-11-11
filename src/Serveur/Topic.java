package Serveur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Topic implements Serializable{
	
	private String titre;
	private String description;
	
	private ArrayList<Message> discussion;
	
	/**
	 * @brief Constructeur d'un nouveau topic
	 * @param titre, nom du topic
	 * @param description, court énoncé du sujet 
	 */
	public Topic(String titre, String description) {
		this.titre = titre;
		this.description = description;
		//todo date
		discussion = new ArrayList();
		
	}
	
	public void addComment(Message comment){
		discussion.add(comment);
	}
}
