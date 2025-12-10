import greenfoot.*;

/**
 * Level4 - "Wind Tunnel" - WindZone-Parcours mit Verfolgung.
 * 
 * Level4 führt die WindZone als Hauptmechanik ein. Der Spieler muss durch
 * mehrere Bereiche navigieren, wobei der Wind ihn kontinuierlich zur Seite
 * schiebt. Kombiniert mit Followern und einem Shooter wird dies zu einer
 * intensiven Herausforderung.
 * 
 * Layout-Beschreibung:
 * - Vier vertikale Gänge, die nach unten führen
 * - Spieler startet oben links (30, 30)
 * - Ziel ist unten rechts (560, 360)
 * - WindZone im zweiten Gang als Hauptherausforderung
 * 
 * Gegner-Strategie:
 * - Gang 1: 1 BlueDot (Aufwärmung)
 * - Gang 2: WindZone (schiebt nach links)
 * - Gang 3: 2 Follower (treiben Spieler vorwärts)
 * - Gang 4: 1 Shooter + 1 BlueDot (Finale)
 * 
 * Lösungsstrategie:
 * 1. Nach unten durch Gang 1 (BlueDot vermeiden)
 * 2. Nach rechts in Gang 2 (gegen Wind ankämpfen!)
 * 3. Nach unten durch Gang 3 (Follower entkommen)
 * 4. Nach rechts in Gang 4 (Shooter-Projektile + BlueDot vermeiden)
 * 5. Zum Ziel
 * 
 * Neue Mechanik:
 * - WindZone als Hauptherausforderung (nicht nur Dekoration)
 * - Follower treiben Spieler in gefährliche Bereiche
 * - Kombination aus Wind + Verfolgung + Projektilen
 * 
 * Schwierigkeitsgrad: Schwer (Wind + aktive Verfolgung)
 * Geschätzte Zeit: 35-50 Sekunden
 * 
 * @author Felix Krusch
 * @version 3.0 (Redesigned)
 */
