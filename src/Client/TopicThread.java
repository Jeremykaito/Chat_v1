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

	public synchronized void run() {
		try {
			// Creation des flux sortant et entrant
			emission = new DataOutputStream(communication.getOutputStream());
			reception = new DataInputStream(communication.getInputStream());
			while (true) {
				Thread.sleep(5000);
				emission.writeUTF("topic");
				emission.writeUTF(title);
				String discussion = reception.readUTF();
				System.out.println(discussion);
			}
		}
		catch (IOException | InterruptedException e) {}
		/*
		try {
			while ((commande=in.readLine())!=null) {
				if (commande.equalsIgnoreCase("/Q")){

				}

				else {

				}
			}
		}
		catch (IOException e) {}*/
	}
}
