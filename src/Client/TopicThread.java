package Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class TopicThread implements Runnable {
	private Socket communication;
	private DataOutputStream emission;
	private DataInputStream reception;
	private String commande; 
	private String title;

	public TopicThread(String title, Socket communication) {
		// Activation d'une communication avec le serveur
		this.communication = communication;
		this.title = title;
		//Lancement d'un nouveau thread
		new Thread(this).start();

	}

	public void run() {
		try {
			// Creation des flux sortant et entrant
			emission = new DataOutputStream(communication.getOutputStream());
			reception = new DataInputStream(communication.getInputStream());

			emission.writeUTF("topic");
			emission.writeUTF(title);
			System.out.println(reception.readUTF());
		}
		catch (IOException e) {}
		/*
		try {
			while ((commande=in.readLine())!=null) {
				if (commande.equalsIgnoreCase("/Q")){

				}

				else {
					// si la commande n'est ni "total", ni "quit", on informe l'utilisateur et on lui donne une aide
					System.out.println("Cette commande n'est pas supportee");
					System.out.println("Quitter : \"quit\"");
					System.out.println("Nombre de connectes : \"total\"");
					System.out.println("--------");
				}
				System.out.flush(); // on affiche tout ce qui est en attente dans le flux
			}
		}
		catch (IOException e) {}*/
	}
}
