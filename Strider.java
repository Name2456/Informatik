import greenfoot.*;

/**
 * Strider – Geradeaus laufender Gegner mit Teleport
 * Wählt zu Beginn eine Richtung und teleportiert am Rand.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Strider extends Enemy {
    private int dx;  // Bewegung X
    private int dy;  // Bewegung Y
    private int speed = 3;

    /**
     * Wird aufgerufen, wenn der Strider zur Welt hinzugefügt wird.
     * Wählt eine zufällige Richtung.
     */
    @Override
    protected void addedToWorld(World w) {
        // Zufällige Richtung (diagonal möglich)
        dx = Greenfoot.getRandomNumber(3) - 1;  // -1, 0, oder 1
        dy = Greenfoot.getRandomNumber(3) - 1;
        
        // Mindestens eine Richtung muss aktiv sein
        if (dx == 0 && dy == 0) {
            dx = 1;
        }
    }

    /**
     * Bewegungslogik: Geradeaus + Teleport am Rand.
     */
    @Override
    public void act() {
        setLocation(getX() + dx * speed, getY() + dy * speed);

        // Am Rand: Teleport auf gegenüberliegende Seite
        World w = getWorld();
        if (getX() <= 0) setLocation(w.getWidth() - 1, getY());
        if (getX() >= w.getWidth()) setLocation(1, getY());
        if (getY() <= 0) setLocation(getX(), w.getHeight() - 1);
        if (getY() >= w.getHeight()) setLocation(getX(), 1);
    }
}