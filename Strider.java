import greenfoot.*;

/**
 * Strider - Geradeaus laufender Gegner mit Teleport-Fähigkeit.
 * 
 * Der Strider ist ein schneller, geradeaus laufender Gegner, der am Weltrand
 * nicht stoppt, sondern auf die gegenüberliegende Seite teleportiert. Dies macht
 * ihn zu einem konstanten Bedrohungsfaktor, da er nie verschwindet.
 * 
 * Bewegungsverhalten:
 * - Wählt beim Start eine zufällige Richtung (auch diagonal möglich)
 * - Bewegt sich kontinuierlich in diese Richtung mit hoher Geschwindigkeit
 * - Teleportiert am Weltrand auf die gegenüberliegende Seite
 * - Ändert niemals die Richtung (außer durch Teleport)
 * 
 * Strategische Bedeutung:
 * - Vorhersehbar: Spieler kann Bewegungsmuster lernen
 * - Schnell: Schwer zu vermeiden, wenn er direkt auf Spieler zukommt
 * - Konstante Bedrohung: Verschwindet nie, kommt immer wieder
 * - Gut für offene Bereiche ohne viele Wände
 * 
 * Besonderheiten:
 * - Kann diagonal laufen (dx und dy können beide != 0 sein)
 * - Teleport-Mechanik sorgt für endlose Bewegung
 * - Ignoriert Wände (teleportiert einfach durch)
 * 
 * Verwendung in Leveln:
 * - Level 5: Zwei Strider im zweiten Gang
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Strider extends Enemy {
    
    // ==================== KONSTANTEN ====================
    
    /** Geschwindigkeit in Pixeln pro Takt */
    private static final int DEFAULT_SPEED = 3;
    
    // ==================== ATTRIBUTE ====================
    
    /** 
     * Bewegungsrichtung in X-Richtung.
     * -1 = links, 0 = keine X-Bewegung, 1 = rechts
     */
    private int dx;
    
    /** 
     * Bewegungsrichtung in Y-Richtung.
     * -1 = hoch, 0 = keine Y-Bewegung, 1 = runter
     */
    private int dy;
    
    /** Geschwindigkeit (Multiplikator für dx und dy) */
    private int speed = DEFAULT_SPEED;
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird einmalig aufgerufen, wenn der Strider zur Welt hinzugefügt wird.
     * Wählt eine zufällige Bewegungsrichtung.
     * 
     * Mögliche Richtungen:
     * - Horizontal: (-1, 0) links, (1, 0) rechts
     * - Vertikal: (0, -1) hoch, (0, 1) runter
     * - Diagonal: (-1, -1) links-oben, (1, -1) rechts-oben, usw.
     * 
     * Wichtig: Mindestens eine Richtung muss aktiv sein (nicht beide 0).
     * 
     * @param w Die Welt, zu der der Strider hinzugefügt wurde
     */
    @Override
    protected void addedToWorld(World w) {
        // === Zufällige Richtung wählen ===
        // Greenfoot.getRandomNumber(3) liefert 0, 1 oder 2
        // Durch -1 erhalten wir -1, 0 oder 1
        dx = Greenfoot.getRandomNumber(3) - 1;  // -1, 0, oder 1
        dy = Greenfoot.getRandomNumber(3) - 1;  // -1, 0, oder 1
        
        // === Sonderfall: Beide Richtungen sind 0 ===
        // Dies würde bedeuten, dass der Strider sich nicht bewegt
        // In diesem Fall setzen wir dx auf 1 (Bewegung nach rechts)
        if (dx == 0 && dy == 0) {
            dx = 1;
        }
    }
    
    // ==================== BEWEGUNGSLOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die Bewegung und Teleportation.
     * 
     * Ablauf:
     * 1. Bewege den Strider in die gewählte Richtung (dx * speed, dy * speed)
     * 2. Prüfe, ob der Strider den Weltrand erreicht hat
     * 3. Wenn ja: Teleportiere auf die gegenüberliegende Seite
     * 
     * Teleport-Logik:
     * - Links raus → Rechts rein
     * - Rechts raus → Links rein
     * - Oben raus → Unten rein
     * - Unten raus → Oben rein
     */
    @Override
    public void act() {
        // === SCHRITT 1: Bewegung ausführen ===
        // Multipliziere Richtung (dx, dy) mit Geschwindigkeit (speed)
        setLocation(getX() + dx * speed, getY() + dy * speed);
        
        // === SCHRITT 2: Weltrand-Teleportation ===
        World w = getWorld();
        
        // Linker Rand erreicht → Teleport nach rechts
        if (getX() <= 0) {
            setLocation(w.getWidth() - 1, getY());
        }
        
        // Rechter Rand erreicht → Teleport nach links
        if (getX() >= w.getWidth()) {
            setLocation(1, getY());
        }
        
        // Oberer Rand erreicht → Teleport nach unten
        if (getY() <= 0) {
            setLocation(getX(), w.getHeight() - 1);
        }
        
        // Unterer Rand erreicht → Teleport nach oben
        if (getY() >= w.getHeight()) {
            setLocation(getX(), 1);
        }
    }
}
