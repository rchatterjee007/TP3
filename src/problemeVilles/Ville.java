package problemeVilles;

/**
 * Cette classe représente une ville dans le problème de réseau
 * 
 * Liste des méthodes publiques: 
 *     - Ville, 
 *     - calculDistanceDe, 
 *     - getIndex, 
 *     - toString, 
 *
 * @author Fred Simard | ETS, 
 * @version A2022
 */

import java.lang.Math;

public class Ville{

    private int index;
    private double posX;
    private double posY;
    
    public Ville(int index, double posX, double posY){
        
    	this.index = index;
    	this.posX = posX;
        this.posY = posY;
        
    }
    
    public double calculDistanceDe(Ville destination){
        return Math.sqrt(Math.pow(destination.posX-this.posX,2) + 
        		Math.pow(destination.posY-this.posY,2));
    }


    /**
	 * @return the posX
	 */
	public double getPosX() {
		return posX;
	}

	/**
	 * @return the posY
	 */
	public double getPosY() {
		return posY;
	}

	public int getIndex(){
        return this.index;
    }
    
	public String toString(){
		return String.format("Ville: %d pos: %.1f:%.1f", index, posX, posY);
	}
    
}
