package Serveur;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import Exceptions.ChargerChatException;
import Exceptions.SauvegarderChatException;

/**
 * Chat : outil de discussion entre plusieurs utilisateurs
 * @author J�r�my Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class Chat {

	//Variables
	private ArrayList<Utilisateur> communaute; 	// La liste des utilisateurs
	private ArrayList<Topic> liste_topics; // Liste des sujets du chat
	private String nomFichierUtilisateurs; //nom du fichier de sauvegarde des utilisateurs
	private String nomFichierTopics; //nom du fichier de sauvegarde des topics

	/**
	 * @brief Constructeur 
	 */
	public Chat() throws FileNotFoundException{

		//Initialisation
		liste_topics = new ArrayList();
		communaute = new ArrayList();
		nomFichierUtilisateurs="./users.obj";
		nomFichierTopics="./topics.obj";

		// Chargement des topics, messages et utilisateurs sauvegard�s
		try {
			chargerChat();
			System.out.println("Chargement du chat r�ussi.");
		} catch (FileNotFoundException | ChargerChatException e) {
			System.out.println("Erreur lors du chargement des donn�es. Un nouveau chat a �t� cr��.");
		}
	}


	/**
	 * @brief Cr�ation d'un utilisateur
	 * @param String pseudo : le login de l'utilisateur
	 * @param String mdp : le mot de passe de l'utilisateur
	 * @return boolean : true si l'inscription a r�ussi
	 */
	public boolean creationUtilisateur(String pseudo, String mdp){
		
		//V�rifie si l'utilisateur existe d�j�
		for(Utilisateur u : communaute) {
			if(pseudo.equals(u.getPseudo())){
				return false;
			}
		}
		
		//Cr�ation d'un nouvel utilisateur s'il n'existe pas
		Utilisateur arrivant = new Utilisateur(pseudo, mdp);
		communaute.add(arrivant); //Ajout de l'utilisateur dans la liste communaute
		return true;
	}

	/**
	 * @brief Connexion d'un utilisateur
	 * @param String pseudo : le login de l'utilisateur
	 * @param String mdp : le mot de passe de l'utilisateur
	 * @return boolean : true si la connexion a r�ussi
	 */
	public boolean connexion(String pseudo, String mdp) {
		for(Utilisateur u : communaute) {
			if(pseudo.equals(u.getPseudo()) && mdp.equals(u.getMdp())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @brief Ajout d'un message dans le topic correpondant
	 * @param String titre : le titre du topic
	 * @param String message : le message envoy�
	 * @param String auteur : l'auteur du message
	 */
	public void addMessage(String titre, String message, String auteur) {
		
		for(Topic t : liste_topics) {
			if(titre.equalsIgnoreCase(t.getTitre())){
				t.addComment(new Message(message,auteur,new Date()));
			}
		}
	}
	
	/**
	 * @brief Cr�ation d'un nouveau topic
	 * @param String titre : le titre du topic
	 * @param String description : la description du topic
	 * @return boolean : true si la cr�ation a r�ussi
	 */
	public boolean creationTopic(String titre, String description) {
		
		//On v�rifie si le topic existe d�j�
		if (existTopic(titre)) return false;
		
		//Cr�ation d'un nouveau topic s'il n'existe pas
		Topic topic = new Topic(titre, description);
		liste_topics.add(topic); //Ajout du topic dans la liste
		return true;
	}

	
	/**
	 * @brief V�rifie si un topic existe d�j�
	 * @param String titre : le titre du topic � chercher
	 * @return boolean : true si le topic existe d�j�
	 */
	public boolean existTopic(String titre) {
		for(Topic t : liste_topics) {
			if(titre.equalsIgnoreCase(t.getTitre())){
				return true;
			}
		}
		return false;
	}

	/**
	 * @brief Charge les topics, messages et utilisateurs sauvegard�s
	 */
	public void chargerChat() throws ChargerChatException,FileNotFoundException {

		//Chargement des utilisateurs
		try (InputStream is = new FileInputStream(nomFichierUtilisateurs)){
			ObjectInputStream ios = new ObjectInputStream(is);
			while (is.available() > 0) {
				communaute.add((Utilisateur) ios.readObject()); //On lit les utilisateurs depuis le fichier
			}
			ios.close();
		}
		catch(FileNotFoundException e){ //Si le fichier n'est pas trouv�

			throw new FileNotFoundException();
		}

		catch(IOException | ClassNotFoundException  e){ //Si une autre erreur appara�t
			e.printStackTrace();
			throw new ChargerChatException("Erreur durant le chargement des utilisateurs.");
		}

		//Chargement des topics
		try (InputStream is = new FileInputStream(nomFichierTopics)){
			ObjectInputStream ios = new ObjectInputStream(is);
			while (is.available() > 0) {
				liste_topics.add((Topic) ios.readObject()); //On lit les topics depuis le fichier			}
			}
			ios.close();

		}
		catch(FileNotFoundException e){ //Si le fichier n'est pas trouv�

			throw new FileNotFoundException();
		}

		catch(IOException | ClassNotFoundException  e){ //Si une autre erreur appara�t
			e.printStackTrace();
			throw new ChargerChatException("Erreur durant le chargement des topics.");
		}
	}

	
	/**
	 * @brief Sauvegarde dans un fichier les topics, messages et utilisateurs du chat en cours
	 */
	public void sauvegarderChat() throws SauvegarderChatException, IOException {

		ObjectOutputStream oos = null;

		//Sauvegarde des utilisateurs
		try (OutputStream os = new FileOutputStream(nomFichierUtilisateurs);) {

			//R�cup�ration des utilisateurs
			Utilisateur[] utilisateurs=new Utilisateur[0];
			utilisateurs=communaute.toArray(utilisateurs);

			//Ecriture dans le fichier
			oos = new ObjectOutputStream(os);
			for(Utilisateur util : utilisateurs){
				oos.writeObject(util);
			}
			oos.flush();
		} catch (IOException e ) {
			throw new SauvegarderChatException("Erreur durant la sauvegarde des utilisateurs.");
		}finally {
			oos.close();
		}

		//Sauvegarde des topics
		try (OutputStream os = new FileOutputStream(nomFichierTopics);) {

			//R�cup�ration des utilisateurs
			Topic[] topics =new Topic[0];
			topics=liste_topics.toArray(topics);

			//Ecriture dans le fichier
			oos = new ObjectOutputStream(os);
			for(Topic top : topics){
				oos.writeObject(top);
			}
			oos.flush();
		} catch (IOException e ) {
			throw new SauvegarderChatException("Erreur durant la sauvegarde des topics.");
		}finally {
			oos.close();
		}
	}

	//Getters et setters
	
	public int getNbTopics(){
		return liste_topics.size();
	}
	
	public int getNbUsers() {
		return communaute.size();
	}
	
	public String toStringCommunaut�() {
		String s ="";
		for(Utilisateur u: communaute){
			s=s+u.toString();
		}
		return s;
	}
	
	public ArrayList<Topic> getTopics() {

		return liste_topics;
	}
	
	public String getTopicMessages(String titre) {
		String messages ="aucun message ant�rieur";
		for(Topic t : liste_topics) {
			if(titre.equalsIgnoreCase(t.getTitre())){
				for(Message m : t.getMessages()){
					messages+=m.toString();
				}
				return messages;
			}
		}
		return messages;
	}
}
