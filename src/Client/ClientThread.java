package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Serveur.Chat;


public class ClientThread implements Runnable{

	private Socket communication;
	private Chat chat;
	private final ArrayList<ClientThread> threads;
	String nom;

	public ClientThread(Socket com, Chat chat, ArrayList<ClientThread> threads){
		this.communication=com;
		this.chat=chat;
		this.threads=threads;
	}

	@Override
	public synchronized void run() {
		try{
			DataInputStream reception = new DataInputStream(communication.getInputStream());
			DataOutputStream emission = new DataOutputStream(communication.getOutputStream());
			String choix="";
			String pseudo="";
			String mdp="";
			String titre="";
			String description="";
			String message="";
			boolean rep;
			
			ArrayList<ClientThread> threads = this.threads;

			while(true){
				choix = reception.readUTF();
				switch(choix) {

				case "connexion":
					pseudo = reception.readUTF();
					mdp = reception.readUTF();
					rep = chat.connexion(pseudo,mdp);
					if (rep) this.nom = pseudo;
					emission.writeBoolean(rep);
					break;

				case "inscription":
					pseudo = reception.readUTF();
					mdp = reception.readUTF();
					rep = chat.creationUtilisateur(pseudo,mdp);
					emission.writeBoolean(rep);
					break;

				case "rejoindreTopic" :
					titre = reception.readUTF();
					rep = chat.rejoindreTopic(titre);
					emission.writeBoolean(rep);
					break;

				case "creationTopic" :
					titre = reception.readUTF();
					description = reception.readUTF();
					rep = chat.creationTopic(titre,description);
					emission.writeBoolean(rep);
					break;

				case "topic" :
					titre = reception.readUTF();
					String messages = chat.getTopic(titre);
					
					emission.writeUTF(messages);
					break;

				case "listTopics" :
					emission.writeUTF(chat.toStringUsers());
					break;
					
				case "parler" :
					message = reception.readUTF();
					titre = reception.readUTF();
					chat.addMessage(titre,message);
					break;

				case "quitter":
					break;
				default :
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
