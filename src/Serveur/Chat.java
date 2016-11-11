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
	private String nomFichierChat; //nom du fichier de sauvegarde du chat
	private int nbUsers;

	/**
	 * @brief Constructeur via les données chargées 
	 * @param data
	 */
	public Chat() throws FileNotFoundException{

		//Initialisation
		liste_topics = new ArrayList();
		communaute = new ArrayList();
		nomFichierChat="./chat.obj";
		nbUsers = 0;
		// Chargement du chat sauvegardé
		try {
			chargerChat();
			System.out.println("Chargement réussi.");
		} catch (FileNotFoundException | ChargerChatException e) {
			System.out.println("Erreur lors du chargement des données. Un nouveau chat a été créé.");
		}
	}

	public int getNbUsers() {
		return nbUsers;
	}

	public void setNbUsers(int nbUsers) {
		this.nbUsers = nbUsers;
	}

	/**
	 * @brief Creation d'un utilisateur
	 * @param pseudo
	 * @param mdp
	 * @return boolean, le resultat de la création
	 */
	public boolean creationUtilisateur(String pseudo, String mdp){
		//Vérifie si l'utilisateur existe déjà
		for(Utilisateur u : communaute) {
			if(pseudo.equals(u.getPseudo())){
				return false;
			}
		}
		//Création d'un nouvel utilisateur s'il n'existe pas
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

		try (InputStream is = new FileInputStream(nomFichierChat)){
			ObjectInputStream ios = new ObjectInputStream(is);
			while (is.available() > 0) {
				communaute.add((Utilisateur) ios.readObject()); //On lit les utilisateurs depuis le fichier
				liste_topics.add((Topic) ios.readObject()); //On lit les topics depuis le fichier
			}
			ios.close();
		}
		catch(FileNotFoundException e){ //Si le fichier n'est pas trouvé

			throw new FileNotFoundException();
		}

		catch(IOException | ClassNotFoundException  e){ //Si une autre erreur apparaît
			e.printStackTrace();
			throw new ChargerChatException("Erreur durant le chargement des données.");
		}
	}

	public void sauvegarderChat() throws SauvegarderChatException {
		Utilisateur[] utilisateurs=new Utilisateur[0];
		Topic[] topics=new Topic[0];
		utilisateurs=communaute.toArray(utilisateurs);
		topics=liste_topics.toArray(topics);

		try (OutputStream os = new FileOutputStream(nomFichierChat);) {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			for(Utilisateur util : utilisateurs){
				oos.writeObject(util);
			}
			for(Topic top : topics){
				oos.writeObject(top);
			}
			oos.flush();
			oos.close();
		} catch (IOException e ) {
			throw new SauvegarderChatException("Erreur durant la sauvegarde des données.");
		}
	}

}
