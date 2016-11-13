package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import Serveur.Utilisateur;


public class ClientReadingThread implements Runnable{

	private Socket communication;
	private DataOutputStream emission;
	private DataInputStream reception;
	private Scanner commande;
	private boolean ouvert;

	public ClientReadingThread(int port, String serveur) throws UnknownHostException, IOException{
		// Activation d'une communication avec le serveur
		communication = new Socket(serveur, port);
		// Creation des flux sortant et entrant
		emission = new DataOutputStream(communication.getOutputStream());
		reception = new DataInputStream(communication.getInputStream());

		//Creation de l'entr�e clavier
		commande = new Scanner(System.in);
		ouvert = true;
	}

	public void run() {
		String responseLine;
		try {
			while ((responseLine = reception.readUTF()) != null) {
				System.out.println(responseLine);
				if (responseLine.indexOf("*** Bye") != -1)
					break;
			}
			ouvert = false;
		} catch (IOException e) {
			System.err.println("IOException:  " + e);
		}
		if (communication != null && emission != null && reception != null) {
			try {
				while (ouvert) {
					emission.writeUTF(commande.next());
				}

				emission.close();
				reception.close();
				communication.close();
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
	}
}