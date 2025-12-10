import greenfoot.*;

/**
 * Level2 - Das zweite Level mit vertikalen Gegnern.
 * 
 * Level2 führt den neuen Gegnertyp GreenDot ein, der sich vertikal bewegt.
 * Das Layout ist horizontal ausgerichtet (im Gegensatz zum vertikalen Level1),
 * was eine neue strategische Herausforderung darstellt.
 * 
 * Layout-Beschreibung:
 * - Zwei horizontale Wände bilden einen vertikalen Korridor
 * - Obere Wand hat rechts eine Lücke (Eingang)
 * - Untere Wand hat links eine Lücke (Ausgang zum Ziel)
 * - Spieler startet oben links (30, 30)
 * - Ziel ist unten rechts (560, 360)
 * 
 * Gegner:
 * - 4 GreenDots mit unterschiedlichen Geschwindigkeiten
 * - Alle bewegen sich vertikal im Korridor
 * - Unterschiedliche X-Positionen für gestaffelte Herausforderung
 * - Kleinere Größe (30 Pixel) als Standard für erhöhte Schwierigkeit
 * 
 * Lösungsstrategie:
 * 1. Nach rechts zur oberen Lücke
 * 2. Nach unten in den Korridor
 * 3. Timing beachten: Zwischen den GreenDots durchlaufen
 * 4. Nach links zur unteren Lücke
 * 5. Nach rechts zum Ziel
 * 
 * Neue Mechanik:
 * - Vertikale Bewegung der Gegner erfordert andere Timing-Strategie
 * - Spieler muss horizontal durch den Korridor navigieren
 * 
 * Schwierigkeitsgrad: Mittel (neue Gegnerart)
 * Geschätzte Zeit: 15-25 Sekunden
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Level2 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // Wand-Positionen und -Größen
    /** X-Position der oberen Wand */
    private static final int TOP_WALL_X = 0;
    /** Y-Position der oberen Wand */
    private static final int TOP_WALL_Y = 100;
    /** Breite der oberen Wand (lässt rechts Platz für Durchgang) */
    private static final int TOP_WALL_WIDTH = 540;
    /** Höhe der oberen Wand */
    private static final int TOP_WALL_HEIGHT = 60;
    
    /** X-Position der unteren Wand (lässt links Platz für Durchgang) */
    private static final int BOTTOM_WALL_X = 60;
    /** Y-Position der unteren Wand */
    private static final int BOTTOM_WALL_Y = 260;
    /** Breite der unteren Wand */
    private static final int BOTTOM_WALL_WIDTH = 540;
    /** Höhe der unteren Wand */
    private static final int BOTTOM_WALL_HEIGHT = 60;
    
    // GreenDot-Bewegungsgrenzen
    /** Obere Grenze für GreenDot-Bewegung (unter oberer Wand) */
    private static final int GREENDOT_MIN_Y = 145;
    /** Untere Grenze für GreenDot-Bewegung (über unterer Wand) */
    private static final int GREENDOT_MAX_Y = 255;
    
    /** Größe der GreenDots in Pixeln */
    private static final int GREENDOT_SIZE = 30;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level2 und initialisiert alle Spielelemente.
     * 
     * Ablauf:
     * 1. Welt mit 600x400 Pixeln erstellen
     * 2. Wände, Gegner und Ziel platzieren (setup)
     * 3. Spieler hinzufügen
     * 4. HUD initialisieren (label)
     */
    public Level2() {
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
     * - Zwei horizontale Wände bilden einen vertikalen Korridor in der Mitte
     * - Obere Wand: Durchgang rechts (für Hinweg)
     * - Untere Wand: Durchgang links (für Rückweg zum Ziel)
     * - GreenDots bewegen sich vertikal im Korridor zwischen den Wänden
     */
    private void setup() {
        // === WÄNDE ===
        
        // Obere Wand (mit Lücke rechts)
        // Diese Wand blockiert den direkten Weg nach unten
        // und zwingt den Spieler, nach rechts zu gehen
        Wall topWall = new Wall();
        addObject(topWall, 0, 0);
        topWall.placeWall(TOP_WALL_X, TOP_WALL_Y, TOP_WALL_WIDTH, TOP_WALL_HEIGHT);
        
        // Untere Wand (mit Lücke links)
        // Diese Wand zwingt den Spieler, durch den gesamten Korridor zu gehen
        // bevor er zum Ziel gelangen kann
        Wall bottomWall = new Wall();
        addObject(bottomWall, 0, 0);
        bottomWall.placeWall(BOTTOM_WALL_X, BOTTOM_WALL_Y, BOTTOM_WALL_WIDTH, BOTTOM_WALL_HEIGHT);
        
        // === ZIEL ===
        
        // Zielbereich unten rechts
        // Spieler muss durch den Korridor und zurück navigieren
        addObject(new TargetArea(), 560, 360);
        
        // === GEGNER ===
        
        // GreenDots mit unterschiedlichen Geschwindigkeiten
        // Alle bewegen sich vertikal zwischen den beiden Wänden
        // Kleinere Größe (30 Pixel) macht sie schwerer zu vermeiden
        
        // GreenDot 1: Linker Bereich (X=200), langsam (Speed=1)
        // Erste Herausforderung beim Betreten des Korridors
        addObject(new GreenDot(GREENDOT_MIN_Y, GREENDOT_MAX_Y, 1, GREENDOT_SIZE), 200, 200);
        
        // GreenDot 2: Mittlerer Bereich (X=300), schnell (Speed=2)
        // Zweite Herausforderung, schnellere Bewegung erhöht Schwierigkeit
        addObject(new GreenDot(GREENDOT_MIN_Y, GREENDOT_MAX_Y, 2, GREENDOT_SIZE), 300, 200);
        
        // GreenDot 3: Rechter Bereich (X=400), langsam (Speed=1)
        // Dritte Herausforderung, wieder langsamer für Abwechslung
        addObject(new GreenDot(GREENDOT_MIN_Y, GREENDOT_MAX_Y, 1, GREENDOT_SIZE), 400, 200);
        
        // GreenDot 4: Ganz rechts (X=500), schnell (Speed=2)
        // Letzte Herausforderung vor dem Ausgang, schnelle Bewegung
        addObject(new GreenDot(GREENDOT_MIN_Y, GREENDOT_MAX_Y, 2, GREENDOT_SIZE), 500, 200);
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
        showText("Level: 2", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }
    
    /**
     * Gibt den Namen dieses Levels zurück.
     * 
     * @return Der Level-Name "Level 2"
     */
    @Override
    public String levelName() {
        return "Level 2";
    }
}
