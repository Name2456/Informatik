import greenfoot.*;

/**
 * Level1 - Das erste Level des Spiels.
 * 
 * Level1 dient als Einführung in die Spielmechanik und ist bewusst einfach gehalten.
 * Der Spieler lernt hier die Grundlagen: Bewegung mit Pfeiltasten, Vermeidung von
 * Gegnern und das Erreichen der Zielzone.
 * 
 * Layout-Beschreibung:
 * - Zwei vertikale Wände bilden einen horizontalen Korridor
 * - Linke Wand hat unten eine Lücke (Eingang)
 * - Rechte Wand hat oben eine Lücke (Ausgang zum Ziel)
 * - Spieler startet oben links (30, 30)
 * - Ziel ist unten rechts (560, 360)
 * 
 * Gegner:
 * - 4 BlueDots mit zufälliger Geschwindigkeit (1-5 Pixel/Takt)
 * - Alle bewegen sich horizontal im Korridor
 * - Unterschiedliche Y-Positionen für gestaffelte Herausforderung
 * 
 * Lösungsstrategie:
 * 1. Nach rechts durch die obere Lücke in den Korridor
 * 2. Timing beachten: Zwischen den BlueDots durchlaufen
 * 3. Nach unten zur unteren Lücke
 * 4. Nach rechts zum Ziel
 * 
 * Schwierigkeitsgrad: Leicht (Einführungslevel)
 * Geschätzte Zeit: 10-20 Sekunden
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Level1 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // Wand-Positionen und -Größen
    /** X-Position der linken Wand */
    private static final int LEFT_WALL_X = 100;
    /** Y-Position der linken Wand */
    private static final int LEFT_WALL_Y = 0;
    /** Breite der linken Wand */
    private static final int LEFT_WALL_WIDTH = 80;
    /** Höhe der linken Wand (lässt unten Platz für Durchgang) */
    private static final int LEFT_WALL_HEIGHT = 340;
    
    /** X-Position der rechten Wand */
    private static final int RIGHT_WALL_X = 420;
    /** Y-Position der rechten Wand (lässt oben Platz für Durchgang) */
    private static final int RIGHT_WALL_Y = 60;
    /** Breite der rechten Wand */
    private static final int RIGHT_WALL_WIDTH = 80;
    /** Höhe der rechten Wand */
    private static final int RIGHT_WALL_HEIGHT = 340;
    
    // BlueDot-Bewegungsgrenzen
    /** Linke Grenze für BlueDot-Bewegung (rechts von linker Wand) */
    private static final int BLUEDOT_MIN_X = 190;
    /** Rechte Grenze für BlueDot-Bewegung (links von rechter Wand) */
    private static final int BLUEDOT_MAX_X = 410;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level1 und initialisiert alle Spielelemente.
     * 
     * Ablauf:
     * 1. Welt mit 600x400 Pixeln erstellen
     * 2. Wände, Gegner und Ziel platzieren (setup)
     * 3. Spieler hinzufügen
     * 4. HUD initialisieren (label)
     */
    public Level1() {
        super(WORLD_WIDTH, WORLD_HEIGHT, CELL_SIZE);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }
    
    // ==================== LEVEL-AUFBAU ====================
    
    /**
     * Platziert alle Wände, Gegner und das Ziel im Level.
     * 
     * Layout-Logik:
     * - Zwei vertikale Wände bilden einen Korridor in der Mitte
     * - Linke Wand: Durchgang unten (für Rückweg)
     * - Rechte Wand: Durchgang oben (für Hinweg zum Ziel)
     * - BlueDots bewegen sich im Korridor zwischen den Wänden
     */
    private void setup() {
        // === WÄNDE ===
        
        // Linke Wand (mit Lücke unten)
        // Diese Wand blockiert den direkten Weg nach unten
        // und zwingt den Spieler, durch den Korridor zu gehen
        Wall leftWall = new Wall();
        addObject(leftWall, 0, 0);
        leftWall.placeWall(LEFT_WALL_X, LEFT_WALL_Y, LEFT_WALL_WIDTH, LEFT_WALL_HEIGHT);
        
        // Rechte Wand (mit Lücke oben)
        // Diese Wand ermöglicht den Einstieg in den Korridor oben
        // und blockiert den direkten Weg zum Ziel
        Wall rightWall = new Wall();
        addObject(rightWall, 0, 0);
        rightWall.placeWall(RIGHT_WALL_X, RIGHT_WALL_Y, RIGHT_WALL_WIDTH, RIGHT_WALL_HEIGHT);
        
        // === ZIEL ===
        
        // Zielbereich unten rechts
        // Spieler muss durch den Korridor navigieren, um hierher zu gelangen
        addObject(new TargetArea(), 560, 360);
        
        // === GEGNER ===
        
        // BlueDots mit zufälliger Geschwindigkeit (0 = zufällig 1-5)
        // Alle bewegen sich horizontal zwischen den beiden Wänden
        
        // BlueDot 1: Oberer Bereich (Y=100)
        // Erste Herausforderung beim Betreten des Korridors
        addObject(new BlueDot(BLUEDOT_MIN_X, BLUEDOT_MAX_X, 0), 250, 100);
        
        // BlueDot 2: Mittlerer Bereich (Y=160)
        // Zweite Herausforderung, etwas tiefer
        addObject(new BlueDot(BLUEDOT_MIN_X, BLUEDOT_MAX_X, 0), 300, 160);
        
        // BlueDot 3: Mittlerer Bereich (Y=220)
        // Dritte Herausforderung, noch tiefer
        addObject(new BlueDot(BLUEDOT_MIN_X, BLUEDOT_MAX_X, 0), 350, 220);
        
        // BlueDot 4: Unterer Bereich (Y=280)
        // Letzte Herausforderung vor dem Ausgang
        addObject(new BlueDot(BLUEDOT_MIN_X, BLUEDOT_MAX_X, 0), 280, 280);
    }
    
    // ==================== HUD-VERWALTUNG ====================
    
    /**
     * Initialisiert das HUD (Heads-Up Display) für dieses Level.
     * 
     * Ablauf:
     * 1. Level-Name unten links anzeigen
     * 2. Spieler-Objekt finden
     * 3. Leben auf 5 zurücksetzen (neues Level)
     * 4. HUD mit Leben-Anzeige aktualisieren
     */
    private void label() {
        // Level-Name unten links anzeigen
        showText(levelName(), 70, 380);
        
        // Spieler finden und Leben zurücksetzen
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) {
            p.resetLives();
        }
        
        // HUD mit 5 Leben initialisieren
        showHUD(5);
    }
    
    /**
     * Aktualisiert die HUD-Anzeige mit Level-Nummer und Leben.
     * 
     * @param lives Aktuelle Anzahl Leben des Spielers
     */
    @Override
    public void showHUD(int lives) {
        showText("Level: 1", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }
    
    /**
     * Gibt den Namen dieses Levels zurück.
     * 
     * @return Der Level-Name "Level 1"
     */
    @Override
    public String levelName() {
        return "Level 1";
    }
}
