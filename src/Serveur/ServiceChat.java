package Serveur;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ServiceChat implements Runnable{

	private Socket communication;
	private Chat chat;

	public ServiceChat(Socket com, Chat chat){
		this.communication=com;
		this.chat=chat;
	}

	@Override
	public synchronized void run() {
		try{
			DataInputStream reception = new DataInputStream(communication.getInputStream());
			DataOutputStream emission = new DataOutputStream(communication.getOutputStream());
			String choix = reception.readUTF();
			String pseudo;
			String mdp;
			String titre;
			String description;
			boolean rep;
			
			while(true){
				switch(choix) {

				case "connexion":
					pseudo = reception.readUTF();
					mdp = reception.readUTF();
					rep = chat.connexion(pseudo,mdp);
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
					
				case "CreationTopic" :
					titre = reception.readUTF();
					description = reception.readUTF();
					rep = chat.creationTopic(titre,description);
					emission.writeBoolean(rep);
					break;
					
				case "topic" :
					System.out.println(reception.readUTF());
					System.out.println(reception.readUTF());
			
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
