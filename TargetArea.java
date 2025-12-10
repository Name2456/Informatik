import greenfoot.*;

/**
 * TargetArea - Zielbereich, der das Level abschließt.
 * 
 * Die TargetArea ist das Ziel, das der Spieler erreichen muss, um das
 * aktuelle Level zu beenden und zum nächsten Level zu gelangen. Sie ist
 * statisch (bewegt sich nicht) und wird typischerweise am Ende des Levels
 * platziert.
 * 
 * Funktionalität:
 * - Markiert das Ziel des Levels
 * - Wird vom Player erkannt (Player prüft auf TargetArea-Kollision)
 * - Löst Level-Wechsel aus, wenn Spieler sie berührt
 * - Visuell erkennbar (orangefarbenes Feld)
 * 
 * Interaktion:
 * - Player prüft in act() auf Kollision mit TargetArea
 * - Bei Kollision: Sound abspielen und nextLevel() aufrufen
 * - TargetArea selbst ist passiv (tut nichts in act())
 * 
 * Platzierung:
 * Die TargetArea wird typischerweise am Ende des Levels platziert:
 * 
 * <pre>
 * // Beispiel: Ziel unten rechts
 * addObject(new TargetArea(), 560, 360);
 * 
 * // Beispiel: Ziel oben rechts
 * addObject(new TargetArea(), 540, 62);
 * </pre>
 * 
 * Empfohlene Größe:
 * - Standard: 40x40 Pixel (wie im Bild definiert)
 * - Sollte groß genug sein, um leicht getroffen zu werden
 * - Sollte klein genug sein, um nicht zu einfach zu sein
 * 
 * Level-Design-Tipps:
 * - Platziere die TargetArea so, dass der Spieler sie sehen kann
 * - Mache den Weg zum Ziel herausfordernd, aber fair
 * - In schwierigen Leveln: Ziel in sicherer Zone (keine Gegner direkt daneben)
 * - In einfachen Leveln: Ziel kann näher an Gegnern sein
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class TargetArea extends Actor {
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen.
     * 
     * Die TargetArea ist statisch und tut nichts. Die Kollisionsprüfung
     * erfolgt im Player, nicht hier.
     * 
     * Warum ist die Kollisionsprüfung im Player?
     * - Player hat die Logik für Level-Wechsel
     * - Player spielt den Sound ab
     * - Player ruft nextLevel() auf
     * - Trennung der Verantwortlichkeiten: TargetArea ist nur ein Marker
     */
    @Override
    public void act() {
        // Statisches Ziel - keine Aktion erforderlich
        // Kollisionsprüfung erfolgt im Player
    }
}
