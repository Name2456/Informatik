import greenfoot.*;

/**
 * Enemy - Abstrakte Basisklasse für alle Gegner im Spiel.
 * 
 * Diese Klasse dient als gemeinsame Basis für alle Gegnertypen und stellt
 * grundlegende Funktionalität bereit, die von allen Gegnern genutzt wird.
 * 
 * Vorteile der Vererbung:
 * - Gemeinsamer Code muss nur einmal geschrieben werden
 * - Player kann alle Gegner mit einem einzigen Check erkennen (instanceof Enemy)
 * - Neue Gegnertypen können einfach hinzugefügt werden
 * - Konsistentes Verhalten über alle Gegner hinweg
 * 
 * Abgeleitete Klassen:
 * - BlueDot: Horizontal pendelnder Gegner
 * - GreenDot: Vertikal pendelnder Gegner
 * - Pulsar: Pulsierender Gegner (größer/kleiner)
 * - RandomWalker: Zufällig bewegender Gegner
 * - Strider: Geradeaus laufender Gegner mit Teleport
 * - Shooter: Stationärer Gegner, der Projektile abfeuert
 * - Follower: Verfolgt den Spieler
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Enemy extends Actor {
    
    /**
     * Bewegt den Gegner um die angegebene Distanz rückwärts.
     * 
     * Diese Methode wird typischerweise aufgerufen, wenn ein Gegner gegen
     * eine Wand läuft oder den Weltrand erreicht. Sie korrigiert die Position,
     * indem sie die letzte Bewegung rückgängig macht.
     * 
     * Verwendungsbeispiel:
     * <pre>
     * // Gegner bewegt sich um dx=5 nach rechts
     * setLocation(getX() + 5, getY());
     * 
     * // Wandkollision erkannt
     * if (!getIntersectingObjects(Wall.class).isEmpty()) {
     *     bounceBack(5, 0);  // Bewegung rückgängig machen
     *     speed = -speed;     // Richtung umkehren
     * }
     * </pre>
     * 
     * @param dx Die X-Verschiebung, die rückgängig gemacht werden soll
     *           (positiv = nach links korrigieren, negativ = nach rechts korrigieren)
     * @param dy Die Y-Verschiebung, die rückgängig gemacht werden soll
     *           (positiv = nach oben korrigieren, negativ = nach unten korrigieren)
     */
    protected void bounceBack(int dx, int dy) {
        setLocation(getX() - dx, getY() - dy);
    }
    
    /**
     * Prüft, ob der Gegner am Rand der Welt ist.
     * 
     * Diese Hilfsmethode kann von abgeleiteten Klassen verwendet werden,
     * um zu prüfen, ob der Gegner den Weltrand erreicht hat.
     * 
     * @return true, wenn der Gegner am Rand ist, sonst false
     */
    protected boolean isAtEdge() {
        return getX() <= 0 || getX() >= getWorld().getWidth() - 1 ||
               getY() <= 0 || getY() >= getWorld().getHeight() - 1;
    }
    
    /**
     * Prüft, ob der Gegner mit einer Wand kollidiert.
     * 
     * Diese Hilfsmethode vereinfacht die Kollisionserkennung für abgeleitete Klassen.
     * 
     * @return true, wenn der Gegner eine Wand berührt, sonst false
     */
    protected boolean isTouchingWall() {
        return !getIntersectingObjects(Wall.class).isEmpty();
    }
}
