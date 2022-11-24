package enginCartes;

/**
 * Les constantes de départ de la simulation.
 * 
 * @author Pierre Bélisle
 * @version copyright A2022
 */
public class Constantes {

	public static final int NB_VILLES = 10;

	public static final double POURCENTAGE_MUTATION = 0.5;
	public static final double POURCENTAGE_RETRAIT = 0.5;
	
	public static final double MAX_X = 3000.0;
	public static final double MAX_Y = MAX_X;
	
	public static final double PENALITE_LONGUEUR = (double)(2000.0/MAX_X); //20.0
	public static final double PENALITE_DISTANCE = (double)(100.0/MAX_X); //1

	public static final int PENALITE_DECONNECTE = 1000;
	public static final int NB_ITERATIONS = 1000;

	public static final int NB_CARTES_BASE = 20;
	public static final int NB_CARTES_MAX = 60;

	// Ajout tp3.
	public static final int ECHELLE = 1;


}
