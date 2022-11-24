package hierarchieVehicules;

import javax.swing.JOptionPane;

/**
 * Ce programme teste une hiérarchie de classes nécessaire au tp2.
 * 
 * Elle indique le nom des classes et des méthodes de la fabrique nécessaires.
 * 
 * @author Pierre Bélisle
* @version copyright A2022
 *
 */
public class TestInterfaceVehiculeEtFabrique {

	public void test() {
		
		// Sert à obtenir un véhicule en provenance de la fabrique.
		InterfaceVehicules vehicule;
		
		// Évite pls appels à l'accesseur.
		int nbVehicule = FabriqueVehicule.getNbTypesVehicule();
		
		// On présume que ce nombre comprend tous les types de véhicules possibles.
		for(int i = 0; i < nbVehicule; i++) {
			
			vehicule = FabriqueVehicule.obtenirVehicule(i);
					
			afficherVehicule(vehicule);
		}
	}

	/*
	 * Affiche les infos d'un véhicule.
	 * 
	 * @param vehicule  Le véhicule à afficher.
	 */
	private void afficherVehicule(InterfaceVehicules vehicule) {

		JOptionPane.showMessageDialog(null,
				
				vehicule + " " + 
		        InterfaceVehicules
		        		.tabNomTypeCarburant[vehicule.getTypeCarburant()] + " " +
		        
				vehicule.getNbPassagersMax());
	}
}