import greenfoot.*;

/**
 * GreenDot - Vertikal pendelnder Gegner.
 * 
 * Der GreenDot ist das vertikale Gegenstück zum BlueDot. Er bewegt sich
 * kontinuierlich vertikal zwischen zwei definierten Y-Koordinaten (minY und maxY)
 * auf und ab. Im Gegensatz zum BlueDot kann der GreenDot in verschiedenen Größen
 * erstellt werden, was zusätzliche Variation ermöglicht.
 * 
 * Bewegungsverhalten:
 * - Bewegt sich mit konstanter Geschwindigkeit vertikal
 * - Kehrt Richtung um, wenn er minY oder maxY erreicht
 * - Kehrt Richtung um, wenn er gegen eine Wand läuft
 * - Bleibt auf seiner X-Koordinate (keine horizontale Bewegung)
 * 
 * Besonderheiten:
 * - Unterstützt verschiedene Größen (12-60 Pixel empfohlen)
 * - Kleinere GreenDots sind schwerer zu vermeiden
 * - Größere GreenDots blockieren mehr Raum
 * 
 * Verwendung in Leveln:
 * - Level 2: Mehrere GreenDots im horizontalen Korridor
 * - Level 3: GreenDots in Kombination mit BlueDots und Pulsaren
 * - Level 5: GreenDots im dritten Gang
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class GreenDot extends Enemy {
    
    // ==================== KONSTANTEN ====================
    
    /** Standard-Größe für GreenDots in Pixeln */
    private static final int DEFAULT_SIZE = 36;
    
    /** Minimale erlaubte Größe in Pixeln */
    private static final int MIN_SIZE = 12;
    
    // ==================== ATTRIBUTE ====================
    
    /** Obere Grenze des Bewegungsbereichs (Y-Koordinate) */
    private int minY;
    
    /** Untere Grenze des Bewegungsbereichs (Y-Koordinate) */
    private int maxY;
    
    /** 
     * Geschwindigkeit und Richtung der Bewegung.
     * Positiver Wert = Bewegung nach unten
     * Negativer Wert = Bewegung nach oben
     */
    private int speed;
    
    /** Größe des GreenDots in Pixeln (Breite und Höhe) */
    private int size;
    
    // ==================== KONSTRUKTOREN ====================
    
    /**
     * Erstellt einen GreenDot mit Standardgröße (36 Pixel).
     * 
     * Dieser Konstruktor ist praktisch, wenn die Standard-Größe ausreicht.
     * 
     * @param minY  Obere Grenze des Bewegungsbereichs (Y-Koordinate)
     * @param maxY  Untere Grenze des Bewegungsbereichs (Y-Koordinate)
     * @param speed Geschwindigkeit in Pixeln pro Takt.
     *              Empfohlene Werte: 1 (langsam), 2-3 (mittel), 4-5 (schnell)
     */
    public GreenDot(int minY, int maxY, int speed) {
        this(minY, maxY, speed, DEFAULT_SIZE);
    }
    
    /**
     * Erstellt einen GreenDot mit spezifischer Größe.
     * 
     * Die Größe beeinflusst die Schwierigkeit:
     * - Kleine GreenDots (12-25 px): Schwer zu sehen, aber einfacher zu umgehen
     * - Mittlere GreenDots (26-40 px): Ausgewogene Herausforderung
     * - Große GreenDots (41-60 px): Blockieren viel Raum, aber gut sichtbar
     * 
     * @param minY  Obere Grenze des Bewegungsbereichs (Y-Koordinate)
     * @param maxY  Untere Grenze des Bewegungsbereichs (Y-Koordinate)
     * @param speed Geschwindigkeit in Pixeln pro Takt
     * @param size  Größe in Pixeln (wird auf mindestens 12 px begrenzt)
     */
    public GreenDot(int minY, int maxY, int speed, int size) {
        this.minY = minY;
        this.maxY = maxY;
        this.speed = speed;
        // Sicherstellen, dass die Größe nicht zu klein wird
        this.size = Math.max(MIN_SIZE, size);
    }
    
    // ==================== LIFECYCLE-METHODEN ====================
    
    /**
     * Wird einmalig aufgerufen, wenn der GreenDot zur Welt hinzugefügt wird.
     * Skaliert das Bild auf die im Konstruktor angegebene Größe.
     * 
     * Wichtig: Das Bild wird von einer Kopie aus skaliert, um Qualitätsverlust
     * zu vermeiden. Dies ist besonders wichtig bei mehrfachem Skalieren.
     * 
     * @param w Die Welt, zu der der GreenDot hinzugefügt wurde
     */
    @Override
    protected void addedToWorld(World w) {
        GreenfootImage img = getImage();
        if (img != null) {
            // Neue Kopie des Bildes erstellen (wichtig für Qualität!)
            GreenfootImage scaled = new GreenfootImage(img);
            // Auf gewünschte Größe skalieren
            scaled.scale(size, size);
            // Skaliertes Bild setzen
            setImage(scaled);
        }
    }
    
    // ==================== BEWEGUNGSLOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die Bewegung des GreenDots.
     * 
     * Ablauf:
     * 1. Bewege den GreenDot um 'speed' Pixel nach oben oder unten
     * 2. Prüfe, ob die Bewegungsgrenzen (minY/maxY) erreicht wurden
     * 3. Wenn Grenze erreicht: Position korrigieren und Richtung umkehren
     * 4. Prüfe auf Wandkollision
     * 5. Wenn Wand berührt: Position zurücksetzen und Richtung umkehren
     */
    @Override
    public void act() {
        // === SCHRITT 1: Bewegung ausführen ===
        // dy speichert die aktuelle Bewegung für spätere Korrektur
        int dy = speed;
        setLocation(getX(), getY() + dy);
        
        // === SCHRITT 2: Grenzen prüfen ===
        
        // Obere Grenze erreicht
        if (getY() <= minY) {
            // Position auf exakte Grenze setzen (verhindert "Durchrutschen")
            setLocation(getX(), minY);
            // Richtung umkehren: Nach unten bewegen (positiver speed)
            speed = Math.abs(speed);
        }
        
        // Untere Grenze erreicht
        if (getY() >= maxY) {
            // Position auf exakte Grenze setzen
            setLocation(getX(), maxY);
            // Richtung umkehren: Nach oben bewegen (negativer speed)
            speed = -Math.abs(speed);
        }
        
        // === SCHRITT 3: Wandkollision prüfen ===
        // Wenn der GreenDot eine Wand berührt, muss er zurückprallen
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            // Bewegung rückgängig machen (zurück zur Position vor der Kollision)
            bounceBack(0, dy);
            // Richtung umkehren
            speed = -speed;
        }
    }
}
