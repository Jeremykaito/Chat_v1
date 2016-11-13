package Serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Exceptions.ChargerChatException;


public class ServeurChat {

	private int port;
	private ServerSocket sock;
	private Chat chat;
	private final ArrayList<ServeurThread> services;
	
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
		services = new ArrayList();
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
			ServeurThread service = new ServeurThread(communication,chat, services);
			Thread t = new Thread(service);
			services.add(service);
			t.start();
			System.out.println("Un utilisateur accède  au Chat. Ouverture d'une connexion...");
		}while(true);
	}
}
