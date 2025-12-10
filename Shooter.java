import greenfoot.*;

/**
 * Shooter - Stationärer Gegner, der Projektile abfeuert.
 * 
 * Der Shooter ist ein stationärer Gegner, der sich nicht bewegt, aber in
 * regelmäßigen Abständen Projektile (Bullets) in eine festgelegte Richtung
 * abfeuert. Spieler müssen das Schussmuster lernen und Timing beachten.
 * 
 * Bewegungsverhalten:
 * - Bewegt sich nicht (stationär)
 * - Feuert regelmäßig Projektile ab
 * - Schussrichtung wird beim Erstellen festgelegt
 * - Feuerrate ist konstant (mit kleiner Variation)
 * 
 * Strategische Bedeutung:
 * - Vorhersehbar: Spieler kann Schussmuster lernen
 * - Blockiert Durchgänge temporär (wenn Projektil fliegt)
 * - Erfordert Timing: Spieler muss zwischen Schüssen durchlaufen
 * - Gut für enge Korridore
 * 
 * Besonderheiten:
 * - Projektile werden als separate Bullet-Objekte erstellt
 * - Feuerrate hat kleine zufällige Variation (verhindert perfektes Timing)
 * - Kann in alle vier Richtungen schießen
 * 
 * Verwendung in Leveln:
 * - Level 4: Shooter schießt nach rechts durch WindZone
 * - Level 5: Shooter schießt nach links im finalen Gang
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Shooter extends Enemy {
    
    // ==================== KONSTANTEN ====================
    
    /** Basis-Feuerrate in Takten (60 Takte ≈ 1 Sekunde bei 60 FPS) */
    private static final int BASE_FIRE_RATE = 60;
    
    /** Maximale zufällige Variation der Feuerrate in Takten */
    private static final int FIRE_RATE_VARIATION = 30;
    
    // Richtungscodes
    /** Richtung: Hoch (nach oben) */
    public static final int DIRECTION_UP = 0;
    /** Richtung: Runter (nach unten) */
    public static final int DIRECTION_DOWN = 1;
    /** Richtung: Links */
    public static final int DIRECTION_LEFT = 2;
    /** Richtung: Rechts */
    public static final int DIRECTION_RIGHT = 3;
    
    // ==================== ATTRIBUTE ====================
    
    /** Aktuelle Wartezeit bis zum nächsten Schuss (Countdown) */
    private int cooldown = 0;
    
    /** Basis-Feuerrate (Takte zwischen Schüssen) */
    private int fireRate = BASE_FIRE_RATE;
    
    /** 
     * Schussrichtung.
     * 0 = hoch, 1 = runter, 2 = links, 3 = rechts
     */
    private int direction;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt einen Shooter mit festgelegter Schussrichtung.
     * 
     * Richtungscodes:
     * - 0: Hoch (Projektile fliegen nach oben)
     * - 1: Runter (Projektile fliegen nach unten)
     * - 2: Links (Projektile fliegen nach links)
     * - 3: Rechts (Projektile fliegen nach rechts)
     * 
     * Empfehlung: Verwende die Konstanten DIRECTION_UP, DIRECTION_DOWN, etc.
     * 
     * @param direction Schussrichtung (0-3)
     */
    public Shooter(int direction) {
        this.direction = direction;
    }
    
    // ==================== SCHUSS-LOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert das Abfeuern von Projektilen.
     * 
     * Ablauf:
     * 1. Prüfe, ob Cooldown abgelaufen ist (cooldown <= 0)
     * 2. Wenn ja: Projektil abfeuern und Cooldown zurücksetzen
     * 3. Wenn nein: Cooldown um 1 verringern
     * 
     * Cooldown-Mechanismus:
     * - Verhindert, dass Shooter in jedem Takt schießt
     * - Nach jedem Schuss wird Cooldown auf fireRate + Variation gesetzt
     * - Variation sorgt für leicht unvorhersehbares Timing
     */
    @Override
    public void act() {
        // === Cooldown abgelaufen? ===
        if (cooldown <= 0) {
            // Projektil abfeuern
            fire();
            
            // Cooldown zurücksetzen mit zufälliger Variation
            // Dies verhindert, dass Spieler perfektes Timing lernen können
            cooldown = fireRate + Greenfoot.getRandomNumber(FIRE_RATE_VARIATION);
        } else {
            // Cooldown verringern (Countdown)
            cooldown--;
        }
    }
    
    /**
     * Feuert ein Projektil in die festgelegte Richtung ab.
     * 
     * Das Projektil wird an der Position des Shooters erstellt und
     * bewegt sich dann in die festgelegte Richtung.
     */
    private void fire() {
        // Neues Projektil mit der Schussrichtung erstellen
        Bullet bullet = new Bullet(direction);
        
        // Projektil an der Position des Shooters zur Welt hinzufügen
        getWorld().addObject(bullet, getX(), getY());
    }
}
