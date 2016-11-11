package Exceptions;
/**
 * ChargerChatException : exception quand le chargement des données échoue
 * @author Annelyse
 * @date 22/09/2016
 */

public class ChargerChatException extends Exception {
	
	/**
	 * @brief constructor
	 * @param message : message to display when the exception occurs
	 */
	public ChargerChatException(String message){
		super(message);
	}
}