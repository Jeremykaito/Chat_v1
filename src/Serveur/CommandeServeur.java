package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Exceptions.SauvegarderChatException;

public class CommandeServeur implements Runnable{

	Chat chat;
	BufferedReader in; // pour gestion du flux d'entr�e (celui de la console)
	String commande=""; // contiendra la commande tap�e
	Thread t; // contiendra le thread
	
	public CommandeServeur(Chat chat){
		this.chat = chat;
		// le flux d'entr�e de la console sera g�r� plus pratiquement dans un BufferedReader
		in = new BufferedReader(new InputStreamReader(System.in));
		t = new Thread(this); // instanciation du thread
		t.start(); // demarrage du thread, la fonction run() est ici lanc�e
		aide();
	}
	@Override
	public void run() {
		
		try
		{
			while ((commande=in.readLine())!=null)
			{
				if (commande.equalsIgnoreCase("save")) // commande "save" detect�e ...
					this.chat.sauvegarderChat(); // ... on sauvegarde le chat
				else if (commande.equalsIgnoreCase("total")) // commande "total" detect�e ...
					System.out.println(this.chat.getNbConnectedUsers()+ " sont connect�s actuellement."); // ... on affiche le nombre d'utilisateurs connect�s
				else if (commande.equalsIgnoreCase("exit")) // commande "exit" detect�e ...
					System.exit(0); // ... on ferme alors le serveur
				else
				{
					// si la commande n'existe pas, on informe l'utilisateur et on lui donne une aide
					System.out.println("Cette commande n'est pas supportee");
					aide();
				}
				System.out.flush(); // on affiche tout ce qui est en attente dans le flux
			}
		}
		catch (IOException | SauvegarderChatException e) {}
	}
	
	public void aide(){
		System.out.println("---------------------------------------------");
		System.out.println("Voici les commandes que vous pouvez taper : ");
		System.out.println("Sauvegarder : \"save\"");
		System.out.println("Nombre d'utilisateurs connect�s : \"total\"");
		System.out.println("Fermer le serveur : \"exit\"");
		System.out.println("---------------------------------------------");
	}
		
}