public class Level4 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // Wand-Dicke
    /** Standard-Wanddicke */
    private static final int WALL_THICKNESS = 50;
    
    // Gang-Breiten
    /** Breite jedes vertikalen Gangs */
    private static final int CORRIDOR_WIDTH = 130;
    
    // WindZone-Parameter
    /** Windstärke in X-Richtung (negativ = nach links) */
    private static final int WIND_DX = -1;
    /** Windstärke in Y-Richtung (0 = kein vertikaler Wind) */
    private static final int WIND_DY = 0;
    
    // Shooter-Parameter
    /** Schussrichtung: 1 = nach unten */
    private static final int SHOOTER_DIRECTION = 1;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level4 und initialisiert alle Spielelemente.
     */
    public Level4() {
        super(WORLD_WIDTH, WORLD_HEIGHT, CELL_SIZE);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }
    
    // ==================== LEVEL-AUFBAU ====================
    
    /**
     * Platziert alle Wände, Gegner, Spezialzonen und das Ziel im Level.
     * 
     * Layout-Logik:
     * Das Level besteht aus vier vertikalen Gängen:
     * 
     * GANG 1 (Links): Startbereich mit BlueDot
     * - Einfache Aufwärmung
     * - 1 BlueDot als erste Herausforderung
     * 
     * GANG 2 (Mitte-Links): WindZone-Bereich
     * - Große WindZone schiebt Spieler nach links
     * - Spieler muss gegen Wind ankämpfen (mehr rechts-Tasten)
     * - Keine zusätzlichen Gegner (Wind ist Herausforderung genug)
     * 
     * GANG 3 (Mitte-Rechts): Follower-Zone
     * - 2 Follower verfolgen Spieler
     * - Enger Gang macht Flucht schwierig
     * - Follower treiben Spieler vorwärts
     * 
     * GANG 4 (Rechts): Finale mit Shooter und BlueDot
     * - Shooter feuert nach unten
     * - BlueDot als zusätzliche Herausforderung
     * - Ziel am Ende
     */
    private void setup() {
        // === WÄNDE ===
        
        // === WAND 1: Trennt Gang 1 von Gang 2 ===
        // Vertikale Wand, lässt oben und unten Durchgänge
        // Höhe: 200 Pixel (Durchgang oben und unten)
        Wall wall1 = new Wall();
        addObject(wall1, 0, 0);
        wall1.placeWall(CORRIDOR_WIDTH, 100, WALL_THICKNESS, 200);
        
        // === WAND 2: Trennt Gang 2 von Gang 3 ===
        // Vertikale Wand, lässt oben und unten Durchgänge
        // Höhe: 200 Pixel (Durchgang oben und unten)
        Wall wall2 = new Wall();
        addObject(wall2, 0, 0);
        wall2.placeWall(CORRIDOR_WIDTH * 2 + WALL_THICKNESS, 100, WALL_THICKNESS, 200);
        
        // === WAND 3: Trennt Gang 3 von Gang 4 ===
        // Vertikale Wand, lässt oben und unten Durchgänge
        // Höhe: 200 Pixel (Durchgang oben und unten)
        Wall wall3 = new Wall();
        addObject(wall3, 0, 0);
        wall3.placeWall(CORRIDOR_WIDTH * 3 + WALL_THICKNESS * 2, 100, WALL_THICKNESS, 200);
        
        // === ZIEL ===
        
        // Ziel unten rechts (am Ende von Gang 4)
        addObject(new TargetArea(), 560, 360);
        
        // === SPEZIALZONEN ===
        
        // === WindZone: Im gesamten Gang 2 ===
        // Schiebt Spieler kontinuierlich nach links (windDx = -1)
        // Spieler muss mehr rechts-Tasten drücken, um voranzukommen
        // Breite: CORRIDOR_WIDTH (gesamter Gang)
        // Höhe: 400 (gesamte Höhe)
        // Zentrum: Mitte von Gang 2
        int windZoneX = CORRIDOR_WIDTH + WALL_THICKNESS + CORRIDOR_WIDTH / 2;
        addObject(new WindZone(WIND_DX, WIND_DY, CORRIDOR_WIDTH, WORLD_HEIGHT), 
                 windZoneX, WORLD_HEIGHT / 2);
        
        // === GEGNER ===
        
        // === GANG 1: BlueDot als Aufwärmung ===
        // Bewegt sich vertikal im ersten Gang
        // Geschwindigkeit: 2 (mittel)
        // Bewegungsbereich: Y=120 bis Y=280
        addObject(new GreenDot(120, 280, 2, 35), CORRIDOR_WIDTH / 2, 200);
        
        // === GANG 2: Keine Gegner ===
        // WindZone ist Herausforderung genug!
        
        // === GANG 3: Follower ===
        
        // Follower 1: Oberer Bereich von Gang 3
        // Verfolgt Spieler aktiv
        // Position: Mitte von Gang 3, Y=120
        int gang3X = CORRIDOR_WIDTH * 2 + WALL_THICKNESS + CORRIDOR_WIDTH / 2;
        addObject(new Follower(), gang3X, 120);
        
        // Follower 2: Unterer Bereich von Gang 3
        // Zweiter Follower für erhöhte Schwierigkeit
        // Position: Mitte von Gang 3, Y=280
        addObject(new Follower(), gang3X, 280);
        
        // === GANG 4: Shooter + BlueDot ===
        
        // Shooter: Oben in Gang 4
        // Schießt nach unten (Richtung 1)
        // Projektile fliegen durch den Gang
        // Position: Mitte von Gang 4, Y=80
        int gang4X = CORRIDOR_WIDTH * 3 + WALL_THICKNESS * 2 + CORRIDOR_WIDTH / 2;
        addObject(new Shooter(SHOOTER_DIRECTION), gang4X, 80);
        
        // BlueDot: Mitte von Gang 4
        // Bewegt sich horizontal (enger Bereich)
        // Geschwindigkeit: 3 (schnell)
        // Bewegungsbereich: X=gang4X-40 bis X=gang4X+40
        addObject(new BlueDot(gang4X - 40, gang4X + 40, 3), gang4X, 250);
    }
    
    // ==================== HUD-VERWALTUNG ====================
    
    /**
     * Initialisiert das HUD (Heads-Up Display) für dieses Level.
     */
    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) {
            p.resetLives();
        }
        showHUD(5);
    }
    
    /**
     * Aktualisiert die HUD-Anzeige mit Level-Nummer und Leben.
     * 
     * @param lives Aktuelle Anzahl Leben des Spielers
     */
    @Override
    public void showHUD(int lives) {
        showText("Level: 4", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }
    
    /**
     * Gibt den Namen dieses Levels zurück.
     * 
     * @return Der Level-Name "Level 4"
     */
    @Override
    public String levelName() {
        return "Level 4 - Wind Tunnel";
    }
}
