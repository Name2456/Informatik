/**
 * LabeledWorld – Interface für Welten mit HUD-Anzeige
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public interface LabeledWorld {
    
    /**
     * Zeichnet die HUD-Anzeige (Level, Leben).
     * 
     * @param lives Aktuelle Lebenanzahl
     */
    void showHUD(int lives);
    
    /**
     * Gibt den Level-Namen zurück.
     * 
     * @return Name des Levels
     */
    String levelName();
}