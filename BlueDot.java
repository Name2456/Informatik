import greenfoot.*;

/**
 * BlueDot - Horizontal pendelnder Gegner.
 * 
 * Der BlueDot bewegt sich kontinuierlich horizontal zwischen zwei definierten
 * X-Koordinaten (minX und maxX) hin und her. Er ist einer der grundlegenden
 * Gegnertypen und wird hauptsächlich in den ersten Leveln verwendet.
 * 
 * Bewegungsverhalten:
 * - Bewegt sich mit konstanter Geschwindigkeit horizontal
 * - Kehrt Richtung um, wenn er minX oder maxX erreicht
 * - Kehrt Richtung um, wenn er gegen eine Wand läuft
 * - Bleibt auf seiner Y-Koordinate (keine vertikale Bewegung)
 * 
 * Verwendung in Leveln:
 * - Level 1: Mehrere BlueDots mit zufälliger Geschwindigkeit
 * - Level 3: BlueDots in Kombination mit anderen Gegnertypen
 * - Level 4: BlueDots als zusätzliche Herausforderung
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class BlueDot extends Enemy {
    
    // ==================== ATTRIBUTE ====================
    
    /** Linke Grenze des Bewegungsbereichs (X-Koordinate) */
    private int minX;
    
    /** Rechte Grenze des Bewegungsbereichs (X-Koordinate) */
    private int maxX;
    
    /** 
     * Geschwindigkeit und Richtung der Bewegung.
     * Positiver Wert = Bewegung nach rechts
     * Negativer Wert = Bewegung nach links
     */
    private int speed;
    
    // ==================== KONSTRUKTOREN ====================
    
    /**
     * Erstellt einen BlueDot mit spezifischen Bewegungsgrenzen und Geschwindigkeit.
     * 
     * @param minX  Linke Grenze des Bewegungsbereichs (X-Koordinate)
     * @param maxX  Rechte Grenze des Bewegungsbereichs (X-Koordinate)
     * @param speed Geschwindigkeit in Pixeln pro Takt.
     *              Wenn speed <= 0, wird eine zufällige Geschwindigkeit zwischen 1 und 5 gewählt.
     *              Empfohlene Werte: 1 (langsam), 2-3 (mittel), 4-5 (schnell)
     */
    public BlueDot(int minX, int maxX, int speed) {
        this.minX = minX;
        this.maxX = maxX;
        
        // Bei speed <= 0: Zufällige Geschwindigkeit zwischen 1 und 5
        // Dies sorgt für Variation und macht das Spiel interessanter
        if (speed <= 0) {
            this.speed = Greenfoot.getRandomNumber(5) + 1;
        } else {
            this.speed = speed;
        }
    }
    
    /**
     * Erstellt einen BlueDot mit Standardgrenzen (120-480).
     * 
     * Dieser Konstruktor ist nützlich für schnelles Level-Design,
     * wenn die Standard-Bewegungsgrenzen ausreichen.
     * 
     * @param speed Geschwindigkeit (0 = zufällig 1-5)
     */
    public BlueDot(int speed) {
        this(120, 480, speed);
    }
    
    // ==================== BEWEGUNGSLOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die Bewegung des BlueDots.
     * 
     * Ablauf:
     * 1. Bewege den BlueDot um 'speed' Pixel nach links oder rechts
     * 2. Prüfe, ob die Bewegungsgrenzen (minX/maxX) erreicht wurden
     * 3. Wenn Grenze erreicht: Position korrigieren und Richtung umkehren
     * 4. Prüfe auf Wandkollision
     * 5. Wenn Wand berührt: Position zurücksetzen und Richtung umkehren
     */
    @Override
    public void act() {
        // === SCHRITT 1: Bewegung ausführen ===
        // dx speichert die aktuelle Bewegung für spätere Korrektur
        int dx = speed;
        setLocation(getX() + dx, getY());
        
        // === SCHRITT 2: Grenzen prüfen ===
        
        // Linke Grenze erreicht
        if (getX() <= minX) {
            // Position auf exakte Grenze setzen (verhindert "Durchrutschen")
            setLocation(minX, getY());
            // Richtung umkehren: Nach rechts bewegen (positiver speed)
            speed = Math.abs(speed);
        }
        
        // Rechte Grenze erreicht
        if (getX() >= maxX) {
            // Position auf exakte Grenze setzen
            setLocation(maxX, getY());
            // Richtung umkehren: Nach links bewegen (negativer speed)
            speed = -Math.abs(speed);
        }
        
        // === SCHRITT 3: Wandkollision prüfen ===
        // Wenn der BlueDot eine Wand berührt, muss er zurückprallen
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            // Bewegung rückgängig machen (zurück zur Position vor der Kollision)
            bounceBack(dx, 0);
            // Richtung umkehren
            speed = -speed;
        }
    }
}
