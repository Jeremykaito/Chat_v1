package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClientChat {

	private Socket communication;
	private DataOutputStream emission;
	private DataInputStream reception;
	private Scanner commande;
	private String choix;

	public ClientChat(int port, String serveur) throws UnknownHostException, IOException{
		// Activation d'une communication avec le serveur
		communication = new Socket(serveur, port);
		// Creation des flux sortant et entrant
		emission = new DataOutputStream(communication.getOutputStream());
		reception = new DataInputStream(communication.getInputStream());

		int i = reception.read();
		System.out.println(i);

		//Creation de l'entrée clavier
		commande = new Scanner(System.in);
		choix="";
		//Accueil
		vue_accueil();
	}

	// Affichages
	public void vue_accueil() throws IOException {
		do{
			//Menu
			System.out.println("Bienvenue");
			System.out.println("1 - Se connecter");
			System.out.println("2 - S'inscrire ");
			System.out.println("3 - Quitter ");
			System.out.println("Que voulez-vous faire (1 à 3) ?");

			//Interprétation de la commande
			choix = commande.next();
			switch(choix) {
			case "1":
				vue_connexion();
				break;
			case "2":
				vue_inscription();
				break;
			case "3":
				System.out.println("Au revoir !");
				communication.close();
				System.exit(0);
			default :
				System.out.println("Commande incorrecte...");
				break;
			}
		}while(true);
	}

	public void vue_connexion() throws IOException {
		//Données utilisateur
		String pseudo;
		String mdp;

		//Saisie des données par l'utilisateur
		System.out.println("Veuillez entrer votre pseudo :");
		pseudo = commande.next();
		System.out.println("Veuillez entrer votre mdp :");
		mdp = commande.next();

		//Envoi des données au serveur
		this.emission.writeUTF("connexion");
		this.emission.writeUTF(pseudo);
		this.emission.writeUTF(mdp);

		//Vérification de la réponse serveur reçue
		if(this.reception.readBoolean()){
			//Accès au chat
			vue_chat();
		}
		else System.out.println("Identification incorrecte!");


	}

	private void vue_inscription() throws IOException {
		//Données utilisateur
		String pseudo;
		String mdp, mdp_confirm;

		//Saisie des données par l'utilisateur
		System.out.println("Veuillez entrer un nom :");
		pseudo = commande.next();
		System.out.println("Veuillez entrer un mot de passe :");
		mdp = commande.next();	
		System.out.println("Veuillez entrer une nouvelle fois votre mot de passe :");
		mdp_confirm = commande.next();

		//Vérification de la confirmation du mot de passe
		if(mdp.equals(mdp_confirm)) {
			//Envoi des données au serveur
			this.emission.writeUTF("inscription");
			this.emission.writeUTF(pseudo);
			this.emission.writeUTF(mdp);

			//Vérification de la réponse serveur reçue
			if(this.reception.readBoolean()) {
				System.out.println("Création réussie !");
			}
			else System.out.println("Désolé ce nom est déjà pris !");
		}
		else System.out.println("Erreur mot de passe non validé");	

	}

	public void vue_chat() throws IOException{

		boolean ouvert = true;
		//
		do{
			//Menu
			System.out.println("1 - Rejoindre un topic");
			System.out.println("2 - Créer un topic");
			System.out.println("3 - Déconnexion");
			System.out.println("Que voulez-vous faire ?");

			//Interprétation de la commande
			choix = commande.next();
			switch(choix) {

			case "1":
				vue_rejoindreTopic();
				break;

			case "2":
				vue_creationTopic();
				break;

			case "3":
				System.out.println("Déconnexion...");
				ouvert = false;
				break;

			default :
				break;
			}

		}while(ouvert);

	}

	private void vue_creationTopic() throws IOException {
		//Données utilisateur
		String titre;
		String description;

		//Saisie des données par l'utilisateur
		System.out.println("Veuillez entrer un titre :");
		titre = commande.next();
		System.out.println("Veuillez entrer une description :");
		description = commande.next();
		//Envoi des données au serveur
		this.emission.writeUTF("creationTopic");
		this.emission.writeUTF(titre);
		this.emission.writeUTF(description);

		if(this.reception.readBoolean()) {
			System.out.println("Création réussie !");
		}
		else System.out.println("Désolé ce titre est déjà pris !");
	}

	private void vue_rejoindreTopic() throws IOException {
		//Données utilisateur
		String titre;

		//Saisie des données par l'utilisateur
		System.out.println("Veuillez entrer le titre :");
		titre = commande.next();

		//Envoi des données au serveur
		this.emission.writeUTF("rejoindreTopic");
		this.emission.writeUTF(titre);

		if(this.reception.readBoolean()) {
			System.out.println("Entrée dans le topic");
			vue_topic();
		}
		else System.out.println("Désolé aucun topic correspondant");
	}

	private void vue_topic() throws IOException{
		//Données utilisateur
				String titre;

				//Saisie des données par l'utilisateur
				System.out.println("Veuillez entrer le titre :");
				titre = commande.next();

				//Envoi des données au serveur
				this.emission.writeUTF("rejoindreTopic");
				this.emission.writeUTF(titre);
	}

}
