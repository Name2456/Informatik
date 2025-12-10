import greenfoot.*;

/**
 * Wall - Statisches Hindernis im Spiel.
 * 
 * Die Wall-Klasse repräsentiert Wände und Hindernisse, die den Spieler und
 * Gegner blockieren. Wände sind statisch (bewegen sich nicht) und können
 * in beliebiger Größe und Position platziert werden.
 * 
 * Funktionalität:
 * - Blockiert Spieler-Bewegung (Player prüft auf Wall-Kollision)
 * - Blockiert Gegner-Bewegung (Gegner prallen ab oder kehren um)
 * - Stoppt Projektile (Bullets verschwinden bei Wandkollision)
 * - Kann in beliebiger Größe erstellt werden
 * - Position wird über obere linke Ecke definiert (nicht Mittelpunkt!)
 * 
 * Verwendung:
 * Die placeWall()-Methode ist die Hauptmethode zum Platzieren von Wänden:
 * 
 * <pre>
 * Wall wall = new Wall();
 * addObject(wall, 0, 0);  // Temporäre Position
 * wall.placeWall(100, 50, 200, 30);  // Wand bei (100,50) mit Größe 200x30
 * </pre>
 * 
 * Koordinatensystem:
 * - (x, y) ist die obere linke Ecke der Wand
 * - Greenfoot platziert Actors normalerweise am Mittelpunkt
 * - placeWall() rechnet automatisch um: Mittelpunkt = (x + width/2, y + height/2)
 * 
 * Empfohlene Wanddicken:
 * - Dünne Wände: 30-40 Pixel
 * - Standard-Wände: 40-60 Pixel
 * - Dicke Wände: 60-80 Pixel
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Wall extends Actor {
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen.
     * 
     * Wände sind statisch und tun nichts, daher ist diese Methode leer.
     * Sie wird überschrieben, um zu dokumentieren, dass Wände sich nicht bewegen.
     */
    @Override
    public void act() {
        // Statisches Hindernis - keine Aktion erforderlich
    }
    
    // ==================== HILFSMETHODEN ====================
    
    /**
     * Setzt die Größe des Wand-Bildes.
     * 
     * Diese Methode skaliert das Bild der Wand auf die angegebene Größe.
     * Das Bild wird direkt verändert (nicht kopiert), daher sollte diese
     * Methode nur einmal pro Wand aufgerufen werden.
     * 
     * @param width  Breite in Pixeln (empfohlen: 30-500)
     * @param height Höhe in Pixeln (empfohlen: 30-500)
     */
    public void setSize(int width, int height) {
        GreenfootImage wallImg = getImage();
        wallImg.scale(width, height);
    }
    
    /**
     * Setzt die Position der Wand basierend auf der oberen linken Ecke.
     * 
     * Wichtig: Greenfoot platziert Actors normalerweise am Mittelpunkt.
     * Diese Methode rechnet die Position so um, dass (x, y) die obere
     * linke Ecke der Wand ist.
     * 
     * Berechnung:
     * - Mittelpunkt X = x + (Breite / 2)
     * - Mittelpunkt Y = y + (Höhe / 2)
     * 
     * Voraussetzung: setSize() muss vorher aufgerufen worden sein,
     * damit die Bildgröße korrekt ist.
     * 
     * @param x X-Koordinate der oberen linken Ecke
     * @param y Y-Koordinate der oberen linken Ecke
     */
    public void setPos(int x, int y) {
        GreenfootImage wallImg = getImage();
        // Mittelpunkt berechnen: obere linke Ecke + halbe Größe
        int centerX = x + wallImg.getWidth() / 2;
        int centerY = y + wallImg.getHeight() / 2;
        setLocation(centerX, centerY);
    }
    
    /**
     * Platziert diese Wand mit Position und Größe in einem Aufruf.
     * 
     * Dies ist die Hauptmethode zum Platzieren von Wänden. Sie kombiniert
     * setSize() und setPos() in einem praktischen Aufruf.
     * 
     * Verwendungsbeispiel:
     * <pre>
     * // Horizontale Wand oben
     * Wall topWall = new Wall();
     * addObject(topWall, 0, 0);
     * topWall.placeWall(0, 100, 540, 60);
     * 
     * // Vertikale Wand links
     * Wall leftWall = new Wall();
     * addObject(leftWall, 0, 0);
     * leftWall.placeWall(100, 0, 80, 340);
     * </pre>
     * 
     * Koordinatensystem:
     * - (0, 0) ist oben links in der Welt
     * - X-Achse geht nach rechts
     * - Y-Achse geht nach unten
     * 
     * @param x      X-Koordinate der oberen linken Ecke
     * @param y      Y-Koordinate der oberen linken Ecke
     * @param width  Breite der Wand in Pixeln
     * @param height Höhe der Wand in Pixeln
     */
    public void placeWall(int x, int y, int width, int height) {
        // Schritt 1: Größe setzen
        setSize(width, height);
        // Schritt 2: Position setzen (basierend auf oberer linker Ecke)
        setPos(x, y);
    }
}
