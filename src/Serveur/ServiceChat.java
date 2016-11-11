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
			DataInputStream dis = new DataInputStream(communication.getInputStream());
			DataOutputStream dos = new DataOutputStream(communication.getOutputStream());
			String choix = dis.readUTF();
			String pseudo;
			String mdp;
			String titre;
			boolean rep;
			
			while(true){
				switch(choix) {

				case "connexion":
					pseudo = dis.readUTF();
					mdp = dis.readUTF();
					rep = chat.connexion(pseudo,mdp);
					dos.writeBoolean(rep);
					break;

				case "inscription":
					pseudo = dis.readUTF();
					mdp = dis.readUTF();
					rep = chat.creationUtilisateur(pseudo,mdp);
					dos.writeBoolean(rep);
					break;
					
				case "rejoindreTopic" :
					titre = dis.readUTF();
					rep = chat.rejoindreTopic(titre);
					dos.writeBoolean(rep);
					break;
					
				case "topic" :
					System.out.println(dis.readUTF());
					System.out.println(dis.readUTF());
					break;
					
				case "listTopics" :
					dos.writeUTF(chat.getTopics());
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
