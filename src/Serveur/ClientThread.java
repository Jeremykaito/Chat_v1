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
	private String nom="";
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
				emission.writeUTF("Que voulez-vous faire (1 � 3) ?");

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
					emission.writeUTF("/quit");
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

	public synchronized void vue_connexion() throws IOException {
		//Donn�es utilisateur
		String pseudo;
		String mdp;
		boolean connected=false;
		//Saisie des donn�es par l'utilisateur
		emission.writeUTF("Veuillez entrer votre pseudo :");
		pseudo = reception.readUTF();
		emission.writeUTF("Veuillez entrer votre mot de passe :");
		mdp = reception.readUTF();
		
		for (ClientThread clientThread : threads) {
			if (clientThread.nom.equals(pseudo))
				connected=true;
		}
		//V�rification de la r�ponse serveur re�ue
		if(chat.connexion(pseudo,mdp) && connected==false){
			nom = pseudo;
			//Acc�s au chat
			vue_chat();
		}
		else emission.writeUTF("Identification incorrecte!");
	}

	private synchronized void vue_inscription() throws IOException {

		//Donn�es utilisateur
		String pseudo;
		String mdp, mdp_confirm;

		//Saisie des donn�es par l'utilisateur
		emission.writeUTF("Veuillez entrer un nom :");
		pseudo = reception.readUTF();
		emission.writeUTF("Veuillez entrer un mot de passe :");
		mdp = reception.readUTF();	
		emission.writeUTF("Veuillez entrer une nouvelle fois votre mot de passe :");
		mdp_confirm = reception.readUTF();

		//V�rification de la confirmation du mot de passe
		if(mdp.equals(mdp_confirm)) {

			//V�rification de la r�ponse serveur re�ue
			if(chat.creationUtilisateur(pseudo,mdp)) {
				emission.writeUTF("Cr�ation r�ussie !");
			}
			else emission.writeUTF("D�sol� ce nom est d�j� pris !");
		}
		else emission.writeUTF("Erreur mot de passe non valid�");	
	}


	public void  vue_chat() throws IOException{

		boolean ouvert = true;
		emission.writeUTF("- Bonjour "+ nom+" -");
		while(ouvert){
			//Affichage informations
			emission.writeUTF("\nNombre de topics existants : " + chat.getNbTopics());
			emission.writeUTF("\nNombre de personnes connect�es : " + threads.size());

			//Menu
			emission.writeUTF("---------------------");
			emission.writeUTF("1 - Rejoindre un topic");
			emission.writeUTF("2 - Cr�er un topic");
			emission.writeUTF("3 - D�connexion");
			emission.writeUTF("---------------------");
			emission.writeUTF("Que voulez-vous faire ? (1 - 3)");

			//Interpr�tation de la commande
			choix = reception.readUTF();
			switch(choix) {

			case "1":
				vue_rejoindreTopic();
				break;

			case "2":
				vue_creationTopic();
				break;

			case "3":
				emission.writeUTF("D�connexion...");
				ouvert = false;
				break;

			default :
				emission.writeUTF("Commande incorrecte...");
				break;
			}

		}
	}

	private synchronized void vue_rejoindreTopic() throws IOException {

		//Donn�es utilisateur
		String titre;

		emission.writeUTF("\nTopics existants : \n");


		//Saisie des donn�es par l'utilisateur
		emission.writeUTF("Veuillez entrer le titre :");
		titre = reception.readUTF();

		if(chat.existTopic(titre)) {
			topic = titre;
			vue_topic(topic);
		}
		else emission.writeUTF("D�sol� aucun topic correspondant.");
	}

	private synchronized void vue_creationTopic() throws IOException {

		//Donn�es utilisateur
		String titre;
		String description;

		//Saisie des donn�es par l'utilisateur
		emission.writeUTF("Veuillez entrer un titre :");
		titre = reception.readUTF();
		emission.writeUTF("Veuillez entrer une description :");
		description = reception.readUTF();

		//Cr�ation du topic
		if(chat.creationTopic(titre,description)) {
			emission.writeUTF("Cr�ation r�ussie !");
		}
		else emission.writeUTF("D�sol� ce titre est d�j� pris !");
	}

	private synchronized void vue_topic(String topic) throws IOException{

		boolean ouvert =true;

		//Message de bienvenue
		emission.writeUTF("Bienvenue dans le topic " + topic);
		emission.writeUTF("----------------------------------------------------------");
		emission.writeUTF("Commandes : ");
		emission.writeUTF("/exit pour quitter ce topic");
		emission.writeUTF("----------------------------------------------------------");

		emission.writeUTF(chat.getTopicMessages(topic));

		do{

			//On r�cup�re le texte tap� par l'utilisateur
			String msg = reception.readUTF();

			if(msg.equalsIgnoreCase("/exit")){
				ouvert=false;
				topic = "";
			}
			else{ 
				chat.addMessage(topic,msg,nom);
				for (ClientThread clientThread : threads) {
					if (clientThread.topic.equals(this.topic) && !clientThread.nom.equals(this.nom)) {
						clientThread.emission.writeUTF(nom + " : " + msg);
					}

				}
			}
		}while (ouvert);
	}
}
