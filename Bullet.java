import greenfoot.*;

/**
 * Bullet - Projektil, das vom Shooter abgefeuert wird.
 * 
 * Das Bullet ist ein schnelles Projektil, das in eine festgelegte Richtung
 * fliegt und beim Kontakt mit dem Spieler Schaden verursacht. Es verschwindet,
 * wenn es den Weltrand oder eine Wand erreicht.
 * 
 * Bewegungsverhalten:
 * - Fliegt geradeaus in die beim Erstellen festgelegte Richtung
 * - Hohe Geschwindigkeit (schneller als Spieler)
 * - Verschwindet am Weltrand
 * - Verschwindet bei Wandkollision
 * 
 * Interaktion mit Spieler:
 * - Wird vom Player erkannt (Player prüft auf Bullet-Kollision)
 * - Verursacht Lebensverlust beim Spieler
 * - Verschwindet nicht beim Treffen des Spielers (Player entfernt es)
 * 
 * Strategische Bedeutung:
 * - Schnell: Schwer zu vermeiden, wenn man direkt in der Schusslinie steht
 * - Begrenzte Reichweite: Verschwindet an Wänden
 * - Vorhersehbar: Fliegt immer geradeaus
 * 
 * Technische Details:
 * - Erbt von Actor (nicht von Enemy, da es kein Gegner ist)
 * - Wird vom Shooter erstellt und zur Welt hinzugefügt
 * - Entfernt sich selbst bei Kollision oder Weltrand
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Bullet extends Actor {
    
    // ==================== KONSTANTEN ====================
    
    /** Geschwindigkeit des Projektils in Pixeln pro Takt */
    private static final int DEFAULT_SPEED = 5;
    
    // Richtungscodes (müssen mit Shooter übereinstimmen)
    /** Richtung: Hoch (nach oben) */
    private static final int DIRECTION_UP = 0;
    /** Richtung: Runter (nach unten) */
    private static final int DIRECTION_DOWN = 1;
    /** Richtung: Links */
    private static final int DIRECTION_LEFT = 2;
    /** Richtung: Rechts */
    private static final int DIRECTION_RIGHT = 3;
    
    // ==================== ATTRIBUTE ====================
    
    /** 
     * Bewegung in X-Richtung pro Takt.
     * Negativ = links, 0 = keine X-Bewegung, Positiv = rechts
     */
    private int dx = 0;
    
    /** 
     * Bewegung in Y-Richtung pro Takt.
     * Negativ = hoch, 0 = keine Y-Bewegung, Positiv = runter
     */
    private int dy = 0;
    
    /** Geschwindigkeit des Projektils */
    private int speed = DEFAULT_SPEED;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt ein Projektil mit festgelegter Flugrichtung.
     * 
     * Die Richtung wird in Bewegungsvektoren (dx, dy) umgewandelt:
     * - Hoch (0): dx=0, dy=-speed
     * - Runter (1): dx=0, dy=+speed
     * - Links (2): dx=-speed, dy=0
     * - Rechts (3): dx=+speed, dy=0
     * 
     * @param direction Flugrichtung (0=hoch, 1=runter, 2=links, 3=rechts)
     */
    public Bullet(int direction) {
        // Richtung in Bewegungsvektoren umwandeln
        switch (direction) {
            case DIRECTION_UP:    // Hoch
                dy = -speed;
                break;
            case DIRECTION_DOWN:  // Runter
                dy = speed;
                break;
            case DIRECTION_LEFT:  // Links
                dx = -speed;
                break;
            case DIRECTION_RIGHT: // Rechts
                dx = speed;
                break;
        }
    }
    
    // ==================== BEWEGUNGSLOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die Bewegung des Projektils.
     * 
     * Ablauf:
     * 1. Bewege das Projektil in die festgelegte Richtung (dx, dy)
     * 2. Prüfe, ob das Projektil den Weltrand erreicht hat
     *    → Wenn ja: Projektil entfernen
     * 3. Prüfe, ob das Projektil eine Wand getroffen hat
     *    → Wenn ja: Projektil entfernen
     * 
     * Wichtig: Das Projektil entfernt sich selbst aus der Welt.
     * Der Spieler prüft separat auf Kollision mit Bullets.
     */
    @Override
    public void act() {
        // === SCHRITT 1: Bewegung ausführen ===
        setLocation(getX() + dx, getY() + dy);
        
        // === SCHRITT 2: Weltrand prüfen ===
        // isAtEdge() ist eine Greenfoot-Methode, die true zurückgibt,
        // wenn der Actor am Rand der Welt ist
        if (isAtEdge()) {
            // Projektil aus der Welt entfernen
            getWorld().removeObject(this);
            // return verhindert weitere Ausführung (wichtig, da Objekt entfernt wurde)
            return;
        }
        
        // === SCHRITT 3: Wandkollision prüfen ===
        // Wenn das Projektil eine Wand trifft, verschwindet es
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            // Projektil aus der Welt entfernen
            getWorld().removeObject(this);
        }
    }
}
