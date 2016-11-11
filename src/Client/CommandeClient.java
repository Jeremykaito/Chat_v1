package Client;

import java.io.*;


public class CommandeClient implements Runnable {
	BufferedReader in; // pour gestion du flux d'entr�e (celui de la console)
	String commande=""; // contiendra la commande tap�e
	Thread t; // contiendra le thread

	//** Constructeur : initialise les variables n�cessaires **
	public CommandeClient() {
		// le flux d'entr�e de la console sera g�r� plus pratiquement dans un BufferedReader
		in = new BufferedReader(new InputStreamReader(System.in));
		t = new Thread(this); // instanciation du thread
		t.start(); // demarrage du thread, la fonction run() est ici lanc�e
	}

	//** Methode : attend les commandes dans la console et ex�cute l'action demand�e **
	public void run() // cette m�thode doit obligatoirement �tre impl�ment�e � cause de l'interface Runnable
	{
		try
		{
			while ((commande=in.readLine())!=null)
			{
				if (commande.equalsIgnoreCase("quit")) // commande "quit" detect�e ...
					System.exit(0); // ... on ferme alors le serveur
				else
				{
					// si la commande n'est ni "total", ni "quit", on informe l'utilisateur et on lui donne une aide
					System.out.println("Cette commande n'est pas supportee");
					System.out.println("Quitter : \"quit\"");
					System.out.println("Nombre de connectes : \"total\"");
					System.out.println("--------");
				}
				System.out.flush(); // on affiche tout ce qui est en attente dans le flux
			}
		}
		catch (IOException e) {}
	}
}
