import greenfoot.*;

/**
 * Level5 - "Inversion Madness" - Das finale Level mit InvertZone.
 * 
 * Level5 ist das schwierigste Level im Spiel. Es kombiniert alle bisherigen
 * Mechaniken und führt die InvertZone als ultimative Herausforderung ein.
 * Die InvertZone kehrt die Steuerung um, was mentale Anpassung erfordert.
 * 
 * Layout-Beschreibung:
 * - Fünf vertikale Gänge, die nach unten führen
 * - Spieler startet oben links (30, 30)
 * - Ziel ist unten rechts (560, 360)
 * - Große InvertZone in Gang 3 als Hauptherausforderung
 * 
 * Gegner-Strategie:
 * - Gang 1: 1 RandomWalker (unvorhersehbar)
 * - Gang 2: 2 Strider (schnell und gefährlich)
 * - Gang 3: GROSSE InvertZone + 2 GreenDots (Hauptherausforderung!)
 * - Gang 4: 1 Pulsar (Timing)
 * - Gang 5: 1 Shooter + 1 BlueDot (Finale)
 * 
 * Lösungsstrategie:
 * 1. Nach unten durch Gang 1 (RandomWalker ausweichen)
 * 2. Nach rechts in Gang 2 (Strider vermeiden - sie sind schnell!)
 * 3. Nach unten durch Gang 3 (IN der InvertZone mit umgekehrter Steuerung!)
 * 4. Nach rechts in Gang 4 (Pulsar-Timing abpassen)
 * 5. Nach unten durch Gang 5 (Shooter + BlueDot vermeiden)
 * 6. Zum Ziel
 * 
 * Neue Mechanik:
 * - InvertZone als großer Bereich (nicht nur kleiner Fleck)
 * - GreenDots IN der InvertZone (mit umgekehrter Steuerung ausweichen!)
 * - Kombination aller Gegnertypen für maximale Schwierigkeit
 * 
 * Schwierigkeitsgrad: Sehr Schwer (Invertierung + alle Mechaniken)
 * Geschätzte Zeit: 45-60 Sekunden (oder länger beim ersten Versuch!)
 * 
 * @author Felix Krusch
 * @version 3.0 (Redesigned)
 */
