import greenfoot.*;
import java.util.List;

/**
 * Follower – Verfolgt den Spieler
 * Bewegt sich jeden Takt einen Schritt auf den Player zu.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Follower extends Enemy {
    private int step = 1;  // Langsame Verfolgung

    /**
     * Bewegungslogik: Auf den Spieler zu bewegen.
     */
    @Override
    public void act() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;

        Player player = players.get(0);
        int xOld = getX();
        int yOld = getY();

        // Richtung zum Spieler berechnen
        int dx = 0;
        int dy = 0;

        if (player.getX() < getX()) dx = -step;
        if (player.getX() > getX()) dx = step;
        if (player.getY() < getY()) dy = -step;
        if (player.getY() > getY()) dy = step;

        setLocation(getX() + dx, getY() + dy);

        // Bei Wandkollision: Zurück
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld);
        }
    }
}