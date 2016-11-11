package Client;
import java.io.IOException;
import java.net.UnknownHostException;


public class MainClient {

	public static void main(String[] args) {
		//Définition du port et de l'adresse de connexion
		int port=2002;
		String serveur = "127.0.0.1";
		try {
			ClientChat client = new ClientChat(port,serveur);
		} catch (UnknownHostException e) {
			System.out.println("Serveur inconnu");
		} catch (IOException e) {
			System.out.println("Erreur de connexion au serveur. Reessayez plus tard.");
		}

	}

}
