/**
 * LabeledWorld - Interface für Welten mit HUD-Anzeige (Heads-Up Display).
 * 
 * Dieses Interface definiert die Methoden, die alle Level-Welten implementieren
 * müssen, um ein konsistentes HUD (Heads-Up Display) zu gewährleisten. Das HUD
 * zeigt wichtige Spielinformationen wie Level-Nummer und verbleibende Leben an.
 * 
 * Zweck des Interfaces:
 * - Definiert einen Vertrag für alle Level-Welten
 * - Ermöglicht dem Player, HUD-Methoden aufzurufen, ohne die konkrete Level-Klasse zu kennen
 * - Gewährleistet Konsistenz über alle Level hinweg
 * - Erleichtert das Hinzufügen neuer Level
 * 
 * Verwendung:
 * Alle Level-Klassen (Level1, Level2, etc.) implementieren dieses Interface:
 * 
 * <pre>
 * public class Level1 extends World implements LabeledWorld {
 *     // ...
 *     
 *     {@literal @}Override
 *     public void showHUD(int lives) {
 *         showText("Level: 1", 70, 20);
 *         showText("Leben: " + lives, 170, 20);
 *     }
 *     
 *     {@literal @}Override
 *     public String levelName() {
 *         return "Level 1";
 *     }
 * }
 * </pre>
 * 
 * Vorteile des Interface-Ansatzes:
 * 
 * Der Player kann das HUD aktualisieren, ohne zu wissen, welches Level gerade läuft:
 * <pre>
 * // Im Player:
 * public void updateHUD() {
 *     if (getWorld() instanceof LabeledWorld) {
 *         ((LabeledWorld)getWorld()).showHUD(lives);
 *     }
 * }
 * </pre>
 * 
 * Ohne Interface müsste der Player jedes Level einzeln prüfen:
 * <pre>
 * // Schlechte Alternative (ohne Interface):
 * if (getWorld() instanceof Level1) {
 *     ((Level1)getWorld()).showHUD(lives);
 * } else if (getWorld() instanceof Level2) {
 *     ((Level2)getWorld()).showHUD(lives);
 * } // ... und so weiter für jedes Level
 * </pre>
 * 
 * Design-Pattern:
 * Dies ist ein Beispiel für das "Interface Segregation Principle" (ISP)
 * aus den SOLID-Prinzipien der objektorientierten Programmierung.
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public interface LabeledWorld {
    
    /**
     * Zeichnet die HUD-Anzeige (Heads-Up Display) mit Level-Nummer und Leben.
     * 
     * Diese Methode wird vom Player aufgerufen, wenn sich die Anzahl der Leben
     * ändert oder das Level neu geladen wird. Sie sollte alle relevanten
     * Spielinformationen anzeigen.
     * 
     * Typische Implementierung:
     * <pre>
     * public void showHUD(int lives) {
     *     showText("Level: " + levelNumber, 70, 20);
     *     showText("Leben: " + lives, 170, 20);
     * }
     * </pre>
     * 
     * Empfohlene HUD-Elemente:
     * - Level-Nummer (z.B. "Level: 1")
     * - Verbleibende Leben (z.B. "Leben: 5")
     * - Optional: Punkte, Zeit, Sammelgegenstände
     * 
     * Empfohlene Position:
     * - Oben links oder oben Mitte
     * - Nicht zu nah am Rand (mindestens 20 Pixel Abstand)
     * - Sollte Spielfeld nicht verdecken
     * 
     * @param lives Aktuelle Anzahl Leben des Spielers (typischerweise 0-5)
     */
    void showHUD(int lives);
    
    /**
     * Gibt den Namen des Levels zurück.
     * 
     * Diese Methode wird verwendet, um den Level-Namen anzuzeigen, z.B.
     * unten links im Bildschirm. Sie hilft dem Spieler zu wissen, in
     * welchem Level er sich befindet.
     * 
     * Typische Implementierung:
     * <pre>
     * public String levelName() {
     *     return "Level 1";
     * }
     * </pre>
     * 
     * Empfohlene Namenskonvention:
     * - Einfache Level: "Level 1", "Level 2", etc.
     * - Thematische Level: "The Beginning", "Wind Tunnel", "Inversion Madness"
     * - Schwierigkeitsgrade: "Easy Mode", "Hard Mode", "Nightmare"
     * 
     * @return Name des Levels als String (z.B. "Level 1")
     */
    String levelName();
}
