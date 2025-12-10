import greenfoot.*;

/**
 * RandomWalker - Zufällig umherlaufender Gegner.
 * 
 * Der RandomWalker ist ein unvorhersehbarer Gegner, der sich in jedem Spieltakt
 * in eine zufällige Richtung bewegt. Dies macht ihn schwer zu planen, aber auch
 * weniger gefährlich als gezielte Gegner wie Follower.
 * 
 * Bewegungsverhalten:
 * - Wählt in jedem Takt zufällig eine der vier Richtungen (hoch, runter, links, rechts)
 * - Bewegt sich um eine feste Schrittweite in die gewählte Richtung
 * - Bleibt stehen, wenn er gegen eine Wand oder den Weltrand läuft
 * - Keine Verfolgung des Spielers (rein zufällig)
 * 
 * Strategische Bedeutung:
 * - Unvorhersehbar: Spieler kann Bewegung nicht antizipieren
 * - Kann zufällig den Weg blockieren
 * - Weniger gefährlich als Follower, da keine gezielte Verfolgung
 * - Gut für Bereiche, wo konstante Bewegung gewünscht ist
 * 
 * Verwendung in Leveln:
 * - Level 5: Im ersten Gang als Einführung in zufällige Bewegung
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class RandomWalker extends Enemy {
    
    // ==================== KONSTANTEN ====================
    
    /** Schrittweite pro Bewegung in Pixeln */
    private static final int DEFAULT_STEP = 2;
    
    /** Sicherheitsabstand zum Weltrand in Pixeln */
    private static final int EDGE_MARGIN = 5;
    
    // ==================== ATTRIBUTE ====================
    
    /** Aktuelle Schrittweite (kann angepasst werden) */
    private int step = DEFAULT_STEP;
    
    // ==================== BEWEGUNGSLOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die zufällige Bewegung.
     * 
     * Ablauf:
     * 1. Aktuelle Position speichern (für Kollisionskorrektur)
     * 2. Zufällige Richtung wählen (0-3)
     * 3. In die gewählte Richtung bewegen
     * 4. Bei Wandkollision: Zurück zur alten Position
     * 5. Bei Weltrand: Zurück zur alten Position
     * 
     * Richtungscodes:
     * - 0: Hoch (Y verringern)
     * - 1: Runter (Y erhöhen)
     * - 2: Links (X verringern)
     * - 3: Rechts (X erhöhen)
     */
    @Override
    public void act() {
        // === SCHRITT 1: Alte Position speichern ===
        // Diese wird benötigt, um bei Kollision zurückzusetzen
        int xOld = getX();
        int yOld = getY();
        
        // === SCHRITT 2: Zufällige Richtung wählen ===
        // Greenfoot.getRandomNumber(4) liefert Werte von 0 bis 3
        int direction = Greenfoot.getRandomNumber(4);
        
        // === SCHRITT 3: Bewegung ausführen ===
        switch (direction) {
            case 0:  // Hoch
                setLocation(getX(), getY() - step);
                break;
            case 1:  // Runter
                setLocation(getX(), getY() + step);
                break;
            case 2:  // Links
                setLocation(getX() - step, getY());
                break;
            case 3:  // Rechts
                setLocation(getX() + step, getY());
                break;
        }
        
        // === SCHRITT 4: Wandkollision prüfen ===
        // Wenn der RandomWalker eine Wand berührt, wird er zurückgesetzt
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld);
        }
        
        // === SCHRITT 5: Weltrand prüfen ===
        // Wenn der RandomWalker zu nahe am Rand ist, wird er zurückgesetzt
        // Dies verhindert, dass er "stecken bleibt" oder aus der Welt fällt
        if (getX() <= EDGE_MARGIN || 
            getX() >= getWorld().getWidth() - EDGE_MARGIN ||
            getY() <= EDGE_MARGIN || 
            getY() >= getWorld().getHeight() - EDGE_MARGIN) {
            setLocation(xOld, yOld);
        }
    }
}
