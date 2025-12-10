import greenfoot.*;

/**
 * RandomWalker – Zufällig umherlaufender Gegner
 * Bewegt sich jeden Takt in eine zufällige Richtung.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class RandomWalker extends Enemy {
    private int step = 2;  // Schrittweite

    /**
     * Bewegungslogik: Zufällige Richtung wählen.
     */
    @Override
    public void act() {
        int xOld = getX();
        int yOld = getY();

        // Zufällige Richtung (0=hoch, 1=runter, 2=links, 3=rechts)
        int direction = Greenfoot.getRandomNumber(4);

        switch (direction) {
            case 0: setLocation(getX(), getY() - step); break;  // Hoch
            case 1: setLocation(getX(), getY() + step); break;  // Runter
            case 2: setLocation(getX() - step, getY()); break;  // Links
            case 3: setLocation(getX() + step, getY()); break;  // Rechts
        }

        // Bei Wandkollision: Zurück zur alten Position
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld);
        }

        // Am Weltrand: Zurück zur alten Position
        if (getX() <= 5 || getX() >= getWorld().getWidth() - 5 ||
            getY() <= 5 || getY() >= getWorld().getHeight() - 5) {
            setLocation(xOld, yOld);
        }
    }
}