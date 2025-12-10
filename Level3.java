import greenfoot.*;

/**
 * Level3 - "The Maze" - Labyrinth mit Pulsaren und Timing-Herausforderungen.
 * 
 * Level3 ist ein klassisches Labyrinth-Level, das präzises Timing und Navigation
 * erfordert. Der Spieler muss durch enge Gänge navigieren, während Pulsare die
 * Durchgänge temporär blockieren.
 * 
 * Layout-Beschreibung:
 * - U-förmiger Parcours: Links runter → Unten nach rechts → Rechts hoch
 * - Spieler startet oben links (30, 30)
 * - Ziel ist oben rechts (560, 60)
 * - Drei Hauptbereiche mit unterschiedlichen Gegnertypen
 * 
 * Gegner-Strategie:
 * - Linker Gang: 2 BlueDots (horizontal pendelnd)
 * - Unterer Gang: 2 GreenDots (vertikal pendelnd)
 * - Rechter Gang: 2 Pulsare (als Engstellen)
 * 
 * Lösungsstrategie:
 * 1. Nach unten durch den linken Gang (BlueDots vermeiden)
 * 2. Nach rechts durch den unteren Gang (GreenDots vermeiden)
 * 3. Nach oben durch den rechten Gang (Pulsare-Timing abpassen!)
 * 4. Zum Ziel oben rechts
 * 
 * Neue Mechanik:
 * - Pulsare als Engstellen: Spieler muss warten, bis sie klein sind
 * - Kombination von horizontalen, vertikalen und pulsierenden Gegnern
 * 
 * Schwierigkeitsgrad: Mittel-Schwer (Timing + Navigation)
 * Geschätzte Zeit: 30-45 Sekunden
 * 
 * @author Felix Krusch
 * @version 3.0 (Redesigned)
 */
public class Level3 extends World implements LabeledWorld {
    
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
    
    // Linker vertikaler Gang
    /** Breite des linken Gangs */
    private static final int LEFT_CORRIDOR_WIDTH = 120;
    
    // Unterer horizontaler Gang
    /** Höhe des unteren Gangs */
    private static final int BOTTOM_CORRIDOR_HEIGHT = 120;
    
