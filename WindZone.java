import greenfoot.*;

/**
 * WindZone - Unsichtbare Zone, die den Spieler kontinuierlich verschiebt.
 * 
 * Die WindZone ist eine spezielle Zone, die den Spieler in eine bestimmte
 * Richtung "schiebt" (wie Wind oder Strömung). Sie ist halbtransparent
 * sichtbar, damit der Spieler sie erkennen kann.
 * 
 * Funktionalität:
 * - Verschiebt den Spieler kontinuierlich in eine festgelegte Richtung
 * - Wirkt zusätzlich zur normalen Spieler-Bewegung
 * - Kann horizontal, vertikal oder diagonal wirken
 * - Mehrere WindZones können sich addieren
 * 
 * Interaktion:
 * - Player prüft in act() auf Kollision mit WindZone
 * - Player addiert windDx und windDy zu seiner Bewegung
 * - WindZone selbst ist passiv (tut nichts in act())
 * 
 * Verwendungsbeispiele:
 * 
 * <pre>
 * // Seitenwind nach links (wie in Level 4)
 * WindZone leftWind = new WindZone(-1, 0, 140, 400);
 * addObject(leftWind, 225, 200);
 * 
 * // Aufwind nach oben
 * WindZone upWind = new WindZone(0, -1, 100, 200);
 * addObject(upWind, 300, 200);
 * 
 * // Diagonaler Wind (nach rechts-unten)
 * WindZone diagWind = new WindZone(1, 1, 150, 150);
 * addObject(diagWind, 400, 300);
 * </pre>
 * 
 * Strategische Bedeutung:
 * - Erhöht Schwierigkeit: Spieler muss gegen Wind ankämpfen
 * - Erfordert Anpassung der Steuerung (mehr Tasten in Gegenrichtung)
 * - Kann Bereiche schwerer zugänglich machen
 * - Kombiniert gut mit anderen Gegnern für erhöhte Komplexität
 * 
 * Empfohlene Werte:
 * - Leichter Wind: windDx/windDy = ±1
 * - Mittlerer Wind: windDx/windDy = ±2
 * - Starker Wind: windDx/windDy = ±3 (sehr schwierig!)
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class WindZone extends Actor {
    
    // ==================== ATTRIBUTE ====================
    
    /** 
     * Wind-Stärke in X-Richtung (horizontal).
     * Negativ = Wind nach links, Positiv = Wind nach rechts, 0 = kein horizontaler Wind
     * 
     * Öffentlich, damit Player direkt darauf zugreifen kann.
     */
    public int windDx;
    
    /** 
     * Wind-Stärke in Y-Richtung (vertikal).
     * Negativ = Wind nach oben, Positiv = Wind nach unten, 0 = kein vertikaler Wind
     * 
     * Öffentlich, damit Player direkt darauf zugreifen kann.
     */
    public int windDy;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt eine WindZone mit festgelegter Windrichtung und Größe.
     * 
     * Die WindZone wird als halbtransparentes blaues Rechteck dargestellt,
     * damit der Spieler sie sehen und vermeiden kann (oder sich darauf
     * vorbereiten kann).
     * 
     * Windrichtungen:
     * - Nach links: windDx = -1 (oder stärker: -2, -3)
     * - Nach rechts: windDx = +1 (oder stärker: +2, +3)
     * - Nach oben: windDy = -1 (oder stärker: -2, -3)
     * - Nach unten: windDy = +1 (oder stärker: +2, +3)
     * - Diagonal: Beide Werte != 0
     * 
     * Hinweis: Die Parameter width und height werden im aktuellen Code
     * nicht verwendet, da das Bild nicht dynamisch erstellt wird.
     * In einer erweiterten Version könnte hier ein halbtransparentes
     * Rechteck mit der angegebenen Größe erstellt werden.
     * 
     * @param windDx Verschiebung pro Takt in X-Richtung (empfohlen: -3 bis +3)
     * @param windDy Verschiebung pro Takt in Y-Richtung (empfohlen: -3 bis +3)
     * @param width  Breite der Zone in Pixeln (für zukünftige Verwendung)
     * @param height Höhe der Zone in Pixeln (für zukünftige Verwendung)
     */
    public WindZone(int windDx, int windDy, int width, int height) {
        this.windDx = windDx;
        this.windDy = windDy;
        
        // TODO: Hier könnte ein halbtransparentes Rechteck erstellt werden:
        // GreenfootImage img = new GreenfootImage(width, height);
        // img.setColor(new Color(100, 150, 255, 100));  // Halbtransparentes Blau
        // img.fill();
        // setImage(img);
    }
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen.
     * 
     * Die WindZone ist passiv und tut nichts. Die Wind-Logik wird im Player
     * implementiert, der auf Kollision mit WindZone prüft und windDx/windDy
     * zu seiner Bewegung addiert.
     * 
     * Warum ist die Wind-Logik im Player?
     * - Player hat die Bewegungslogik
     * - Player kann mehrere WindZones gleichzeitig berücksichtigen (Effekte addieren)
     * - Trennung der Verantwortlichkeiten: WindZone ist nur ein Marker mit Parametern
     */
    @Override
    public void act() {
        // Die Zone selbst tut nichts
        // Player prüft Kollision und wendet windDx/windDy an
    }
}
