import greenfoot.*;

/**
 * GreenDot – Vertikal pendelnder Gegner
 * Bewegt sich zwischen minY und maxY hin und her.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class GreenDot extends Enemy {
    private int minY;   // Obere Grenze
    private int maxY;   // Untere Grenze
    private int speed;  // Geschwindigkeit
    private int size;   // Größe in Pixeln

    /**
     * Konstruktor mit Standardgröße (36 px).
     */
    public GreenDot(int minY, int maxY, int speed) {
        this(minY, maxY, speed, 36);
    }

    /**
     * Konstruktor mit allen Parametern.
     * 
     * @param minY  Obere Grenze
     * @param maxY  Untere Grenze
     * @param speed Geschwindigkeit
     * @param size  Größe in Pixeln (mind. 12)
     */
    public GreenDot(int minY, int maxY, int speed, int size) {
        this.minY = minY;
        this.maxY = maxY;
        this.speed = speed;
        this.size = Math.max(12, size);
    }

    /**
     * Wird aufgerufen, wenn der GreenDot zur Welt hinzugefügt wird.
     * Skaliert das Bild auf die gewünschte Größe.
     */
    @Override
    protected void addedToWorld(World w) {
        GreenfootImage img = getImage();
        if (img != null) {
            GreenfootImage scaled = new GreenfootImage(img);
            scaled.scale(size, size);
            setImage(scaled);
        }
    }

    /**
     * Bewegungslogik: Vertikal pendeln.
     */
    @Override
    public void act() {
        int dy = speed;
        setLocation(getX(), getY() + dy);

        // An Grenzen umkehren
        if (getY() <= minY) {
            setLocation(getX(), minY);
            speed = Math.abs(speed);  // Nach unten
        }
        if (getY() >= maxY) {
            setLocation(getX(), maxY);
            speed = -Math.abs(speed);  // Nach oben
        }

        // Bei Wandkollision: Korrektur + Richtungswechsel
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            bounceBack(0, dy);
            speed = -speed;
        }
    }
}