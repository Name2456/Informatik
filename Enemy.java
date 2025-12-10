import greenfoot.*;

/**
 * Enemy – Basisklasse für alle Gegner
 * Enthält gemeinsame Hilfsmethoden.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Enemy extends Actor {
    
    /**
     * Korrekturschritt rückwärts (z.B. bei Wandkollision).
     * 
     * @param dx Verschiebung in X-Richtung rückgängig machen
     * @param dy Verschiebung in Y-Richtung rückgängig machen
     */
    protected void bounceBack(int dx, int dy) {
        setLocation(getX() - dx, getY() - dy);
    }
}