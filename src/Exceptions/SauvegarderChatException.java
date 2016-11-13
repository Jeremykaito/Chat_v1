package Exceptions;

/**
 * SauvegarderChatException : exception quand le chargement des donn�es �choue
 * @author Annelyse
 * @date 13/11/2016
 */
public class SauvegarderChatException extends Exception {
	
	/**
	 * @brief constructeur
	 * @param message : message � afficher quand l'erreur survient
	 */
	public SauvegarderChatException(String message){
		super(message);
	}
	
}
