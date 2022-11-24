package enginCartes;

/**
 * Cette classe implémente une proposition de carte de liens liant 
 * les villes du problème. 
 * 
 * 
 * Liste des méthodes publiques: 
 *     - Carte, constructeur avec liste vide.
 *     - Carte, constructeur à partir de sections de listes.
 *     - ajouterLien, méthode permettant d'ajouter un lien.
 *     - enleverLien, méthode permettant d'enlever un lien à l'index.
 *     - obtientFraction, obtient une fraction de la liste (deep copy)
 *     - getNbLiens, méthode pour obtenir le nombre de liens..
 *     - getScore, retourne le score.
 *     - evalueScore, calcule le score de la carte.
 *     - calculSommeLongueurs, calcul la somme des longeurs.
 *     - toString, obtient une représentation chaîne de caractères de l'objet.
 *
 * @author Fred Simard | ETS,
 *  
 * @revision Pierre Bélisle | ETS
 * 
 *              principalement : Mettre le code sur 80 colonne max.
 *                               Aération.
 *                               Correction orthographique. 
 * 
 * @version A2022
 */

import listeChainee.ListeDChainee;
import problemeVilles.Ville;

public class Carte{
    
    // Liste chainee des liens.
	private ListeDChainee listeLiens = new ListeDChainee();
	
	
	/**
	 * @return the listeLiens
	 */
	public ListeDChainee getListeLiens() {
		return listeLiens;
	}

	double score = Double.POSITIVE_INFINITY;
	
	MoteurDistanceMoyenne moteurDistanceMoyenne;

	/**
	 * Constructeur qui reçoit le moteur de distance et initialise avec une
	 * liste de liens vide.
	 * 
	 * @param moteurDistanceMoyenne, reference au moteur de calcul de la 
	 * n                             distance moyenne
	 */
	public Carte(MoteurDistanceMoyenne moteurDistanceMoyenne, Configuration config){

		this.moteurDistanceMoyenne = moteurDistanceMoyenne;
	}

	/**
	 * Constructeur qui reçoit le moteur de distance et des segments de listes 
	 * pour initialiser la liste
	 * 
	 * @param moteurDistanceMoyenne, reference au moteur de calcul de  la 
	 *                               distance moyenne
	 * @param section1, premiere section de liste
	 * @param section2, deuxieme section de liste
	 */
	public Carte(MoteurDistanceMoyenne moteurDistanceMoyenne, 
			     ListeDChainee section1, 
			     ListeDChainee section2){
		
				
		// Copie la référence au moteur de calcul distance.
		this.moteurDistanceMoyenne = moteurDistanceMoyenne;
		
		// Démarre à partir de la premiere section.
		listeLiens = section1;
		
		section2.deplacerDebut();
		
		for(int i=0;i<section2.getNbElements();i++){
			
			listeLiens.ajouterFin(section2.getElement());
			section2.deplacerSuivant();
		}
	}

	/**
	 * Méthode permettant d'ajouter un lien à la fin.
	 * 
	 * @param ceLien, lien à ajouter
	 */
	public void ajouterLien(Lien ceLien){
		listeLiens.ajouterFin(ceLien);
	}

	/**
	 * Méthode permettant d'enlever un lien à l'index.
	 * 
	 * @param index, index où enlever le lien
	 */
	public void enleverLien(int index){
		listeLiens.enleverIndex(index);
	}
	

