package models.tours;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import models.creatures.Creature;

/**
 * Classe de gestion d'une tour de glace.
 * Cette classe derive de Tour.
 * 
 * @author Pierre-Dominique Putallaz
 * @author Aurélien Da Campo
 * @author Lazhar Farjallah
 * @version 1.0 | 27 novemenbre 2009
 * @since jdk1.6.0_16
 * @see Tour
 */
public class TourDeGlace extends Tour
{
	private static final long serialVersionUID = 1L;
	public static final Color COULEUR;
	public static final Image IMAGE;
	public static final int NIVEAU_MAX = 4;
	
	static
	{
		COULEUR = Color.BLUE;
		IMAGE 	= Toolkit.getDefaultToolkit().getImage("img/tours/basic_tower_3.png");
	}
	
	public TourDeGlace()
	{
		super(0, 				// x
			  0, 				// y
			  20, 				// largeur
		      20, 				// hauteur
			  COULEUR,			// couleur de fond
			  "Tour de glace",	// nom
			  20,				// prix achat
			  40,				// degats
			  40,				// rayon de portee
			  IMAGE);				
		
		description = "La tour de glace est une \ntour rapide qui ralenti les ennemis";
	}

	public void ameliorer()
	{
		// le prix total est ajouté du prix d'achat de la tour
		prixTotal 	+= prixAchat;
		
		prixAchat 	*= 2;	// + 100%
		degats    	*= 1.5; // + 50%
		rayonPortee *= 1.2; // + 20%
		
		niveau++;
	}
	
	public void tirer(Creature creature)
	{
		// terrain.ajouteTire(new bouleDeGlace(this,creature));
		creature.blesser(degats);
	}


	public Tour getCopieOriginale()
	{
		return new TourDeGlace();
	}
	
	public boolean peutEncoreEtreAmelioree()
	{
		return niveau <= NIVEAU_MAX;
	}
}