    // Rechter vertikaler Gang
    /** Breite des rechten Gangs */
    private static final int RIGHT_CORRIDOR_WIDTH = 120;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level3 und initialisiert alle Spielelemente.
     */
    public Level3() {
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
     * Das Level ist ein U-förmiges Labyrinth mit drei Hauptbereichen:
     * 
     * BEREICH 1 (Linker Gang): Vertikaler Gang nach unten
     * - Wand rechts begrenzt den Gang
     * - 2 BlueDots bewegen sich horizontal
     * - Spieler muss zwischen BlueDots durchnavigieren
     * 
     * BEREICH 2 (Unterer Gang): Horizontaler Gang nach rechts
     * - Wand oben begrenzt den Gang
     * - 2 GreenDots bewegen sich vertikal
     * - Enger Gang erhöht Schwierigkeit
     * 
     * BEREICH 3 (Rechter Gang): Vertikaler Gang nach oben
     * - Wand links begrenzt den Gang
     * - 2 Pulsare blockieren Engstellen
     * - Timing ist entscheidend!
     */
    private void setup() {
        // === WÄNDE ===
        
        // === WAND 1: Trennt linken Gang vom Rest (vertikal) ===
        // Diese Wand zwingt den Spieler, nach unten zu gehen
        // Höhe: 280 Pixel (lässt unten Platz für Durchgang)
        Wall wall1 = new Wall();
        addObject(wall1, 0, 0);
        wall1.placeWall(LEFT_CORRIDOR_WIDTH, 0, WALL_THICKNESS, 280);
        
        // === WAND 2: Trennt unteren Gang vom oberen Bereich (horizontal) ===
        // Diese Wand zwingt den Spieler, nach rechts zu gehen
        // Breite: 440 Pixel (von linker Wand bis fast zum rechten Rand)
        Wall wall2 = new Wall();
        addObject(wall2, 0, 0);
        wall2.placeWall(LEFT_CORRIDOR_WIDTH, WORLD_HEIGHT - BOTTOM_CORRIDOR_HEIGHT, 
                       440, WALL_THICKNESS);
        
        // === WAND 3: Trennt rechten Gang vom mittleren Bereich (vertikal) ===
        // Diese Wand zwingt den Spieler, nach oben zu gehen
        // Position: Rechts, Höhe: 240 Pixel (lässt unten Platz für Durchgang)
        Wall wall3 = new Wall();
        addObject(wall3, 0, 0);
        wall3.placeWall(WORLD_WIDTH - RIGHT_CORRIDOR_WIDTH - WALL_THICKNESS, 
                       WORLD_HEIGHT - BOTTOM_CORRIDOR_HEIGHT - 240, 
                       WALL_THICKNESS, 240);
        
        // === ZIEL ===
        
        // Ziel oben rechts (am Ende des rechten Gangs)
        // Spieler muss durch alle drei Gänge navigieren
        addObject(new TargetArea(), 560, 60);
        
        // === GEGNER ===
        
        // === BEREICH 1: BlueDots im linken Gang ===
        
        // BlueDot 1: Oberer Teil des linken Gangs
        // Bewegt sich horizontal zwischen linkem Rand und Wand
        // Geschwindigkeit: 2 (mittel)
        // Position: Y=100
        addObject(new BlueDot(10, LEFT_CORRIDOR_WIDTH - 10, 2), 60, 100);
        
        // BlueDot 2: Mittlerer Teil des linken Gangs
        // Bewegt sich horizontal im gleichen Bereich
        // Geschwindigkeit: 3 (schneller für Variation)
        // Position: Y=200
        addObject(new BlueDot(10, LEFT_CORRIDOR_WIDTH - 10, 3), 60, 200);
        
        // === BEREICH 2: GreenDots im unteren Gang ===
        
        // GreenDot 1: Linker Teil des unteren Gangs
        // Bewegt sich vertikal zwischen Wand und unterem Rand
        // Geschwindigkeit: 2, Größe: 30 (kleiner für erhöhte Schwierigkeit)
        // Position: X=220
        addObject(new GreenDot(WORLD_HEIGHT - BOTTOM_CORRIDOR_HEIGHT + 50, 
                              WORLD_HEIGHT - 10, 2, 30), 220, 340);
        
        // GreenDot 2: Mittlerer Teil des unteren Gangs
        // Bewegt sich vertikal im gleichen Bereich
        // Geschwindigkeit: 2, Größe: 30
        // Position: X=380
        addObject(new GreenDot(WORLD_HEIGHT - BOTTOM_CORRIDOR_HEIGHT + 50, 
                              WORLD_HEIGHT - 10, 2, 30), 380, 340);
        
        // === BEREICH 3: Pulsare im rechten Gang ===
        
        // Pulsar 1: Mittlerer Teil des rechten Gangs
        // Pulsiert zwischen 25 und 50 Pixeln
        // Delta: 1 (langsame Pulsation für präzises Timing)
        // Position: X=520, Y=220 (Engstelle!)
        // Spieler muss warten, bis Pulsar klein ist, um durchzukommen
        addObject(new Pulsar(25, 50, 1), 520, 220);
        
        // Pulsar 2: Oberer Teil des rechten Gangs
        // Pulsiert zwischen 25 und 50 Pixeln
        // Delta: 2 (schnellere Pulsation als Pulsar 1 für Variation)
        // Position: X=520, Y=120 (zweite Engstelle!)
        // Kombination beider Pulsare erfordert gutes Timing
        addObject(new Pulsar(25, 50, 2), 520, 120);
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
        showText("Level: 3", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }
    
    /**
     * Gibt den Namen dieses Levels zurück.
     * 
     * @return Der Level-Name "Level 3"
     */
    @Override
    public String levelName() {
        return "Level 3 - The Maze";
    }
}
