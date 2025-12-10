import greenfoot.*;

/**
 * Level5 - Das finale Level mit allen Mechaniken.
 * 
 * Level5 ist das schwierigste und letzte Level des Spiels. Es kombiniert
 * alle bisher eingeführten Mechaniken und Gegnertypen in einem kniffligen
 * Finale. Die InvertZone fügt eine zusätzliche Herausforderung hinzu,
 * die präzise Kontrolle und Anpassungsfähigkeit erfordert.
 * 
 * Layout-Beschreibung:
 * - Enger Parcours mit vier vertikalen Wänden
 * - Große InvertZone im mittleren Bereich (verkehrte Steuerung!)
 * - Spieler startet oben links (30, 30)
 * - Ziel ist ganz rechts unten (565, 360)
 * 
 * Gegner-Kombination (alle Typen!):
 * - 1 RandomWalker (zufällige Bewegung)
 * - 2 Strider (teleportierende Gegner)
 * - 1 GreenDot (vertikal)
 * - 1 Pulsar (pulsierend)
 * - 1 Shooter (Projektile)
 * 
 * Neue Mechanik:
 * - InvertZone: Steuerung wird umgekehrt
 *   → Links-Taste bewegt nach rechts
 *   → Rechts-Taste bewegt nach links
 *   → Oben-Taste bewegt nach unten
 *   → Unten-Taste bewegt nach oben
 *   → Erfordert mentale Anpassung und präzise Kontrolle
 * 
 * Lösungsstrategie:
 * 1. Nach unten durch den ersten Gang (RandomWalker vermeiden)
 * 2. Nach rechts in den zweiten Gang (Strider vermeiden)
 * 3. ACHTUNG: InvertZone betreten (Steuerung umkehren!)
 * 4. Durch InvertZone navigieren (mit invertierter Steuerung)
 * 5. Nach oben im dritten Gang (GreenDot vermeiden)
 * 6. Durch den vierten Gang (Pulsar-Timing beachten)
 * 7. Shooter-Projektile vermeiden
 * 8. Zum Ziel ganz rechts
 * 
 * Schwierigkeitsgrad: Sehr Schwer (alle Mechaniken + InvertZone)
 * Geschätzte Zeit: 40-60 Sekunden
 * 
 * Besonderheit: Nach diesem Level erscheint der Gratulationstext!
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Level5 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // InvertZone-Parameter
    /** Breite der InvertZone */
    private static final int INVERT_WIDTH = 120;
    /** Höhe der InvertZone (gesamte Höhe der Welt) */
    private static final int INVERT_HEIGHT = 400;
    
    // Shooter-Parameter
    /** Schussrichtung: 2 = nach links */
    private static final int SHOOTER_DIRECTION = 2;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level5 und initialisiert alle Spielelemente.
     * 
     * Ablauf:
     * 1. Welt mit 600x400 Pixeln erstellen
     * 2. Wände, Gegner, Spezialzonen und Ziel platzieren (setup)
     * 3. Spieler hinzufügen
     * 4. HUD initialisieren (label)
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
     * Das Level ist in fünf enge vertikale Gänge unterteilt:
     * 
     * GANG 1 (Links): Startbereich mit RandomWalker
     * - Spieler startet hier
     * - RandomWalker bewegt sich zufällig (unvorhersehbar)
     * 
     * GANG 2 (Mitte-Links): Strider-Zone
     * - 2 Strider teleportieren am Rand
     * - Sehr gefährlich, da Strider schnell sind
     * 
     * GANG 3 (Mitte): InvertZone mit GreenDot
     * - Große InvertZone (verkehrte Steuerung!)
     * - GreenDot bewegt sich vertikal
     * - Kombination ist sehr schwierig
     * 
     * GANG 4 (Mitte-Rechts): Pulsar-Passage
     * - Pulsar pulsiert vor dem Durchgang
     * - Timing ist entscheidend
     * 
     * GANG 5 (Rechts): Finale Passage mit Shooter
     * - Shooter feuert nach links
     * - Ziel ist am Ende dieses Gangs
     */
    private void setup() {
        // === WÄNDE ===
        
        // === WAND 1: Erste vertikale Barriere ===
        // Trennt Startbereich (Gang 1) von Strider-Zone (Gang 2)
        // Lücke zwischen Y=80 und Y=320 für Durchgang
        Wall wall1 = new Wall();
        addObject(wall1, 0, 0);
        wall1.placeWall(120, 80, 40, 240);
        
        // === WAND 2: Zweite vertikale Barriere ===
        // Trennt Strider-Zone (Gang 2) von InvertZone (Gang 3)
        // Lücke zwischen Y=0 und Y=280 für Durchgang
        Wall wall2 = new Wall();
        addObject(wall2, 0, 0);
        wall2.placeWall(250, 0, 40, 280);
        
        // === WAND 3: Dritte vertikale Barriere ===
        // Trennt InvertZone (Gang 3) von Pulsar-Passage (Gang 4)
        // Lücke zwischen Y=120 und Y=400 für Durchgang
        Wall wall3 = new Wall();
        addObject(wall3, 0, 0);
        wall3.placeWall(380, 120, 40, 280);
        
        // === WAND 4: Vierte vertikale Barriere ===
        // Trennt Pulsar-Passage (Gang 4) von finaler Passage (Gang 5)
        // Lücke zwischen Y=0 und Y=250 für Durchgang
        Wall wall4 = new Wall();
        addObject(wall4, 0, 0);
        wall4.placeWall(510, 0, 40, 250);
        
        // === ZIEL ===
        
        // Ziel ganz rechts unten (am Ende von Gang 5)
        // Spieler muss durch alle fünf Gänge navigieren
        addObject(new TargetArea(), 565, 360);
        
        // === SPEZIALZONEN ===
        
        // === InvertZone: Verkehrte Steuerung ===
        // Positioniert im dritten Gang (zwischen Wand 2 und Wand 3)
        // In dieser Zone ist die Steuerung komplett invertiert:
        // - Links → Rechts
        // - Rechts → Links
        // - Oben → Unten
        // - Unten → Oben
        // Breite: 120 Pixel, Höhe: gesamte Welt (400 Pixel)
        // Zentrum bei X=315, Y=200
        // Diese Zone ist die größte Herausforderung des Levels!
        addObject(new InvertZone(INVERT_WIDTH, INVERT_HEIGHT), 315, 200);
        
        // === GEGNER ===
        
        // === GANG 1: RandomWalker ===
        // Bewegt sich zufällig in alle Richtungen
        // Unvorhersehbar, aber langsam
        // Position: X=60, Y=200 (Mitte des ersten Gangs)
        addObject(new RandomWalker(), 60, 200);
        
        // === GANG 2: Strider (2 Stück) ===
        
        // Strider 1: Oberer Bereich
        // Bewegt sich geradeaus und teleportiert am Rand
        // Position: X=185, Y=150
        addObject(new Strider(), 185, 150);
        
        // Strider 2: Unterer Bereich
        // Zweiter Strider für erhöhte Schwierigkeit
        // Position: X=185, Y=350
        addObject(new Strider(), 185, 350);
        
        // === GANG 3: GreenDot in der InvertZone ===
        // Bewegt sich vertikal in der InvertZone
        // Kombination mit invertierter Steuerung ist sehr schwierig!
        // Bewegt sich zwischen Y=130 und Y=390
        // Geschwindigkeit: 3 (schnell), Größe: 25 (klein)
        // Position: X=315, Y=250 (Mitte der InvertZone)
        addObject(new GreenDot(130, 390, 3, 25), 315, 250);
        
        // === GANG 4: Pulsar ===
        // Pulsiert vor dem Durchgang zu Gang 5
        // Spieler muss Timing abpassen (wenn Pulsar klein ist)
        // Pulsiert zwischen 25 und 55 Pixeln
        // Delta: 2 (mittlere Geschwindigkeit)
        // Position: X=445, Y=300
        addObject(new Pulsar(25, 55, 2), 445, 300);
        
        // === GANG 5: Shooter ===
        // Stationärer Gegner am Ende des Levels
        // Schießt nach links (Richtung 2) durch Gang 5
        // Spieler muss Projektile vermeiden, während er zum Ziel läuft
        // Position: X=530, Y=50 (oben im Gang)
        addObject(new Shooter(SHOOTER_DIRECTION), 530, 50);
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
        showText("Level: 5", 90, 20);
        showText("Leben: " + lives, 200, 20);
    }
    
    /**
     * Gibt den Namen dieses Levels zurück.
     * 
     * @return Der Level-Name "Level 5"
     */
    @Override
    public String levelName() {
        return "Level 5";
    }
}
