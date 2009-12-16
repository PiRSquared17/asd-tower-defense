package models.maillage;

import java.awt.Point;

/**
 * Fichier : Noeud.java
 * <p>
 * Encodage : UTF-8
 * 
 * <p>
 * Cette classe modélise un noeud du graphe. Elle étend la classe Point pour
 * permettre un dessin plus facile et une encapsulation des coordonnées x et y
 * en pixel.
 * <p>
 * Remarques :
 * 
 * @author Pierre-Dominique Putallaz
 * @author Aurélien Da Campo
 * @author Lazhar Farjallah
 * @version 30 nov. 2009
 * @since jdk1.6.0_16
 */
public class Noeud extends Point
{

	// Fanion pour savoir si le noeud est actif ou pas.
	// Actif par defaut, obligatoirement.
	private boolean actif = true;

	/**
	 * @param x
	 *            La coordonnée x du centre du noeud, en pixel.
	 * @param y
	 *            La coordonnée y du centre du noeud, en pixel.
	 * @throws IllegalArgumentException
	 *             Levé si les arguments ne sont pas valides (plus petit que
	 *             zéro).
	 */
	public Noeud(int x, int y) throws IllegalArgumentException
	{
		// Vérification des paramètres
		verifierCoordonnees(x, y);
		// Assignation aux membres de la classe parente.
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructeur de copie à partir d'un autre noeud.
	 * 
	 * @param source
	 *            Le noeud à copie.
	 */
	public Noeud(Noeud source)
	{
		this(source.x, source.y);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "[" + x + ", " + y + "] Actif : " + actif;
	}

	/**
	 * Défini le noeud comme actif, ou pas.
	 * 
	 * @param actif
	 *            True si le noeud est actif, False sinon.
	 */
	public void setActif(boolean actif)
	{
		this.actif = actif;
	}

	/**
	 * Retourne True si le noeud est actif, False sinon.
	 * 
	 * @return True si le noeud est actif, False sinon.
	 */
	public boolean isActif()
	{
		return actif;
	}

	/**
	 * Méthode de service pour tester si les coordonnées passées sont valides.
	 * 
	 * @param x
	 *            La coordonnée x.
	 * @param y
	 *            La coordonnée y.
	 * @throws IllegalArgumentException
	 *             Levé si y ou x sont plus petits que zéro.
	 */
	private static void verifierCoordonnees(int x, int y)
			throws IllegalArgumentException
	{
		if (x < 0)
			throw new IllegalArgumentException("La coordonnée x est négative!");

		if (y < 0)
			throw new IllegalArgumentException("La coordonnée y est négative!");

	}

}