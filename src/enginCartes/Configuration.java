package enginCartes;

/**
 * Les valeurs de la simulation qui sont modifiables par le panneau de configuration.
 * 
 * @author Pierre Bélisle
 * @version copyright A2022
 */
public class Configuration {

	/*
	 * Stratégie : Un tableau où chaque case représente une des constantes 
	 * de la simulation.  Cela évite de faire une sélection en cours d'exécution avec
	 * un accès directe.
	 */
	
	// Surcharge les constantes de base pour 
	// vérifier la position de la valeur à l'écran.  Sert à modifier le bonne valeur.
	private static final int POS_MAX_X = 0;
	private static final int POS_MAX_Y = 1;

	private static final int POS_NB_VILLES = 2;

	private static final int POS_POURCENT_MUTATION = 3;
	private static final int POS_POURCENT_RETRAIT = 4;

	private static final int POS_PENALITE_DECONNECTE = 5;
	private static final int POS_PENALITE_DISTANCE = 6;
	private static final int POS_PENALITE_LONGUEUR =7;

	private static final int POS_NB_ITERATIONS = 8;

	private static final int POS_NB_CARTES_BASE = 9;
	private static final int POS_NB_CARTES_MAX = 10;	

	private static final int POS_ECHELLE = 11;


	// Tableau qui contient les valeurs modifiables de configuration de la simulation.
	// Cela permet d'éviter des if lors de la modification à l'écran
	public  double[] tabConfig = {
			Constantes.MAX_X,
			Constantes.MAX_Y,

			Constantes.NB_VILLES,
			Constantes.POURCENTAGE_MUTATION,
			Constantes.POURCENTAGE_RETRAIT,

			Constantes.PENALITE_DECONNECTE,
			Constantes.PENALITE_DISTANCE,
			Constantes.PENALITE_LONGUEUR,

			Constantes.NB_ITERATIONS,

			Constantes.NB_CARTES_BASE,
			Constantes.NB_CARTES_MAX,

			Constantes.ECHELLE
	};


	// Les accessseurs.  Je n'ai pas écrit tous les commentaires car 
	// je ne fournit aucun code au tp3.
	public double getMaxX() {

		return tabConfig[POS_MAX_X];
	}


	public double getMaxY() {

		return tabConfig[POS_MAX_Y];
	}


	public double getPourcentMutation() {

		return tabConfig[POS_POURCENT_MUTATION];
	}
	
	public double getPourcentRetrait() {

		return tabConfig[POS_POURCENT_RETRAIT];
	}
	

	public double getPenaliteDeconnecte() {

		return tabConfig[POS_PENALITE_DECONNECTE];
	}


	public double getPenaliteDistance() {

		return tabConfig[POS_PENALITE_DISTANCE];
	}


	public double getPenaliteLongueur() {

		return  tabConfig[POS_PENALITE_LONGUEUR];
	}

	public int getNbIterations() {

		return (int) tabConfig[POS_NB_ITERATIONS];
	}

	public int getNbVilles() {

		return (int) tabConfig[POS_NB_VILLES];
	}


	public int getNbCartesBase() {

		return (int) tabConfig[POS_NB_CARTES_BASE];
	}

	public int getNbCartesMax() {

		return (int) tabConfig[POS_NB_CARTES_MAX];
	}


	public int getEchelle() {

		return (int) tabConfig[POS_ECHELLE];
	}

	public int nbConstantes() {

		return tabConfig.length;
	}


	/*
	 *  Le mutateur qui permet de modifier n'importe quelle constantes
	 *  à partir de sa position dans le tableau.
	 */
	public void setConfig(int numConfig, String valeur) {
		
		tabConfig[numConfig] = Double.parseDouble(valeur);
		
	}
	
	/*
	 * Mutateur des MAX
	 */
	public void setMaxX(int taille) {
		
		tabConfig[POS_MAX_X] = taille;
	}

	public void setMaxY(int taille) {
		
		tabConfig[POS_MAX_Y] = taille;
	}
	
	
// DEBUG
public String toString() {
		
		String str = "";
	
		for(int i = 0; i < tabConfig.length; i++)
				str += tabConfig[i] + "\n";
	
		return str;
	}


}