public class Level5 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // Wand-Dicke
    /** Standard-Wanddicke */
    private static final int WALL_THICKNESS = 40;
    
    // Gang-Breiten
    /** Breite jedes vertikalen Gangs */
    private static final int CORRIDOR_WIDTH = 100;
    
    // InvertZone-Parameter
    /** Breite der InvertZone (gesamter Gang 3) */
    private static final int INVERT_ZONE_WIDTH = CORRIDOR_WIDTH;
    /** Höhe der InvertZone (fast gesamte Höhe) */
    private static final int INVERT_ZONE_HEIGHT = 350;
    
    // Shooter-Parameter
    /** Schussrichtung: 1 = nach unten */
    private static final int SHOOTER_DIRECTION = 1;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level5 und initialisiert alle Spielelemente.
     */
    public Level5() {
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
     * Das Level besteht aus fünf vertikalen Gängen:
     * 
     * GANG 1 (Links): Startbereich mit RandomWalker
     * - RandomWalker bewegt sich unvorhersehbar
     * - Spieler muss vorsichtig sein
     * 
     * GANG 2 (Mitte-Links): Strider-Zone
     * - 2 Strider bewegen sich schnell und teleportieren
     * - Sehr gefährlich, aber vorhersehbar
     * 
     * GANG 3 (Mitte): GROSSE InvertZone mit GreenDots
     * - Gesamter Gang ist InvertZone!
     * - Steuerung ist umgekehrt (links→rechts, oben→unten)
     * - 2 GreenDots bewegen sich vertikal
     * - SCHWIERIGSTE HERAUSFORDERUNG IM SPIEL!
     * 
     * GANG 4 (Mitte-Rechts): Pulsar-Zone
     * - 1 Pulsar als Engstelle
     * - Timing erforderlich
     * 
     * GANG 5 (Rechts): Finale mit Shooter und BlueDot
     * - Shooter feuert nach unten
     * - BlueDot als letzte Herausforderung
     * - Ziel am Ende
     */
    private void setup() {
        // === WÄNDE ===
        
        // === WAND 1: Trennt Gang 1 von Gang 2 ===
        Wall wall1 = new Wall();
        addObject(wall1, 0, 0);
        wall1.placeWall(CORRIDOR_WIDTH, 80, WALL_THICKNESS, 240);
        
        // === WAND 2: Trennt Gang 2 von Gang 3 (InvertZone) ===
        Wall wall2 = new Wall();
        addObject(wall2, 0, 0);
        wall2.placeWall(CORRIDOR_WIDTH * 2 + WALL_THICKNESS, 80, WALL_THICKNESS, 240);
        
        // === WAND 3: Trennt Gang 3 (InvertZone) von Gang 4 ===
        Wall wall3 = new Wall();
        addObject(wall3, 0, 0);
        wall3.placeWall(CORRIDOR_WIDTH * 3 + WALL_THICKNESS * 2, 80, WALL_THICKNESS, 240);
        
        // === WAND 4: Trennt Gang 4 von Gang 5 ===
        Wall wall4 = new Wall();
        addObject(wall4, 0, 0);
        wall4.placeWall(CORRIDOR_WIDTH * 4 + WALL_THICKNESS * 3, 80, WALL_THICKNESS, 240);
        
        // === ZIEL ===
        
        // Ziel unten rechts (am Ende von Gang 5)
        addObject(new TargetArea(), 560, 360);
        
        // === SPEZIALZONEN ===
        
        // === InvertZone: Im gesamten Gang 3 ===
        // GROSSE InvertZone, die den gesamten Gang 3 ausfüllt
        // Steuerung ist hier umgekehrt:
        // - Links-Taste → Bewegung nach rechts
        // - Rechts-Taste → Bewegung nach links
        // - Oben-Taste → Bewegung nach unten
        // - Unten-Taste → Bewegung nach oben
        // Dies ist die schwierigste Mechanik im Spiel!
        int invertZoneX = CORRIDOR_WIDTH * 2 + WALL_THICKNESS + CORRIDOR_WIDTH / 2;
        addObject(new InvertZone(INVERT_ZONE_WIDTH, INVERT_ZONE_HEIGHT), 
                 invertZoneX, WORLD_HEIGHT / 2);
        
        // === GEGNER ===
        
        // === GANG 1: RandomWalker ===
        // Bewegt sich zufällig im ersten Gang
        // Unvorhersehbar, aber nicht zu gefährlich
        addObject(new RandomWalker(), CORRIDOR_WIDTH / 2, 200);
        
        // === GANG 2: Strider ===
        
        // Strider 1: Oberer Bereich von Gang 2
        // Bewegt sich schnell in zufällige Richtung
        // Teleportiert am Rand
        int gang2X = CORRIDOR_WIDTH + WALL_THICKNESS + CORRIDOR_WIDTH / 2;
        addObject(new Strider(), gang2X, 140);
        
        // Strider 2: Unterer Bereich von Gang 2
        // Zweiter Strider für erhöhte Schwierigkeit
        addObject(new Strider(), gang2X, 260);
        
        // === GANG 3: GreenDots IN der InvertZone ===
        
        // GreenDot 1: Oberer Bereich der InvertZone
        // Bewegt sich vertikal
        // Geschwindigkeit: 2, Größe: 30
        // ACHTUNG: Spieler muss mit umgekehrter Steuerung ausweichen!
        addObject(new GreenDot(100, 300, 2, 30), invertZoneX, 150);
        
        // GreenDot 2: Unterer Bereich der InvertZone
        // Bewegt sich vertikal
        // Geschwindigkeit: 2, Größe: 30
        // Kombination beider GreenDots in InvertZone = SEHR SCHWER!
        addObject(new GreenDot(100, 300, 2, 30), invertZoneX, 250);
        
        // === GANG 4: Pulsar ===
        
        // Pulsar: Mitte von Gang 4
        // Pulsiert zwischen 25 und 50 Pixeln
        // Delta: 2 (mittlere Geschwindigkeit)
        // Timing erforderlich, um durchzukommen
        int gang4X = CORRIDOR_WIDTH * 3 + WALL_THICKNESS * 2 + CORRIDOR_WIDTH / 2;
        addObject(new Pulsar(25, 50, 2), gang4X, 200);
        
        // === GANG 5: Shooter + BlueDot ===
        
        // Shooter: Oben in Gang 5
        // Schießt nach unten (Richtung 1)
        // Projektile fliegen durch den Gang
        int gang5X = CORRIDOR_WIDTH * 4 + WALL_THICKNESS * 3 + CORRIDOR_WIDTH / 2;
        addObject(new Shooter(SHOOTER_DIRECTION), gang5X, 70);
        
        // BlueDot: Mitte von Gang 5
        // Bewegt sich horizontal (enger Bereich)
        // Geschwindigkeit: 3 (schnell)
        // Letzte Herausforderung vor dem Ziel!
        addObject(new BlueDot(gang5X - 35, gang5X + 35, 3), gang5X, 250);
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
        showText("Level: 5", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }
    
    /**
     * Gibt den Namen dieses Levels zurück.
     * 
     * @return Der Level-Name "Level 5"
     */
    @Override
    public String levelName() {
        return "Level 5 - Inversion Madness";
    }
}
