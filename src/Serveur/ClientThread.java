package Serveur;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ClientThread : thread client traitant la communication serveur/client
 * @author J�r�my Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class ClientThread implements Runnable{

	//Variables de communication
	private Socket communication;
	private DataInputStream reception;
	private DataOutputStream emission;
	
	//Variables diverses
	private final ArrayList<ClientThread> threads; //La liste des threads clients
	private Chat chat;
	private String nom;
	private String topic;
	private String choix;

	/**
	 * @brief Constructeur
	 * @param Socket com : la socket
	 * @param Chat chat : le chat dans lequel les utilisateurs discutent
	 * @param ArrayList<ClientThread> threads : la liste de threads clients
	 */
	public ClientThread(Socket com, Chat chat, ArrayList<ClientThread> threads){
		this.communication=com;
		this.chat=chat;
		this.threads=threads;
		this.nom="";
		this.topic="";
	}

	/**
	 * @brief Traite les diff�rentes commandes
	 */
	@Override
	public synchronized void run() {

		ArrayList<ClientThread> threads = this.threads;

		try{
			//Cr�ation des flux
			reception = new DataInputStream(communication.getInputStream());
			emission = new DataOutputStream(communication.getOutputStream());

			do{
				//Affichage du  menu
				emission.writeUTF("---------------------");
				emission.writeUTF("Bienvenue");
				emission.writeUTF("1 - Se connecter");
				emission.writeUTF("2 - S'inscrire ");
				emission.writeUTF("3 - Quitter ");
				emission.writeUTF("---------------------");
				emission.writeUTF("Que voulez-vous faire (1 � 3) ?");

				//Lecture du choix de l'utilisateur
				choix = reception.readUTF();
				
				switch(choix) {
				
				//Se connecter
				case "1":
					vue_connexion();
					break;
				
				//S'incrire
				case "2":
					vue_inscription();
					break;
					
				//Quitter l'application
				case "3":
					emission.writeUTF("Au revoir !");
					emission.writeUTF("/quit");
					
					//On ferme la socket et les flux
					emission.close();
					reception.close();
					communication.close();
					
					//On sauvegarde le chat
					chat.sauvegarderChat();
					
				default :
					emission.writeUTF("Commande incorrecte...");
					break;
				}
			}while(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @brief Affichage du menu de connexion
	 */
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
		
		//Pour emp�cher la connexion si l'utilisateur est d�j� connect�
		for (ClientThread clientThread : threads) {
			if (clientThread.nom.equals(pseudo))
				connected=true;
		}
		
		//Si les donn�es correspondent, l'utilisateur est connect�
		if(chat.connexion(pseudo,mdp) && connected==false){
			nom = pseudo;
			//Acc�s au chat
			vue_chat();
		}
		else emission.writeUTF("Identification incorrecte!");
	}

	/**
	 * @brief Affichage du menu d'inscription
	 */
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


	/**
	 * @brief Affichage du menu du chat
	 */
	public void  vue_chat() throws IOException{

		boolean ouvert = true;
		emission.writeUTF("\n- Bonjour "+ nom+" -");
		
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
				nom="";
				ouvert = false;
				break;

			default :
				emission.writeUTF("Commande incorrecte...");
				break;
			}

		}
	}

	/**
	 * @brief Affichage du menu pour rejoindre un topic
	 */
	private synchronized void vue_rejoindreTopic() throws IOException {

		//Donn�es utilisateur
		String titre;

		//Affichage des topics existants 
		emission.writeUTF("\nTopics existants : \n");
		for(Topic topic : chat.getTopics()) {
			int nb_connecte=0;
			for(ClientThread t : threads){
				if(t.topic.equals(topic.getTitre()))
					nb_connecte++;
			}
			emission.writeUTF(topic.toString()+" - (Personnes connect�es : "+nb_connecte+")");
			}
		
		//Saisie des donn�es par l'utilisateur
		emission.writeUTF("\nVeuillez entrer le titre :");
		titre = reception.readUTF();

		//Si le topic existe, l'utilisateur le rejoint
		if(chat.existTopic(titre)) {
			topic = titre;
			vue_topic(topic);
		}
		else emission.writeUTF("D�sol� aucun topic correspondant.");
	}

	/**
	 * @brief Affichage du menu de cr�ation d'un topic
	 */
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

	/**
	 * @brief Affichage de l'interface permettant de communiquer dans le topic
	 */
	private synchronized void vue_topic(String topic) throws IOException{

		boolean ouvert =true;

		//Message de bienvenue
		emission.writeUTF("\nBienvenue dans le topic " + topic + "\n");
		emission.writeUTF("----------------------------------------------------------");
		emission.writeUTF("Commandes : ");
		emission.writeUTF("/exit pour quitter ce topic");
		emission.writeUTF("----------------------------------------------------------\n");
		
		emission.writeUTF(chat.getTopicMessages(topic));

		do{

			//On r�cup�re le texte tap� par l'utilisateur
			String msg = reception.readUTF();

			//Si le message est /exit, on quitte le topic
			if(msg.equalsIgnoreCase("/exit")){
				ouvert=false;
				topic = "";
			}
			
			//Sinon, on envoie le message aux utilisateurs du m�me topic
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
