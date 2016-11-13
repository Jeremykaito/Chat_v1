package Serveur;
import java.io.IOException;

import Exceptions.ChargerChatException;

/**
 * MainServeur : main c�t� serveur qui lance le serveur
 * @author J�r�my Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class MainServeur {

	public static void main(String[] args) {
		
		int port = 2002;

		try {
			
			//Nouveau serveur de chat avec un port
			System.out.println("D�marrage du serveur...");
			ServeurChat serv= new ServeurChat(port);
			
			// Autorisation des connexions
			serv.accepterConnexion();
			
		} catch (IOException | ChargerChatException e) {
			System.out.println("Erreur au lancement du serveur.");
		}

	}

}
