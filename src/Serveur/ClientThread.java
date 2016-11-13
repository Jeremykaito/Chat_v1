package Serveur;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class ClientThread implements Runnable{

	private Socket communication;
	private Chat chat;
	private final ArrayList<ClientThread> threads;
	private DataInputStream reception;
	private DataOutputStream emission;
	private String nom;
	private String topic;
	private String choix;


	public ClientThread(Socket com, Chat chat, ArrayList<ClientThread> threads){
		this.communication=com;
		this.chat=chat;
		this.threads=threads;
	}

	@Override
	public synchronized void run() {

		ArrayList<ClientThread> threads = this.threads;

		try{
			reception = new DataInputStream(communication.getInputStream());
			emission = new DataOutputStream(communication.getOutputStream());

			do{
				//Menu
				emission.writeUTF("---------------------");
				emission.writeUTF("Bienvenue");
				emission.writeUTF("1 - Se connecter");
				emission.writeUTF("2 - S'inscrire ");
				emission.writeUTF("3 - Quitter ");
				emission.writeUTF("---------------------");
				emission.writeUTF("Que voulez-vous faire (1 à 3) ?");

				choix = reception.readUTF();
				switch(choix) {
				case "1":
					vue_connexion();
					break;
				case "2":
					vue_inscription();
					break;
				case "3":
					emission.writeUTF("Au revoir !");
					communication.close();
					System.exit(0);
				default :
					emission.writeUTF("Commande incorrecte...");
					break;
				}
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void vue_connexion() throws IOException {
		//Données utilisateur
		String pseudo;
		String mdp;

		//Saisie des données par l'utilisateur
		emission.writeUTF("Veuillez entrer votre pseudo :");
		pseudo = reception.readUTF();
		emission.writeUTF("Veuillez entrer votre mot de passe :");
		mdp = reception.readUTF();

		//Vérification de la réponse serveur reçue
		if(chat.connexion(pseudo,mdp)){
			nom = pseudo;
			//Accès au chat
			vue_chat();
		}
		else emission.writeUTF("Identification incorrecte!");
	}

	private void vue_inscription() throws IOException {

		//Données utilisateur
		String pseudo;
		String mdp, mdp_confirm;

		//Saisie des données par l'utilisateur
		emission.writeUTF("Veuillez entrer un nom :");
		pseudo = reception.readUTF();
		emission.writeUTF("Veuillez entrer un mot de passe :");
		mdp = reception.readUTF();	
		emission.writeUTF("Veuillez entrer une nouvelle fois votre mot de passe :");
		mdp_confirm = reception.readUTF();

		//Vérification de la confirmation du mot de passe
		if(mdp.equals(mdp_confirm)) {

			//Vérification de la réponse serveur reçue
			if(chat.creationUtilisateur(pseudo,mdp)) {
				System.out.println("Création réussie !");
			}
			else System.out.println("Désolé ce nom est déjà pris !");
		}
		else System.out.println("Erreur mot de passe non validé");	
	}


	public void  vue_chat() throws IOException{

		boolean ouvert = true;
		System.out.println("- Bonjour "+ nom+" -");
		do{
			//Affichage des topics existants
			emission.writeUTF("\nTopics existants : \n" + chat.toStringTopics());

			//Menu
			emission.writeUTF("1 - Rejoindre un topic");
			emission.writeUTF("2 - Créer un topic");
			emission.writeUTF("3 - Déconnexion");
			emission.writeUTF("Que voulez-vous faire ? (1 - 3)");

			//Interprétation de la commande
			choix = reception.readUTF();
			switch(choix) {

			case "1":
				vue_rejoindreTopic();
				break;

			case "2":
				vue_creationTopic();
				break;

			case "3":
				emission.writeUTF("Déconnexion...");
				ouvert = false;
				break;

			default :
				break;
			}

		}while(ouvert);
	}

	private void vue_rejoindreTopic() throws IOException {

		//Données utilisateur
		String titre;

		//Affichage des topics existants
		emission.writeUTF("\nTopics existants : \n" + chat.toStringTopics());

		//Saisie des données par l'utilisateur
		emission.writeUTF("Veuillez entrer le titre :");
		titre = reception.readUTF();

		if(chat.rejoindreTopic(titre)) {
			emission.writeUTF("Entrée dans le topic...");
			topic = titre;
			vue_topic(topic);
		}
		else emission.writeUTF("Désolé aucun topic correspondant.");
	}

	private void vue_creationTopic() throws IOException {

		//Données utilisateur
		String titre;
		String description;

		//Saisie des données par l'utilisateur
		emission.writeUTF("Veuillez entrer un titre :");
		titre = reception.readUTF();
		emission.writeUTF("Veuillez entrer une description :");
		description = reception.readUTF();

		//Création du topic
		if(chat.creationTopic(titre,description)) {
			emission.writeUTF("Création réussie !");
		}
		else emission.writeUTF("Désolé ce titre est déjà pris !");
	}

	private void vue_topic(String topic) throws IOException{

		boolean ouvert =true;

		//Message de bienvenue
		emission.writeUTF("Bienvenue dans le topic " + topic);
		emission.writeUTF("----------------------------------------------------------");
		emission.writeUTF("Commandes : ");
		emission.writeUTF("/exit pour quitter ce topic");
		emission.writeUTF("----------------------------------------------------------");

		do{

			//Affichage du pseudo
			emission.writeUTF(nom + " : ");

			//On récupère le texte tapé par l'utilisateur
			String msg = reception.readUTF();

			if(msg.equalsIgnoreCase("/exit")){
				ouvert=false;
			}
			else{ 
				chat.addMessage(topic,msg);
				this.emission.writeUTF(nom + " : " + msg);
			}
		}while (ouvert);
	}
}
