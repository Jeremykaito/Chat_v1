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
	private int port;
	private static ServerSocket sock;
	private Chat chat;
	private static final ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
	
	/**
	 * @brief Constructeur du serveur
	 * @param port
	 * @throws IOException
	 * @throws ChargerChatException 
	 */
	public ServeurChat(int port) throws IOException, ChargerChatException{
		this.port=port;
		sock = new ServerSocket(port);
		this.chat = new Chat();
		(new Thread(new CommandeServeur(chat))).start();
	}
	/**
	 * @brief Accepter les connexions entrantes
	 * @throws IOException
	 */
	public void accepterConnexion() throws IOException {
		
		do {
			//Ouverture de la connexion
			Socket communication = sock.accept();
			//Lancement d'un nouveau Thread
		   ClientThread serveurThread = new ClientThread(communication, chat, threads);
		   Thread t = new Thread(serveurThread);
		   threads.add(serveurThread);
		   t.start();
			System.out.println("Un utilisateur accède  au Chat. Ouverture d'une connexion...");
		}while(true);
	}
}
