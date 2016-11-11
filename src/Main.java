import java.io.FileNotFoundException;
import java.util.Scanner;

import Serveur.Chat;

// utiliser de variable fantome de zynchroniation ex : private final object sync
public class Main {

	public static void main(String[] args) {

		Chat fofo = null;
		try {
			fofo = new Chat();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner sc = new Scanner(System.in);
		String choix = "";

		accueil(sc,fofo,choix);

	}

	public static void accueil(Scanner sc, Chat fofo, String choix) {
		do{
			System.out.println("Bienvenue");
			System.out.println("1 - Se connecter");
			System.out.println("2 - S'inscrire ");
			System.out.println("3 - Quitter ");
			System.out.println("Que voulez-vous faire ?");

			choix = sc.next();

			switch(choix) {

			case "1":
				connexion(sc, fofo);
				break;

			case "2":
				inscription(sc, fofo);
				break;

			case "3":
				System.out.println("Au revoir !");
				System.exit(0);

			default :
				break;
			}
		}while(true);
	}


	public static void connexion(Scanner sc, Chat fofo) {
		System.out.println("Veuillez entrer votre pseudo :");
		String pseudo = sc.next();

		System.out.println("Veuillez entrer votre mdp :");
		String mdp = sc.next();	

		if(fofo.connexion(pseudo, mdp)) {
			System.out.println("Connexion réussie !");
			chat_accueil(sc, fofo);
		}
		else System.out.println("Erreur d'authentification !");
	}

	private static void inscription(Scanner sc, Chat fofo) {
		String pseudo=null;
		String mdp=null;
		String mdp_confirm=null;
		boolean valide = false;
		do{
			System.out.println("Veuillez entrer un nouveau pseudo :");
			pseudo = sc.next();
			System.out.println("Veuillez entrer un mdp :");
			mdp = sc.next();	
			System.out.println("Veuillez entrer une nouvelle fois votre mdp pour confirmer :");
			mdp_confirm = sc.next();

			if(mdp.equals(mdp_confirm)) {
				if(fofo.creationUtilisateur(pseudo, mdp)) {
					System.out.println("Création réussie !");
					valide = true;
				}
				else System.out.println("Désolé ce nom est déjà pris !");
			}
			else System.out.println("Erreur mot de passe non validé");	

		}while(!valide);
	}

	public static void chat_accueil(Scanner sc, Chat fofo){

		boolean ouvert = true;
		String choix = "";

		do{
			System.out.println(fofo.toString());
			System.out.println("1 - Rejoindre");
			System.out.println("2 - Créer un topic");
			System.out.println("3 - Quitter ");
			System.out.println("Que voulez-vous faire ?");
			choix = sc.next();
			switch(choix) {

			case "1":
				rejoindre(sc, fofo);
				break;

			case "2":
				creationTopic(sc, fofo);
				break;

			case "3":
				System.out.println("Déconnexion...");
				ouvert = false;
				break;

			default :
				break;
			}

		}while(ouvert);

	}

	private static void creationTopic(Scanner sc, Chat fofo) {
		System.out.println("Entrez le nom : ");
		String nom = sc.next();
		System.out.println("Entrez une description");
		String description = sc.next();


	}

	private static void rejoindre(Scanner sc, Chat fofo) {
		// TODO Auto-generated method stub

	}
}
