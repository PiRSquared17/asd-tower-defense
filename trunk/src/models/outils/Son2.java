package models.outils;

import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;

/**
 * Fichier : Son.java
 * <p>
 * Encodage : UTF-8
 * <p>
 * Cette classe interne privee permet de representer du son (flux).
 * <p>
 * Remarques : <br>
 * Cette classe est "threadee", elle permet de lire plusieurs fois la meme
 * musique en concurrence.
 * 
 * @author Pierre-Dominique Putallaz
 * @author Aurelien Da Campo
 * @author Lazhar Farjallah
 * @version 17 dec. 2009
 * @since jdk1.6.0_16
 */
public class Son2 extends Thread
{
    // Le player de son.
    private Player player;
    // Le flux d'entree lie au fichier a jouer.
    private FileInputStream fluxEntreeFichier;
    // Le nombre de fois qu'on veut repeter le son.
    private int nombreRepetitions = 1;
    // Le fichier ou est stocke la musique a lire.
    private File fichier;
    // Pour savoir si le son actuel est à l'arret.
    private boolean arret = false;
    
    private ArrayList<EcouteurDeSon> ecouteursDeSon = new ArrayList<EcouteurDeSon>();

    /**
     * Ce constructeur permet de creer un objet Son en fonction d'un fichier
     * donne ainsi que d'un nombre de repetitions donne.
     * 
     * @param fichier Le fichier a lire.
     * @param repetitions Le nombre de fois que la musique doit etre jouee. Si
     *            cette valeur vaut 0, la musique est repetee a l'infini.
     */
    public Son2(File fichier)
    {
        this.fichier = fichier;
    }

    /**
     * Cette methode permet de lire le son une fois.
     */
    public void lire()
    {
        start();
    }
    
    /**
     * Cette methode permet de lire la musique courante, en fonction du nombre
     * de repetitions donne. Si <tt>nombreRepetitions</tt> vaut 0, on repete la
     * musique $ l'infini, sinon on repete la musique <tt>nombreRepetitions</tt>
     * fois.
     * 
     * @param nombreRepetitions Le nombre de fois que la musique doit etre
     *            jouee. Si ce parametre vaut 0, la musique est jouee a l'infini
     *            (ne s'arrete jamais).
     */
    public void lire(int nombreRepetitions)
    {
        this.nombreRepetitions = nombreRepetitions == 0 ? Integer.MAX_VALUE
                : nombreRepetitions;
        start();
    }

    /**
     * Cette methode permet d'arreter toutes les occurrences de cette musique
     * qui sont en train d'etre lues. En bref, elle stoppe tous les threads en
     * train de lire cette musique.
     */
    public void arreter()
    {
        interrupt();
    }

    /**
     * Cette methode permet de determiner si toutes les occurrences de lecture
     * de la musique courante sont terminees ou non.
     * 
     * @return True si toutes les occurrences sont terminees, false sinon.
     */
    public boolean estTerminee()
    {
        return arret;
    }

    /**
     * Permet de lancer le thread qui lit le son.
     * 
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        try
        {
            // On lit le nombre de fois voulu le son.
            for (int i = 0; !arret && i < nombreRepetitions; ++i)
            {
                // Creation du flux d'entree en fonction du fichier.
                fluxEntreeFichier = new FileInputStream(fichier);
                // Initialisation de l'objet player en fonction du flux.
                player = new Player(fluxEntreeFichier);
                // Lecture de la musique.
                player.play();
            }
            // On arrete le thread courant car le son est termine.
            interrupt();
        } catch (Exception erreur) // Erreur liee a la lecture du son.
        {
            erreur.printStackTrace();
        }
    }

    /**
     * Permet d'interrompre un thread.
     * 
     * @see Thread#interrupt()
     */
    public void interrupt()
    {
        // On passe en mode arret.
        arret = true;
        // On ferme le lecteur de son.
        player.close();

        // informe les ecouteurs que le son est termine
        for(EcouteurDeSon ecouteurDeSon : ecouteursDeSon)
            ecouteurDeSon.estTerminee(this);
    }

    /**
     * Permet d'ajouter un ecouteur de son
     * @param eds l'ecouteur de son
     */
    public void ajouterEcouteurDeSon(EcouteurDeSon eds)
    {
        ecouteursDeSon.add(eds);
    }
    
    /**
     * Permet de recuperer le fichier du son
     * 
     * @return le fichier du son
     */
    public File getFichier()
    {
        return fichier;
    }
}