package serveur.jeu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import reseau.Canal;
import reseau.Port;

/**
 * Cette classe contiendra le serveur de jeu sur lequel se connecteront tout les
 * cliens.
 * 
 * @author Pierre-Do
 * 
 */
public class ServeurJeu
{
	/**
	 * La version courante du serveur
	 */
	public static final String VERSION = "0.1";
	
	/**
	 * Le port sur lequel le serveur écoute par defaut
	 */
	public final static int _port = 2357;
	
	private static Port port;
	private static final boolean DEBUG = true;
	private HashMap<Integer, JoueurDistant> clients = new HashMap<Integer, JoueurDistant>();

	/**
	 * Le numéros unique d'authentification des clients
	 */
	private static int IDClient = -1;

	/**
	 * Méthode MAIN : entrée dans le programme
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("Lancement du serveur sur le port " + _port);
		try
		{
			ServeurJeu serveur = new ServeurJeu();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public ServeurJeu() throws IOException
	{
		port = new Port(_port);
		port.reserver();
		Canal canal;
		// Boucle d'attente de connections
		while (true)
		{
			// On attend qu'un joueur se présente
			log("écoute sur le port " + _port);
			canal = new Canal(port, DEBUG);
			log("Récéption de " + canal.getIpClient());
			IDClient++;
			// On inscris le joueur à la partie
			clients.put(IDClient, new JoueurDistant(IDClient, canal, this));
		}
	}

	/**
	 * Envoi un message texte à l'ensemble des clients connectés.
	 * 
	 * @param IDFrom
	 *            L'ID de l'expéditeur.
	 * @param message
	 *            Le message à envoyer.
	 */
	public void direATous(int IDFrom, String message)
	{
		for (Entry<Integer, JoueurDistant> joueur : clients.entrySet())
			joueur.getValue().envoyerMessageTexte(IDFrom, message);
	}

	/**
	 * Envoi un message texte à un client en particulier.
	 * 
	 * @param IDFrom
	 *            L'ID de l'expéditeur
	 * @param IDTo
	 *            L'ID du destinataire
	 * @param message
	 *            Le message à envoyer.
	 */
	public void direAuClient(int IDFrom, int IDTo, String message)
	{
		clients.get(IDTo).envoyerMessageTexte(IDFrom, message);
	}

	/**
	 * Affiche toutes les informations de tous les clients connectés.
	 */
	public void infos(){
		System.out.println("Serveur de jeu");
		System.out.println("Nombre de joueurs : "+clients.size());
		for (Entry<Integer, JoueurDistant> joueur : clients.entrySet())
			System.out.println(joueur.getValue());
	}
	
	protected static void log(String msg)
	{
		System.out.print("[SERVEUR] ");
		System.out.println(msg);
	}
}