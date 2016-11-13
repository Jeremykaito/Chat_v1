package Serveur;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import Exceptions.SauvegarderChatException;

public class CommandeServeur implements Runnable{

	//Déclaration des variables
	Chat chat;
	Scanner sc;
	String commande="";
	
	public CommandeServeur(Chat chat){
		this.chat = chat;
		sc = new Scanner(System.in);
		aide();
	}
	
	@Override
	public void run() {
		
		try
		{
			while ((commande=sc.next())!=null)
			{
				//Sauvegarde du chat
				if (commande.equalsIgnoreCase("/save")){
					this.chat.sauvegarderChat();
					System.out.println("Sauvegarde du chat réussie.");
				}
				else if (commande.equalsIgnoreCase("/total"))
					System.out.println(this.chat.getNbUsers()+ " personne(s) inscrite(s).");
				
				//On ferme le serveur
				else if (commande.equalsIgnoreCase("/exit"))
					System.exit(0);
				
				//La commande n'existe pas
				else
				{
					System.out.println("Cette commande n'est pas supportee");
					aide();
				}
				System.out.flush();
			}
		}
		catch (IOException | SauvegarderChatException e) {}
	}
	
	public void aide(){
		System.out.println("---------------------------------------------");
		System.out.println("Voici les commandes que vous pouvez taper : ");
		System.out.println("Sauvegarder le chat : \"/save\"");
		System.out.println("Nombre d'utilisateurs inscrits : \"/total\"");
		System.out.println("Fermer le serveur : \"/exit\"");
		System.out.println("---------------------------------------------");
	}
		
}

