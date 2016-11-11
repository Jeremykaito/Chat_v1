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

import Exceptions.ChargerChatException;
import Exceptions.SauvegarderChatException;

public class Chat {

	//Variables
	private ArrayList<Utilisateur> communaute; 	// La liste des utilisateurs
	private ArrayList<Topic> liste_topics; // Liste des sujets du chat
	private String nomFichierUtilisateurs; //nom du fichier de sauvegarde des utilisateurs
	private String nomFichierTopics; //nom du fichier de sauvegarde des topics
	private int nbUsers;
	private int nbConnectedUsers;

	/**
	 * @brief Constructeur via les donn�es charg�es 
	 * @param data
	 */
	public Chat() throws FileNotFoundException{

		//Initialisation
		liste_topics = new ArrayList();
		communaute = new ArrayList();
		nomFichierUtilisateurs="./users.obj";
		nomFichierTopics="./topics.obj";
		nbUsers = 0;
		nbConnectedUsers=0;
		
		// Chargement du chat sauvegard�
		try {
			chargerChat();
			System.out.println("Chargement du chat r�ussi.");
		} catch (FileNotFoundException | ChargerChatException e) {
			System.out.println("Erreur lors du chargement des donn�es. Un nouveau chat a �t� cr��.");
		}
	}

	public int getNbUsers() {
		return nbUsers;
	}
	
	public int getNbConnectedUsers() {
		return nbConnectedUsers;
	}
	
	public void setNbUsers(int nbUsers) {
		this.nbUsers = nbUsers;
	}

	/**
	 * @brief Creation d'un utilisateur
	 * @param pseudo
	 * @param mdp
	 * @return boolean, le resultat de la cr�ation
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
	 * @param pseudo
	 * @param mdp
	 * @return boolean, le resultat de la tentative connexion
	 */
	public boolean connexion(String pseudo, String mdp) {
		for(Utilisateur u : communaute) {
			if(pseudo.equals(u.getPseudo()) && mdp.equals(u.getMdp()))
				nbConnectedUsers++;
				return true;
		}
		return false;
	}
	/**
	 * 
	 */
	@Override
	public String toString() {
		String s ="";
		for(Utilisateur u: communaute){
			s=s+u.toString();
		}
		return s;
	}

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
			ios.close();
			}
		}
		catch(FileNotFoundException e){ //Si le fichier n'est pas trouv�

			throw new FileNotFoundException();
		}

		catch(IOException | ClassNotFoundException  e){ //Si une autre erreur appara�t
			e.printStackTrace();
			throw new ChargerChatException("Erreur durant le chargement des topics.");
		}
	}

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

}
