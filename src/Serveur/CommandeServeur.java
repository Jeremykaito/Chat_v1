package Serveur;

import java.io.IOException;
import java.util.Scanner;
import Exceptions.SauvegarderChatException;

/**
 * CommandeServeur : thread permettant d'écrire certaines commandes dans le serveur
 * @author Jérémy Ha, Annelyse Nugue
 * @date 13/11/2016
 */
public class CommandeServeur implements Runnable{

	//Variables
	Chat chat;
	Scanner sc;
	String commande="";
	
	/**
	 * @brief Constructeur d'un nouveau topic
	 * @param Chat : le chat du serveur
	 */
	public CommandeServeur(Chat chat){
		this.chat = chat;
		sc = new Scanner(System.in);
		aide();
	}
	
	/**
	 * @brief Traite les commandes serveur
	 */
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
				
				//Nombre d'utilisateurs inscrits
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
	
	/**
	 * @brief Affiche l'aide
	 */
	public void aide(){
		System.out.println("---------------------------------------------");
		System.out.println("Voici les commandes que vous pouvez taper : ");
		System.out.println("Sauvegarder le chat : \"/save\"");
		System.out.println("Nombre d'utilisateurs inscrits : \"/total\"");
		System.out.println("Fermer le serveur : \"/exit\"");
		System.out.println("---------------------------------------------");
	}
		
}

