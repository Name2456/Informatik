import greenfoot.*;

/**
 * Level4 - Seitenwind, Shooter und Follower.
 * 
 * Level4 führt drei neue Spielmechaniken ein:
 * 1. WindZone: Eine Zone, die den Spieler kontinuierlich zur Seite schiebt
 * 2. Shooter: Stationäre Gegner, die Projektile abfeuern
 * 3. Follower: Gegner, die den Spieler aktiv verfolgen
 * 
 * Diese Kombination erfordert völlig neue Strategien und macht das Level
 * deutlich schwieriger als die vorherigen.
 * 
 * Layout-Beschreibung:
 * - Labyrinth-artiges Design mit vier vertikalen Wänden
 * - Große WindZone im mittleren Bereich (schiebt nach links)
 * - Spieler startet oben links (30, 30)
 * - Ziel ist unten rechts (560, 360)
 * 
 * Gegner-Kombination:
 * - 1 Shooter (schießt nach rechts)
 * - 2 Follower (verfolgen den Spieler)
 * - 1 BlueDot (zusätzliche Herausforderung)
 * 
 * Neue Mechaniken:
 * - WindZone: Spieler wird kontinuierlich nach links geschoben
 *   → Muss gegen den Wind ankämpfen (mehr rechts-Tasten drücken)
 * - Shooter: Feuert regelmäßig Projektile ab
 *   → Spieler muss Schussmuster lernen und Timing beachten
 * - Follower: Bewegen sich auf Spieler zu
 *   → Spieler muss ständig in Bewegung bleiben
 * 
 * Lösungsstrategie:
 * 1. Nach rechts durch die erste Lücke
 * 2. Nach unten (Shooter-Projektile vermeiden)
 * 3. Durch WindZone navigieren (gegen Wind ankämpfen)
 * 4. Follower abschütteln durch schnelle Bewegung
 * 5. Nach rechts zum Ziel
 * 
 * Schwierigkeitsgrad: Schwer (neue Mechaniken + aktive Verfolgung)
 * Geschätzte Zeit: 30-50 Sekunden
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Level4 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // WindZone-Parameter
    /** Windstärke in X-Richtung (negativ = nach links) */
    private static final int WIND_DX = -1;
    /** Windstärke in Y-Richtung (0 = kein vertikaler Wind) */
    private static final int WIND_DY = 0;
    /** Breite der WindZone */
    private static final int WIND_WIDTH = 140;
    /** Höhe der WindZone (gesamte Höhe der Welt) */
    private static final int WIND_HEIGHT = 400;
    
    // Shooter-Parameter
    /** Schussrichtung: 3 = nach rechts */
    private static final int SHOOTER_DIRECTION = 3;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level4 und initialisiert alle Spielelemente.
     * 
     * Ablauf:
     * 1. Welt mit 600x400 Pixeln erstellen
     * 2. Wände, Gegner, Spezialzonen und Ziel platzieren (setup)
     * 3. Spieler hinzufügen
     * 4. HUD initialisieren (label)
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
     * Das Level ist in mehrere vertikale Gänge unterteilt:
     * 
     * GANG 1 (Links): Startbereich
     * - Spieler startet hier
     * - Relativ sicher, aber Follower können hierher kommen
     * 
     * GANG 2 (Mitte-Links): WindZone-Bereich
     * - Große WindZone schiebt Spieler nach links
     * - Spieler muss gegen Wind ankämpfen
     * - Shooter feuert Projektile durch diesen Bereich
     * 
     * GANG 3 (Mitte-Rechts): Gefährlicher Bereich
     * - Follower patrouillieren hier
     * - Enger Durchgang
     * 
     * GANG 4 (Rechts): Finale Passage
     * - BlueDot als letzte Herausforderung
     * - Führt zum Ziel
     */
    private void setup() {
        // === WÄNDE ===
        
        // === WAND 1: Erste vertikale Barriere ===
        // Trennt Startbereich von WindZone
        // Lücke zwischen Y=250 und Y=300 für Durchgang
        Wall wall1 = new Wall();
        addObject(wall1, 0, 0);
        wall1.placeWall(150, 0, 50, 250);
        
        // === WAND 1B: Unterer Teil der ersten Barriere ===
        // Fortsetzung von Wand 1 nach der Lücke
        Wall wall1b = new Wall();
        addObject(wall1b, 0, 0);
        wall1b.placeWall(150, 300, 50, 100);
        
        // === WAND 2: Zweite vertikale Barriere ===
        // Trennt WindZone vom mittleren Bereich
        // Lücke oben für Durchgang
        Wall wall2 = new Wall();
        addObject(wall2, 0, 0);
        wall2.placeWall(300, 150, 50, 250);
        
        // === WAND 3: Dritte vertikale Barriere ===
        // Trennt mittleren Bereich vom finalen Gang
        // Lücke zwischen Y=250 und Y=400 für Durchgang
        Wall wall3 = new Wall();
        addObject(wall3, 0, 0);
        wall3.placeWall(450, 0, 50, 250);
        
        // === ZIEL ===
        
        // Ziel unten rechts (im finalen Gang)
        // Spieler muss durch alle Gänge navigieren
        addObject(new TargetArea(), 560, 360);
        
        // === SPEZIALZONEN ===
        
        // === WindZone: Seitenwind nach links ===
        // Positioniert im zweiten Gang (zwischen Wand 1 und Wand 2)
        // Schiebt Spieler kontinuierlich nach links (windDx = -1)
        // Spieler muss mehr rechts-Tasten drücken, um voranzukommen
        // Breite: 140 Pixel, Höhe: gesamte Welt (400 Pixel)
        // Zentrum bei X=225, Y=200
        addObject(new WindZone(WIND_DX, WIND_DY, WIND_WIDTH, WIND_HEIGHT), 225, 200);
        
        // === GEGNER ===
        
        // === Shooter: Stationärer Gegner mit Projektilen ===
        // Positioniert im ersten Gang unten
        // Schießt nach rechts (Richtung 3) durch die WindZone
        // Projektile fliegen durch die WindZone und gefährden den Spieler
        // Position: X=170, Y=320 (nahe der unteren Lücke)
        addObject(new Shooter(SHOOTER_DIRECTION), 170, 320);
        
        // === Follower: Verfolgende Gegner ===
        
        // Follower 1: Im mittleren Bereich oben
        // Verfolgt Spieler aktiv, sobald er in Reichweite ist
        // Position: X=400, Y=100
        addObject(new Follower(), 400, 100);
        
        // Follower 2: Im rechten Bereich
        // Zweiter Follower für erhöhte Schwierigkeit
        // Position: X=520, Y=200
        addObject(new Follower(), 520, 200);
        
        // === BlueDot: Zusätzliche Herausforderung ===
        // Im finalen Gang vor dem Ziel
        // Bewegt sich horizontal zwischen X=360 und X=440
        // Geschwindigkeit: 3 (schnell)
        // Position: X=400, Y=300
        addObject(new BlueDot(360, 440, 3), 400, 300);
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
        return "Level 4";
    }
}
