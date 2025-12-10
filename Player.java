import greenfoot.*;
import java.util.List;

/**
 * Player - Die Spielfigur, die vom Benutzer gesteuert wird.
 * 
 * Der Player ist das zentrale Element des Spiels. Er kann mit den Pfeiltasten
 * in alle vier Richtungen bewegt werden und muss das Ziel (TargetArea) erreichen,
 * ohne von Gegnern getroffen zu werden oder gegen Wände zu laufen.
 * 
 * Hauptfunktionen:
 * - Steuerung mit Pfeiltasten (kann durch InvertZone invertiert werden)
 * - Kollisionserkennung mit Wänden, Gegnern und Projektilen
 * - Leben-System (5 Leben pro Level)
 * - Level-Wechsel beim Erreichen der TargetArea
 * - Cheat-System für direkten Level-Zugriff
 * - Reaktion auf Spezialzonen (WindZone, InvertZone)
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Player extends Actor {
    // ==================== KONSTANTEN ====================
    
    /** Schrittweite pro Tastendruck in Pixeln */
    private static final int DEFAULT_STEP = 3;
    
    /** Standard-Startposition X-Koordinate */
    private static final int DEFAULT_START_X = 30;
    
    /** Standard-Startposition Y-Koordinate */
    private static final int DEFAULT_START_Y = 30;
    
    /** Maximale Anzahl Leben pro Level */
    private static final int MAX_LIVES = 5;
    
    // ==================== ATTRIBUTE ====================
    
    /** Aktuelle Schrittweite (kann durch Powerups verändert werden) */
    private int step = DEFAULT_STEP;
    
    /** Startposition X-Koordinate (für Reset nach Kollision) */
    private int startX = DEFAULT_START_X;
    
    /** Startposition Y-Koordinate (für Reset nach Kollision) */
    private int startY = DEFAULT_START_Y;
    
    /** Verbleibende Leben des Spielers */
    private int lives = MAX_LIVES;
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird einmalig aufgerufen, wenn der Player zur Welt hinzugefügt wird.
     * Setzt den Spieler auf die Startposition und aktualisiert das HUD.
     * 
     * @param world Die Welt, zu der der Player hinzugefügt wurde
     */
    public void addedToWorld(World world) { 
        startPosition(); 
        updateHUD(); 
    }
    
    /**
     * Hauptmethode, die in jedem Spieltakt aufgerufen wird.
     * 
     * Ablauf:
     * 1. Alte Position speichern (für Kollisionskorrektur)
     * 2. Spezialzonen prüfen (WindZone, InvertZone)
     * 3. Tastatureingaben verarbeiten (mit möglicher Invertierung)
     * 4. Wind-Effekt anwenden
     * 5. Kollisionen prüfen (Wände, Gegner, Projektile)
     * 6. Ziel-Erreichung prüfen
     * 7. Cheat-Codes prüfen
     */
    public void act() {
        // === SCHRITT 1: Alte Position speichern ===
        // Diese wird benötigt, um bei Wandkollision zurückzusetzen
        int xOld = getX();
        int yOld = getY();
        
        // === SCHRITT 2: Spezialzonen-Effekte sammeln ===
        int dxMod = 0;  // Zusätzliche X-Verschiebung durch WindZone
        int dyMod = 0;  // Zusätzliche Y-Verschiebung durch WindZone
        boolean invert = false;  // Flag für invertierte Steuerung
        
        // WindZone-Effekte akkumulieren (mehrere WindZones können sich addieren)
        for (WindZone wz : getIntersectingObjects(WindZone.class)) {
            dxMod += wz.windDx; 
            dyMod += wz.windDy;
        }
        
        // InvertZone prüfen (Steuerung wird umgekehrt)
        if (!getIntersectingObjects(InvertZone.class).isEmpty()) {
            invert = true;
        }
        
        // === SCHRITT 3: Steuerung berechnen ===
        // Bei normaler Steuerung: links = -step, rechts = +step
        // Bei invertierter Steuerung: links = +step, rechts = -step
        int sL = invert ? step : -step;   // Schritt nach links
        int sR = invert ? -step : step;   // Schritt nach rechts
        int sU = invert ? step : -step;   // Schritt nach oben
        int sD = invert ? -step : step;   // Schritt nach unten
        
        // === SCHRITT 4: Tastatureingaben verarbeiten ===
        // Jede Pfeiltaste bewegt den Spieler in die entsprechende Richtung
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() + sL, getY());
        }
        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + sR, getY());
        }
        if (Greenfoot.isKeyDown("up")) {
            setLocation(getX(), getY() + sU);
        }
        if (Greenfoot.isKeyDown("down")) {
            setLocation(getX(), getY() + sD);
        }
        
        // === SCHRITT 5: Wind-Effekt anwenden ===
        // WindZone schiebt den Spieler zusätzlich zur normalen Bewegung
        if (dxMod != 0 || dyMod != 0) {
            setLocation(getX() + dxMod, getY() + dyMod);
        }
        
        // === SCHRITT 6: Kollisionen prüfen ===
        
        // Wandkollision: Spieler wird auf alte Position zurückgesetzt
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld);
            Greenfoot.playSound("hit_wall.mp3");
        }
        
        // Gegnerkollision: Leben verlieren
        if (!getIntersectingObjects(Enemy.class).isEmpty()) {
            loseLife();
        }
        
        // Projektilkollision: Leben verlieren
        if (!getIntersectingObjects(Bullet.class).isEmpty()) {
            loseLife();
        }
        
        // === SCHRITT 7: Ziel erreicht ===
        // Wenn der Spieler die TargetArea berührt, geht es ins nächste Level
        if (!getIntersectingObjects(TargetArea.class).isEmpty()) {
            Greenfoot.playSound("level_up.mp3");
            nextLevel();
        }
        
        // === SCHRITT 8: Cheat-Codes prüfen ===
        checkCheats();
    }
    
    // ==================== CHEAT-SYSTEM ====================
    
    /**
     * Prüft, ob eine Zahlentaste (1-5) gedrückt wurde.
     * Ermöglicht direkten Sprung zu einem bestimmten Level.
     * 
     * Cheat-Codes:
     * - Taste 1: Level 1
     * - Taste 2: Level 2
     * - Taste 3: Level 3
     * - Taste 4: Level 4
     * - Taste 5: Level 5
     */
    private void checkCheats() {
        if (Greenfoot.isKeyDown("1")) Greenfoot.setWorld(new Level1());
        if (Greenfoot.isKeyDown("2")) Greenfoot.setWorld(new Level2());
        if (Greenfoot.isKeyDown("3")) Greenfoot.setWorld(new Level3());
        if (Greenfoot.isKeyDown("4")) Greenfoot.setWorld(new Level4());
        if (Greenfoot.isKeyDown("5")) Greenfoot.setWorld(new Level5());
    }
    
    // ==================== LEBEN-SYSTEM ====================
    
    /**
     * Wird aufgerufen, wenn der Spieler einen Gegner oder ein Projektil berührt.
     * 
     * Ablauf:
     * 1. Sound abspielen
     * 2. Leben um 1 reduzieren
     * 3. HUD aktualisieren
     * 4. Bei 0 Leben: Zurück zu Level 1
     * 5. Sonst: Zurück zur Startposition
     */
    private void loseLife() {
        // Schaden-Sound abspielen
        Greenfoot.playSound("hit_enemy.mp3");
        
        // Leben reduzieren
        lives--;
        
        // HUD aktualisieren (zeigt neue Leben-Anzahl)
        updateHUD();
        
        // Prüfen, ob Game Over
        if (lives <= 0) {
            // Keine Leben mehr: Zurück zum Anfang (Level 1)
            Greenfoot.setWorld(new Level1());
        } else {
            // Noch Leben übrig: Zurück zur Startposition
            startPosition();
        }
    }
    
    /**
     * Setzt den Spieler auf die Startposition zurück.
     * Wird nach Kollision mit Gegner aufgerufen (wenn noch Leben übrig sind).
     */
    public void startPosition() { 
        setLocation(startX, startY); 
    }
    
    /**
     * Setzt die Leben auf den Maximalwert zurück.
     * Wird beim Betreten eines neuen Levels aufgerufen.
     */
    public void resetLives() { 
        lives = MAX_LIVES; 
        updateHUD(); 
    }
    
    /**
     * Gibt die aktuelle Anzahl Leben zurück.
     * 
     * @return Verbleibende Leben
     */
    public int getLives() { 
        return lives; 
    }
    
    // ==================== HUD-VERWALTUNG ====================
    
    /**
     * Aktualisiert die Anzeige (HUD) mit der aktuellen Leben-Anzahl.
     * Funktioniert nur, wenn die aktuelle Welt das LabeledWorld-Interface implementiert.
     */
    public void updateHUD() {
        if (getWorld() instanceof LabeledWorld) {
            ((LabeledWorld)getWorld()).showHUD(lives);
        }
    }
    
    // ==================== LEVEL-WECHSEL ====================
    
    /**
     * Wechselt zum nächsten Level, basierend auf der aktuellen Welt.
     * 
     * Level-Reihenfolge:
     * Level 1 → Level 2 → Level 3 → Level 4 → Level 5 → Sieg
     * 
     * Nach Level 5 wird der Gratulationstext angezeigt und das Spiel gestoppt.
     */
    public void nextLevel() {
        World cw = getWorld();
        
        if (cw instanceof Level1) {
            // Von Level 1 zu Level 2
            Greenfoot.setWorld(new Level2());
        } 
        else if (cw instanceof Level2) {
            // Von Level 2 zu Level 3
            Greenfoot.setWorld(new Level3());
        } 
        else if (cw instanceof Level3) {
            // Von Level 3 zu Level 4
            Greenfoot.setWorld(new Level4());
        } 
        else if (cw instanceof Level4) {
            // Von Level 4 zu Level 5
            Greenfoot.setWorld(new Level5());
        } 
        else if (cw instanceof Level5) {
            // Level 5 abgeschlossen: Spiel gewonnen!
            Greenfoot.playSound("victory.mp3");
            getWorld().showText("YEAH, YOU WON!", 300, 180);
            getWorld().showText("Congratulations!", 300, 220);
            Greenfoot.stop();
        }
    }
}
