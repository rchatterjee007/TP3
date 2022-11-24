package enginCartes;

/**
 * Cette classe Implemente le moteur de gestions de la population de cartes candidates
 * 
 * Liste des méthodes publiques: 
 *     - MoteurCartes, constructeur par paramètre
 *     - reduitLaPopulation, opération qui réduit la population à NB_CARTES_BASES
 *     - elargieLaPopulation, opération qui elargie la population à NB_CARTES_MAX
 *     - evalueLesScores, calcul les scores des cartes
 *     - afficheMeilleurSolution, affiche la meilleur solution
 *     - afficheMeilleurScore, affiche le meilleur score
 *     - toString, représentation de l'objet en chaine de caractère
 *
 * @author Fred Simard | ETS, 
 * @version Automne 2022
 */

import java.util.Random;
import java.util.Vector;

import listeChainee.ListeDChainee;
import problemeVilles.PopulationVilles;
import problemeVilles.Ville;

public class MoteurCartes {

	// La population reçue par le constructeur.
		private PopulationVilles popVilles;
		
		// Les cartes de liens.
		private Vector<Carte> cartes;	
		
		private Random rand = new Random();
		
		// Moteur de calcul des distances.
		private MoteurDistanceMoyenne moteurDistanceMoyenne;
	
		// On conserve la configuration reçue au constructeur.
	    private Configuration config;
	
	/**
	 * Constructeur par paramètre
	 * 
	 * @param popVilles, la population de villes
	 */
	public MoteurCartes(PopulationVilles popVilles, Configuration config){
		
		this.config = config;
				
		setPopulation(popVilles);
	}
	

	/**
	 * Réduit la population de cartes en ne gardant que les 
	 * cartes de base ayant le plus bas score (minimisation). Cette
	 * méthode opère sur les champs de la classe.
	 */
	public void reduitLaPopulation(){
	
		// Crée un nouveau vecteur qui ne contiendra que les meilleurs.
		Vector<Carte> meilleurCartes = new Vector<Carte>();
		
		// Ajoute la première carte, puisque seule candidat.
		meilleurCartes.add(cartes.remove(0));
		
		// Continue tant qu'il reste des cartes à traiter.
		while(cartes.size()>0){
			
			// Obtient la prochaine carte.
			Carte cetteCarte = cartes.remove(0);
			
			// Vérifie si à placer au début.
			if(cetteCarte.getScore() < meilleurCartes.firstElement().getScore()){
				meilleurCartes.add(0, cetteCarte);

			// Vérifie si à placer à la fin.
			}else if(cetteCarte.getScore() > meilleurCartes.lastElement().getScore()){
				meilleurCartes.add(cetteCarte);
				
			// Sinon trouve sa place.
			}else if(cetteCarte.getScore() < meilleurCartes.lastElement().getScore()){
				
				// Part de la fin et remonte vers le début, jusqu'à avoir trouvé la place.
				int index = (meilleurCartes.size()-1);
				while(cetteCarte.getScore() < meilleurCartes.get(index).getScore()){
					index-=1;
				}

				meilleurCartes.add(index+1, cetteCarte);
			}
			
			// Vérifie si on a trop de carte, si c'est le cas, enlève la dernière.
			if(meilleurCartes.size() > config.getNbCartesBase()){
				meilleurCartes.remove((meilleurCartes.size()-1));
			}
			
		}

		// Remplace la population de cartes par la nouvelle population réduite.
		cartes = meilleurCartes;
	
	}

	/**
	 * Élargit la population de cartes en générant de nouvelles cartes qui sont des mixtes de 
	 * cartes existantes. Cette méthode opère sur les champs de la classe.
	 */
	public void elargitLaPopulation(){

		// Élargit la population en générant de nouvelles cartes, qui combinent les gênes
		// des parents.

		// Calcule la somme des scores de tous les parents.
		double sommeScore = obtenirSommeScore();

		// Pour toutes les cartes à générer.
		for(int i=0;
				i<(config.getNbCartesMax()- config.getNbCartesBase())
						;i++){

			// Sélectionne 2 coupes de parents aléatoirement.
			ListeDChainee section1 = obtientUneCoupe(sommeScore);
			ListeDChainee section2 = obtientUneCoupe(sommeScore);

			// Assemble et ajoute la nouvelle carte.
			cartes.add(new Carte(moteurDistanceMoyenne, section1, section2));
		}

	}

	/*
	 * Fonction locale qui reyourne la somme des scores. 
	 */
	private double obtenirSommeScore() {
		
				// Calcule la somme des scores de tous les parents.
				double sommeScore = 0.0;

				for(int i=0;i<cartes.size();i++){
					sommeScore += cartes.get(i).getScore();
				}
				
				return sommeScore;
	}

