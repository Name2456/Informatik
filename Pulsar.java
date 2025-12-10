import greenfoot.*;

/**
 * Pulsar - Pulsierender Gegner, der seine Größe kontinuierlich ändert.
 * 
 * Der Pulsar ist ein stationärer Gegner, der sich nicht bewegt, aber seine
 * Größe kontinuierlich zwischen einem Minimum und Maximum ändert. Dies macht
 * ihn zu einer einzigartigen Herausforderung, da der Spieler das Timing
 * abpassen muss, um an ihm vorbeizukommen.
 * 
 * Bewegungsverhalten:
 * - Bleibt an seiner Position (keine Bewegung)
 * - Wächst kontinuierlich bis zur maximalen Größe
 * - Schrumpft kontinuierlich bis zur minimalen Größe
 * - Wiederholt diesen Zyklus endlos
 * 
 * Strategische Bedeutung:
 * - Spieler muss Timing beachten (vorbeilaufen, wenn Pulsar klein ist)
 * - Kann Durchgänge temporär blockieren
 * - Kombiniert mit anderen Gegnern für erhöhte Schwierigkeit
 * 
 * Technische Details:
 * - Verwendet eine Basis-Bildkopie, um Qualitätsverlust zu vermeiden
 * - Skaliert das Bild in jedem Takt neu
 * - Delta-Wert bestimmt die Pulsationsgeschwindigkeit
 * 
 * Verwendung in Leveln:
 * - Level 3: Zwei Pulsare mit unterschiedlichen Geschwindigkeiten
 * - Level 5: Ein Pulsar im letzten Gang als finale Herausforderung
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Pulsar extends Enemy {
    
    // ==================== ATTRIBUTE ====================
    
    /** Minimale Größe des Pulsars in Pixeln */
    private int minSize;
    
    /** Maximale Größe des Pulsars in Pixeln */
    private int maxSize;
    
    /** 
     * Änderungsrate der Größe pro Takt.
     * Positiver Wert = Wachsen
     * Negativer Wert = Schrumpfen
     */
    private int delta;
    
    /** Aktuelle Größe des Pulsars in Pixeln */
    private int current;
    
    /** 
     * Basis-Bild in Originalqualität.
     * Wichtig: Immer von dieser Kopie aus skalieren, um Qualitätsverlust zu vermeiden!
     */
    private GreenfootImage base;
    
    // ==================== KONSTRUKTOR ====================
    
    /**
     * Erstellt einen Pulsar mit spezifischen Größen-Parametern.
     * 
     * Empfohlene Werte:
     * - Langsamer Pulsar: minSize=20, maxSize=45, delta=1
     * - Mittlerer Pulsar: minSize=20, maxSize=45, delta=2
     * - Schneller Pulsar: minSize=25, maxSize=55, delta=3
     * 
     * @param minSize Minimale Größe in Pixeln (empfohlen: 15-30)
     * @param maxSize Maximale Größe in Pixeln (empfohlen: 40-60)
     * @param delta   Änderungsrate pro Takt (empfohlen: 1-3)
     *                Höhere Werte = schnellere Pulsation
     */
    public Pulsar(int minSize, int maxSize, int delta) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.delta = delta;
        // Pulsar startet mit minimaler Größe
        this.current = minSize;
    }
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird einmalig aufgerufen, wenn der Pulsar zur Welt hinzugefügt wird.
     * 
     * Wichtig: Hier wird eine Kopie des Original-Bildes gespeichert.
     * Diese Kopie wird als Basis für alle Skalierungen verwendet, um
     * Qualitätsverlust durch wiederholtes Skalieren zu vermeiden.
     * 
     * Ohne diese Basis-Kopie würde das Bild bei jedem Takt vom bereits
     * skalierten Bild aus neu skaliert werden, was zu zunehmender Unschärfe führt.
     * 
     * @param w Die Welt, zu der der Pulsar hinzugefügt wurde
     */
    @Override
    protected void addedToWorld(World w) {
        // Original-Bild als Basis speichern (wichtig für Bildqualität!)
        base = new GreenfootImage(getImage());
        // Initiale Größe setzen
        updateSize();
    }
    
    // ==================== PULSATIONS-LOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die Pulsation.
     * 
     * Ablauf:
     * 1. Aktuelle Größe um delta ändern (wachsen oder schrumpfen)
     * 2. Prüfen, ob maximale Größe erreicht wurde
     *    → Wenn ja: Schrumpfen beginnen (delta negativ machen)
     * 3. Prüfen, ob minimale Größe erreicht wurde
     *    → Wenn ja: Wachsen beginnen (delta positiv machen)
     * 4. Bild auf neue Größe skalieren
     */
    @Override
    public void act() {
        // === SCHRITT 1: Größe ändern ===
        current += delta;
        
        // === SCHRITT 2: Maximale Größe erreicht ===
        if (current >= maxSize) {
            // Größe auf exaktes Maximum setzen
            current = maxSize;
            // Richtung umkehren: Jetzt schrumpfen (negativer delta)
            delta = -Math.abs(delta);
        }
        
        // === SCHRITT 3: Minimale Größe erreicht ===
        if (current <= minSize) {
            // Größe auf exaktes Minimum setzen
            current = minSize;
            // Richtung umkehren: Jetzt wachsen (positiver delta)
            delta = Math.abs(delta);
        }
        
        // === SCHRITT 4: Bild aktualisieren ===
        updateSize();
    }
    
    /**
     * Aktualisiert das Bild des Pulsars basierend auf der aktuellen Größe.
     * 
     * Diese Methode erstellt eine neue Kopie vom Basis-Bild und skaliert sie
     * auf die aktuelle Größe. Dies ist entscheidend für die Bildqualität:
     * 
     * RICHTIG (wie hier):
     *   Basis-Bild → Kopie erstellen → Skalieren → Setzen
     *   (Jede Skalierung erfolgt vom Original aus)
     * 
     * FALSCH:
     *   Aktuelles Bild → Skalieren → Setzen
     *   (Wiederholtes Skalieren führt zu Qualitätsverlust)
     */
    private void updateSize() {
        if (base != null) {
            // Neue Kopie vom Basis-Bild erstellen
            GreenfootImage scaled = new GreenfootImage(base);
            // Auf aktuelle Größe skalieren
            scaled.scale(current, current);
            // Skaliertes Bild setzen
            setImage(scaled);
        }
    }
}
