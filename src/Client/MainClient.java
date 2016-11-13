package Client;
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * MainClient : thread côté client qui communique avec le serveur
 * @author Jérémy Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class MainClient implements Runnable {

	//Variables de communication
	private static Socket communication;
	private static DataOutputStream emission;
	private static DataInputStream reception;
	private static Scanner commande;
	
	//Variables diverses
	private static boolean ouvert;
	private static int portNumber;
	private static String host;

	/**
	 * @brief méthode main du client
	 * @param String[] : tableau d'arguments
	 */
	public static void main(String[] args) throws UnknownHostException {

		//Initialisation
		portNumber = 2002;
		host = "localhost";
		commande = new Scanner(System.in);
		ouvert= true;

		try {
			
			//Création de la socket et des flux
			communication = new Socket(host, portNumber);
			emission = new DataOutputStream(communication.getOutputStream());
			reception = new DataInputStream(communication.getInputStream());
			
			//On démarre un nouveau thread pour afficher les messages du serveur
			new Thread(new MainClient()).start();
			
			//On envoie au serveur le texte écrit par l'utilisateur 
			while (ouvert) {
				emission.writeUTF(commande.nextLine().trim());
			}
			
			//On ferme la socket et les flux
			emission.close();
			reception.close();
			communication.close();
		}
		catch (IOException e) {
				e.printStackTrace();
		}

	}

	/**
	 * @brief méthode run du thread qui affiche les messages du serveur
	 */
	@Override
	public void run() {

		String messageServeur;
		
		//Affichage des messages en provenance du serveur
		try {
			while ((messageServeur = reception.readUTF()) != null) {
				
				if(messageServeur.equalsIgnoreCase("/quit")){
					System.exit(0);
				}
				
				else System.out.println(messageServeur);
			}
			ouvert = true;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}