	/**
	 * Méthode permettant d'obtenir une fraction de la liste. 
	 * La fraction est construite par deep copy.
	 * 
	 * @param duDebut, Valeur booléenne qui indique si la section du début 
	 *                 doit être retournée.  Autrement, c'est la fin qui 
	 *                 est retournée. 
	 *                 
	 * @param indexCoupe, Index de coupe.
	 * @return Segment Celui de la liste selectionnée.
	 */
    public ListeDChainee obtientFraction(boolean duDebut, int indexCoupe){
        
        // crée une nouvelle liste
    	ListeDChainee listeDuplicat = new ListeDChainee();

        // si du debut
        if(duDebut){
            
            // Copie de 0 à l'index.
            listeLiens.deplacerDebut();
            listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());
            
            for(int i=0;i<indexCoupe;i++){
                listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());
            }
            
        }else{
            
            // Se place à l'index et copie jusqu'à la fin.            
            listeLiens.deplacerAIndex(indexCoupe);
            listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());

            while(listeLiens.deplacerSuivant()){
                listeDuplicat.ajouterFin(((Lien)listeLiens.getElement()).copie());
            }
            
        }
        
        return listeDuplicat;
    }
    

	/**
	 * Méthode permettant d'obtenir le nombre de liens dans la carte.
	 * 
	 * @return nombre de liens dans la carte
	 */
    public int getNbLiens(){
        return listeLiens.getNbElements();
    }    

	/**
	 * Méthode permettant d'obtenir le score de la carte.
	 * 
	 * @return le score
	 */
    public double getScore(){
        return score;
    }    


    /**
     * Évalue le score de la carte.
     * 
     * @param afficher, true si on veut afficher le score en plus de le calculer
     */
    public void evalueScore(Configuration config, boolean afficher){
    	
    	
    	// Calcule la distance moyenne entre 2 noeuds.
		double distanceMoyenne = 
				moteurDistanceMoyenne.getDistanceMoyenne(listeLiens,afficher);
		
    	// Calcule la longeur totales des liens.
    	double longueur = calculSommeLongueurs();
    	
    	// Calcule le score.
    	score = config.getPenaliteDistance() * distanceMoyenne + 
    			                    config.getPenaliteLongueur() * longueur +
    			                               config.getPenaliteDeconnecte() *
    			                               		       moteurDistanceMoyenne.getNbNonConnecte();
    	
    }    
    
    /**
     * Fait le calcul de la somme des longeurs des liens de la carte
     * 
     * @return la longeur totales des liens 
     */
    private double calculSommeLongueurs(){

    	// se déplace au début de la liste de liens
    	double sommeLongueur = 0.0;
    	listeLiens.deplacerDebut();
    	
    	// se déplace vers la fin en additionnant
    	// les longeurs
    	while(listeLiens.deplacerSuivant()){
    		
    		Lien lien = (Lien) listeLiens.getElement();
    		sommeLongueur += lien.getLongueur();
    	}
    	
    	return sommeLongueur;
    }

    /**
     * Redéfinition de la méthode permettant d'obtenir un représentation
     * String de l'objet.
     * 
     * return La représentation de l'objet en chaîne de caractères.
     */
    public String toString(){
    	
    	String str = new String();
    	str += "Liste des liens\n"
    			;
    	listeLiens.deplacerDebut();
    	
    	while(listeLiens.deplacerSuivant()){
    		str += listeLiens.getElement().toString();
    		str += "\n";
    	}
    	
    	str += "Score: " + score + "\n";
    	
    	return str;
    }

    /* Calcule et retourne le nombre de ville dans la liste chaînée qui correspond au 
     * paramètre.
     */
    public int getNbDeCetteVille(Ville v){

    	/*
    	 * Stratégie : Parcours linéaire et compte le nombre d'occurences.
    	 */

    	int nbVilles = 0;

    	listeLiens.deplacerDebut();

    	while(listeLiens.deplacerSuivant()){

    		// Obtient une référence sur le lien.
    		Lien lien = (Lien)listeLiens.getElement();

    		// On obtient les villes du lien.
    		Ville  ville1 = lien.getSrc();
    		Ville  ville2 = lien.getDest(ville1);

    		// SI c'est égale à une ou l'autre, on la compte.
    		if(ville1.equals(v) || ville2.equals(v)) {
    			nbVilles++;
    		}
    	}

    	return nbVilles;

    }

}