	/**
	 * Obtient une section de carte selectionnée au hasard parmis la liste de cartes
	 * en proportion de leur score.
	 *
	 * NOTE: la technique employée est de donner plus de poids aux cartes de base ayant 
	 * le plus mauvais  score. Cette approche augmente le mélange des éléments et aide à 
	 * sortir des minimum locaux.
	 *
	 * NOTE: la technique tend également à favoriser les solutions courtes en applicant un
	 *  retrait de liens.
	 *
	 * @param sommeScore somme des scores de la population de cartes.
	 * @return Une section de carte (Liste).
	 */
	private ListeDChainee obtientUneCoupe(double sommeScore){

		// On prend une carte en proportion du score.
		Carte carte = selectionnerCarte(sommeScore);

		// Obtient une fraction de la liste.
		ListeDChainee section = 
				carte.obtientFraction(rand.nextBoolean(), rand.nextInt(carte.getNbLiens()));

		// Applique une mutation si nécessaire.
		if(rand.nextDouble() < config.getPourcentMutation()){
			
			effectuerMutation(section);
		}

		// Retire un lien au hasard pour favoriser les solutions courtes.
		if(rand.nextDouble() < config.getPourcentRetrait()){
			
			int indexLien = rand.nextInt(section.getNbElements());
			
			section.enleverIndex(indexLien);
		}

		return section;

	}
	
	/*
	 * Procédure locale pour effectuer une mutation à un lien.
	 */
	private void effectuerMutation(ListeDChainee section) {
		
		int indexLien = rand.nextInt(section.getNbElements());
		
		section.deplacerAIndex(indexLien);
		
		// Pour la lisibilité du code.
		Lien lien = ((Lien)section.getElement()); 
		
		int aleaVille = rand.nextInt(popVilles.getNbVille());
		int laquelle = rand.nextInt(2);
		
		lien.mute(popVilles.getVille(aleaVille), laquelle);
	}
	
	/*
	 * Fonction locale qui retourne une carte en proportion du score.  
	 */
	private Carte selectionnerCarte(double sommeScore) {
		
		// Itérateur.
		int i=0;
		
		// Un score au hasard pour commencer.
		double nbAlea = rand.nextDouble()*sommeScore;
			
		// Carte à retourner..
		Carte carte = null;
		
		// Le total des scores.
		double accumulationScore = 0;
		
		// Sélectionne en proportion du score.
		while(i<config.getNbCartesBase() && accumulationScore<=nbAlea){

			carte = cartes.get(i);
			accumulationScore += carte.getScore();

			i++;
		}		
		
		return carte;
	}
	
	/**
	 * Calcul les scores des cartes
	 */
	public void evalueLesScores(){

		// evalue les scores pour chaque individus
    	for(int i=0;i<cartes.size();i++){
    		
        	//System.out.println("Carte: " + i + "------------------------------------");
    		cartes.get(i).evalueScore(config, false);
    	}
	}

	/**
	 * Affiche la meilleur solution
	 */
	public Carte getMeilleurSolution(){
		return cartes.get(0);
	}

	/**
	 * Affiche le meilleur score
	 */
	public void afficheMeilleurScore(){
		System.out.println("Meilleur score: " + cartes.get(0).getScore());
		
	}
	
	/**
	 * Retourne une représentation chaine de caractère de l'objet
	 * 
	 *  @return Chaine de caractère représentant l'objet
	 */
	public String toString(){
    	
		String str = new String();
    	str += "Liste des Cartes\n";
    	
    	// affiche toutes les cartes
    	for(int i=0;i<cartes.size();i++){
    		str += "Carte: " + i + "------------------------------------";
    		str += cartes.get(i).toString();
    		str += "\n";
    	}
    	return str;
	}


	public void setPopulation(PopulationVilles popVilles) {
	
		this.popVilles = popVilles;

		// Évite pls appels à l'accesseur.
		int nbCartesBase = config.getNbCartesBase();

		// population de cartes
		cartes =
				new Vector<Carte>(nbCartesBase);

		// instancie un moteur de distance moyenne
		moteurDistanceMoyenne = new MoteurDistanceMoyenne(popVilles);

		// crée la population de cartes initiales
		for(int i=0; i < nbCartesBase; i++){

			// crée une nouvelle carte
			Carte unCarte = new Carte(moteurDistanceMoyenne, config);

			// selectionne 2 villes différentes au hasard
			for(int j=0;j<popVilles.getNbVille()/2;j++){
				
				Ville villeA = popVilles.getVille(rand.nextInt(popVilles.getNbVille()));
				Ville villeB = popVilles.getVille(rand.nextInt(popVilles.getNbVille()));

				while(villeA == villeB){
					villeB = popVilles.getVille(rand.nextInt(popVilles.getNbVille()));
				}

				unCarte.ajouterLien(new Lien(villeA,villeB));
			}

			// ajoute la carte à la liste
			cartes.addElement(unCarte);
		}

	}	
}