package Serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Client.ClientReadingThread;
import Client.ClientThread;
import Exceptions.ChargerChatException;


public class ServeurChat {

	private int port;
	private ServerSocket sock;
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
		new CommandeServeur(chat);
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
			   ClientThread serveurThread = new ClientThread(communication,chat, threads);
			   Thread t = new Thread(serveurThread);
			   threads.add(serveurThread);
			   t.start();
			System.out.println("Un utilisateur accède  au Chat. Ouverture d'une connexion...");
		}while(true);
	}
}
