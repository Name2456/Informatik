import greenfoot.*;

/**
 * InvertZone - Zone mit invertierter Steuerung.
 * 
 * Die InvertZone ist eine spezielle Zone, die die Steuerung des Spielers
 * umkehrt. Innerhalb dieser Zone bewirken die Pfeiltasten das Gegenteil
 * von dem, was sie normalerweise tun. Dies ist eine der schwierigsten
 * Mechaniken im Spiel!
 * 
 * Funktionalität:
 * - Invertiert alle Steuerungseingaben des Spielers
 * - Links-Taste → Bewegung nach rechts
 * - Rechts-Taste → Bewegung nach links
 * - Oben-Taste → Bewegung nach unten
 * - Unten-Taste → Bewegung nach oben
 * 
 * Interaktion:
 * - Player prüft in act() auf Kollision mit InvertZone
 * - Player setzt invert-Flag auf true
 * - Player berechnet Bewegung mit invertierten Vorzeichen
 * - InvertZone selbst ist passiv (tut nichts in act())
 * 
 * Verwendungsbeispiel:
 * 
 * <pre>
 * // InvertZone im mittleren Bereich (wie in Level 5)
 * InvertZone invertZone = new InvertZone(120, 400);
 * addObject(invertZone, 315, 200);
 * </pre>
 * 
 * Strategische Bedeutung:
 * - Höchste Schwierigkeit: Erfordert mentale Anpassung
 * - Spieler muss umdenken (gegen Intuition handeln)
 * - Besonders schwierig in Kombination mit Gegnern
 * - Gut für finale Level als ultimative Herausforderung
 * 
 * Level-Design-Tipps:
 * - Verwende InvertZone sparsam (nur in schweren Leveln)
 * - Mache die Zone visuell deutlich erkennbar (lila/violett)
 * - Gib dem Spieler Zeit, sich anzupassen (nicht sofort Gegner)
 * - Kombiniere mit langsamen Gegnern (nicht mit Followern!)
 * 
 * Empfohlene Größe:
 * - Klein (50-100 Pixel): Kurze Herausforderung
 * - Mittel (100-150 Pixel): Moderate Herausforderung
 * - Groß (150+ Pixel): Sehr schwierig!
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class InvertZone extends Actor {
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt eine InvertZone mit festgelegter Größe.
     * 
     * Die InvertZone wird als halbtransparentes lila/violettes Rechteck
     * dargestellt, damit der Spieler sie deutlich erkennen kann und sich
     * auf die invertierte Steuerung vorbereiten kann.
     * 
     * Hinweis: Die Parameter width und height werden im aktuellen Code
     * nicht verwendet, da das Bild nicht dynamisch erstellt wird.
     * In einer erweiterten Version könnte hier ein halbtransparentes
     * Rechteck mit der angegebenen Größe erstellt werden.
     * 
     * Empfohlene Farbe: Lila/Violett (RGB: 200, 100, 255) mit Transparenz
     * 
     * @param width  Breite der Zone in Pixeln (empfohlen: 100-200)
     * @param height Höhe der Zone in Pixeln (empfohlen: 100-400)
     */
    public InvertZone(int width, int height) {
        // TODO: Hier könnte ein halbtransparentes Rechteck erstellt werden:
        // GreenfootImage img = new GreenfootImage(width, height);
        // img.setColor(new Color(200, 100, 255, 100));  // Halbtransparentes Lila
        // img.fill();
        // setImage(img);
    }
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen.
     * 
     * Die InvertZone ist passiv und tut nichts. Die Invertierungs-Logik
     * wird im Player implementiert, der auf Kollision mit InvertZone prüft
     * und die Steuerung entsprechend anpasst.
     * 
     * Warum ist die Invertierungs-Logik im Player?
     * - Player hat die Steuerungslogik
     * - Player berechnet die Bewegung
     * - Trennung der Verantwortlichkeiten: InvertZone ist nur ein Marker
     * 
     * Implementierung im Player:
     * <pre>
     * // Im Player.act():
     * boolean invert = false;
     * if (!getIntersectingObjects(InvertZone.class).isEmpty()) {
     *     invert = true;
     * }
     * 
     * // Steuerung berechnen:
     * int sL = invert ? step : -step;   // Links
     * int sR = invert ? -step : step;   // Rechts
     * int sU = invert ? step : -step;   // Oben
     * int sD = invert ? -step : step;   // Unten
     * </pre>
     */
    @Override
    public void act() {
        // Die Zone selbst tut nichts
        // Player prüft Kollision und invertiert Steuerung
    }
}
