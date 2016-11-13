package Serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Exceptions.ChargerChatException;

/**
 * ServeurChat : le serveur qui attend les connexions clients
 * @author Jérémy Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class ServeurChat {

	//Variables
	private static ServerSocket sock;
	private Chat chat;
	private static final ArrayList<ClientThread> threads = new ArrayList<ClientThread>(); //la liste des threads clients
	
	/**
	 * @brief Constructeur
	 * @param port
	 */
	public ServeurChat(int port) throws IOException, ChargerChatException{
		sock = new ServerSocket(port);
		
		//On lance le chat
		this.chat = new Chat();
		
		//On lance un nouveau thread qui va gérer les commandes serveur
		(new Thread(new CommandeServeur(chat))).start();
	}
	
	/**
	 * @brief Accepte les connexions entrantes
	 * @throws IOException
	 */
	public void accepterConnexion() throws IOException {
		
		do {
			
			//Ouverture de la connexion
			Socket communication = sock.accept();
			
			//Lancement d'un nouveau thread client
		   ClientThread serveurThread = new ClientThread(communication, chat, threads);
		   Thread t = new Thread(serveurThread);
		   threads.add(serveurThread); //On l'ajoute au tableau des threads
		   t.start();
		   System.out.println("Un utilisateur accède  au Chat. Ouverture d'une connexion...");
		}while(true);
	}
}
