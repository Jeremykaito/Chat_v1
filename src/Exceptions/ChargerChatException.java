package Exceptions;

/**
 * ChargerChatException : exception quand le chargement des données échoue
 * @author Annelyse
 * @date 13/11/2016
 */
public class ChargerChatException extends Exception {
	
	/**
	 * @brief constructeur
	 * @param message : message à afficher quand l'erreur survient
	 */
	public ChargerChatException(String message){
		super(message);
	}
}