import greenfoot.*;

/**
 * BlueDot – Horizontal pendelnder Gegner
 * Bewegt sich zwischen minX und maxX hin und her.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class BlueDot extends Enemy {
    private int minX;   // Linke Grenze
    private int maxX;   // Rechte Grenze
    private int speed;  // Geschwindigkeit (positiv = rechts, negativ = links)

    /**
     * Konstruktor mit allen Parametern.
     * 
     * @param minX  Linke Grenze des Bewegungsbereichs
     * @param maxX  Rechte Grenze des Bewegungsbereichs
     * @param speed Geschwindigkeit (0 = zufällig 1-5)
     */
    public BlueDot(int minX, int maxX, int speed) {
        this.minX = minX;
        this.maxX = maxX;
        // Bei speed <= 0: Zufällige Geschwindigkeit zwischen 1 und 5
        this.speed = (speed <= 0) ? Greenfoot.getRandomNumber(5) + 1 : speed;
    }

    /**
     * Konstruktor mit Standardgrenzen (120-480).
     * 
     * @param speed Geschwindigkeit (0 = zufällig)
     */
    public BlueDot(int speed) {
        this(120, 480, speed);
    }

    /**
     * Bewegungslogik: Horizontal pendeln.
     */
    @Override
    public void act() {
        int dx = speed;
        setLocation(getX() + dx, getY());

        // An Grenzen umkehren
        if (getX() <= minX) {
            setLocation(minX, getY());
            speed = Math.abs(speed);  // Nach rechts
        }
        if (getX() >= maxX) {
            setLocation(maxX, getY());
            speed = -Math.abs(speed);  // Nach links
        }

        // Bei Wandkollision: Korrektur + Richtungswechsel
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            bounceBack(dx, 0);
            speed = -speed;
        }
    }
}