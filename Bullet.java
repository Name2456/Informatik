import greenfoot.*;

/**
 * Bullet – Projektil vom Shooter
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Bullet extends Actor {
    private int dx = 0;
    private int dy = 0;
    private int speed = 5;

    /**
     * Konstruktor.
     * 
     * @param direction Richtung (0=hoch, 1=runter, 2=links, 3=rechts)
     */
    public Bullet(int direction) {
        switch (direction) {
            case 0: dy = -speed; break;  // Hoch
            case 1: dy = speed; break;   // Runter
            case 2: dx = -speed; break;  // Links
            case 3: dx = speed; break;   // Rechts
        }
    }

    /**
     * Bewegungslogik: Geradeaus fliegen, bei Wand/Rand verschwinden.
     */
    @Override
    public void act() {
        setLocation(getX() + dx, getY() + dy);

        // Am Weltrand entfernen
        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }

        // Bei Wandkollision entfernen
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            getWorld().removeObject(this);
        }
    }
}