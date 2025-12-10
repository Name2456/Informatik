import greenfoot.*;

/**
 * Level3 - Labyrinth mit Pulsaren, BlueDots und GreenDots.
 * 
 * Level3 kombiniert alle bisher gelernten Mechaniken und führt den neuen
 * Gegnertyp Pulsar ein. Das Layout ist komplexer und erfordert präzises
 * Timing und strategisches Denken.
 * 
 * Layout-Beschreibung:
 * - Labyrinth-artiges Design mit mehreren Wänden
 * - Spieler startet oben links (30, 30)
 * - Ziel ist oben rechts (540, 62)
 * - Mehrere Korridore und Engstellen
 * 
 * Gegner-Kombination:
 * - 2 BlueDots (horizontal) im linken Bereich
 * - 2 GreenDots (vertikal) im mittleren Bereich
 * - 2 Pulsare (pulsierend) im oberen Bereich
 * 
 * Neue Mechanik:
 * - Pulsare: Stationäre Gegner, die ihre Größe ändern
 * - Timing ist entscheidend: Spieler muss warten, bis Pulsare klein sind
 * - Kombination verschiedener Gegnertypen erhöht Komplexität
 * 
 * Lösungsstrategie:
 * 1. Nach unten durch den linken Gang (BlueDots vermeiden)
 * 2. Nach rechts durch den mittleren Gang (GreenDots vermeiden)
 * 3. Nach oben durch den rechten Gang (GreenDots vermeiden)
 * 4. Timing für Pulsare abpassen (wenn sie klein sind)
 * 5. Zum Ziel oben rechts
 * 
 * Schwierigkeitsgrad: Mittel-Schwer (neue Gegnerart + Kombination)
 * Geschätzte Zeit: 25-40 Sekunden
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Level3 extends World implements LabeledWorld {
    
    // ==================== KONSTANTEN ====================
    
    /** Breite der Welt in Pixeln */
    private static final int WORLD_WIDTH = 600;
    
    /** Höhe der Welt in Pixeln */
    private static final int WORLD_HEIGHT = 400;
    
    /** Zellgröße (1 = 1 Pixel pro Zelle) */
    private static final int CELL_SIZE = 1;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt Level3 und initialisiert alle Spielelemente.
     * 
     * Ablauf:
     * 1. Welt mit 600x400 Pixeln erstellen
     * 2. Wände, Gegner und Ziel platzieren (setup)
     * 3. Spieler hinzufügen
     * 4. HUD initialisieren (label)
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
     * Das Level ist in mehrere Bereiche unterteilt:
     * 
     * BEREICH 1 (Links): Vertikaler Gang mit BlueDots
     * - Spieler startet hier und muss nach unten navigieren
     * - 2 BlueDots bewegen sich horizontal
     * 
     * BEREICH 2 (Mitte unten): Horizontaler Gang mit GreenDots
     * - Spieler muss nach rechts navigieren
     * - 2 GreenDots bewegen sich vertikal
     * 
     * BEREICH 3 (Rechts): Vertikaler Gang nach oben
     * - Spieler muss nach oben zum Ziel navigieren
     * 
     * BEREICH 4 (Oben): Finale Passage mit Pulsaren
     * - 2 Pulsare blockieren den Weg zum Ziel
     * - Timing ist entscheidend
     */
    private void setup() {
        // === WÄNDE ===
        
        // === WAND 1: Horizontale Barriere oben ===
        // Trennt den oberen Bereich (mit Pulsaren) vom Rest
        // Zwingt Spieler, den Umweg durch das Labyrinth zu nehmen
        Wall wall1 = new Wall();
        addObject(wall1, 0, 0);
        wall1.placeWall(183, 99, 300, 40);
        
        // === WAND 2: Vertikale Barriere Mitte unten ===
        // Trennt den mittleren horizontalen Gang
        // Schafft Engstelle für GreenDots
        Wall wall2 = new Wall();
        addObject(wall2, 0, 0);
        wall2.placeWall(293, 290, 40, 110);
        
        // === WAND 3: Vertikale Barriere rechts ===
        // Begrenzt den rechten vertikalen Gang
        // Zwingt Spieler, durch den engen Gang zu navigieren
        Wall wall3 = new Wall();
        addObject(wall3, 0, 0);
        wall3.placeWall(495, 148, 40, 252);
        
        // === WAND 4: Vertikale Barriere oben Mitte ===
        // Schafft Engstelle im oberen Bereich
        // Spieler muss zwischen dieser Wand und Wand 1 durchpassen
        Wall wall4 = new Wall();
        addObject(wall4, 0, 0);
        wall4.placeWall(297, 12, 40, 80);
        
        // === ZIEL ===
        
        // Ziel oben rechts (hinter den Pulsaren)
        // Spieler muss durch das gesamte Labyrinth navigieren
        addObject(new TargetArea(), 540, 62);
        
        // === GEGNER ===
        
        // === BEREICH 1: BlueDots im linken Gang ===
        
        // BlueDot 1: Oberer Teil des linken Gangs
        // Bewegt sich horizontal zwischen linkem Rand und Wand 1
        // Geschwindigkeit: 2 (mittel)
        addObject(new BlueDot(9, 143, 2), 80, 122);
        
        // BlueDot 2: Unterer Teil des linken Gangs
        // Bewegt sich horizontal im gleichen Bereich
        // Geschwindigkeit: 3 (schneller als BlueDot 1 für Variation)
        addObject(new BlueDot(9, 143, 3), 80, 222);
        
        // === BEREICH 2: GreenDots im mittleren horizontalen Gang ===
        
        // GreenDot 1: Rechter Teil des mittleren Gangs
        // Bewegt sich vertikal zwischen Wand 1 und Wand 2
        // Geschwindigkeit: 2, Größe: 28 (kleiner für erhöhte Schwierigkeit)
        addObject(new GreenDot(182, 251, 2, 28), 452, 220);
        
        // GreenDot 2: Mittlerer Teil des mittleren Gangs
        // Bewegt sich vertikal im gleichen Bereich
        // Geschwindigkeit: 2, Größe: 28
        addObject(new GreenDot(182, 251, 2, 28), 352, 220);
        
        // === BEREICH 3: Pulsare im oberen Bereich ===
        
        // Pulsar 1: Linker Pulsar vor dem Ziel
        // Pulsiert zwischen 20 und 45 Pixeln
        // Delta: 1 (langsame Pulsation für präzises Timing)
        addObject(new Pulsar(20, 45, 1), 348, 83);
        
        // Pulsar 2: Rechter Pulsar vor dem Ziel
        // Pulsiert zwischen 20 und 45 Pixeln
        // Delta: 2 (schnellere Pulsation als Pulsar 1 für Variation)
        // Spieler muss beide Pulsare gleichzeitig beobachten
        addObject(new Pulsar(20, 45, 2), 457, 83);
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
        return "Level 3";
    }
}
