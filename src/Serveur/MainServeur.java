package Serveur;
import java.io.IOException;

import Exceptions.ChargerChatException;


public class MainServeur {

	public static void main(String[] args) {
		
		int port = 2002;

		try {
			
			//Nouveau serveur de chat avec un port
			System.out.println("Démarrage du serveur...");
			ServeurChat serv= new ServeurChat(port);
			
			// Autorisation des connexions
			serv.accepterConnexion();
			
		} catch (IOException | ChargerChatException e) {
			System.out.println("Erreur au lancement du serveur.");
		}

	}

}
