package Serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Exceptions.ChargerChatException;


public class ServeurChat {

	private int port;
	private ServerSocket sock;
	private Chat chat;
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
			new Thread(new ServiceChat(communication,chat)).start();
			System.out.println("Un utilisateur accède  au Chat. Ouverture d'une connexion...");
		}while(true);
	}
}
