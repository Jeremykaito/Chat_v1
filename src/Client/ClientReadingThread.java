package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import Serveur.Utilisateur;


public class ClientReadingThread implements Runnable{

 private Socket communication;
 private DataOutputStream emission;
 private DataInputStream reception;
 private Scanner commande;
 private boolean closed = false;

 public ClientReadingThread(int port, String serveur) throws UnknownHostException, IOException{
  // Activation d'une communication avec le serveur
  communication = new Socket(serveur, port);
  // Creation des flux sortant et entrant
  emission = new DataOutputStream(communication.getOutputStream());
  reception = new DataInputStream(communication.getInputStream());

  //Creation de l'entrée clavier
  commande = new Scanner(System.in);
 }

 public void run() {
  String responseLine;
  try {
   while ((responseLine = reception.readUTF()) != null) {
    System.out.println(responseLine);
    if (responseLine.indexOf("*** Bye") != -1)
     break;
   }
   closed = true;
  } catch (IOException e) {
   System.err.println("IOException:  " + e);
  }
 }

}