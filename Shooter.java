import greenfoot.*;

/**
 * Shooter – Stationärer Gegner, der Projektile abfeuert
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Shooter extends Enemy {
    private int cooldown = 0;       // Aktuelle Wartezeit
    private int fireRate = 60;      // Takte zwischen Schüssen
    private int direction;          // Schussrichtung (0=hoch, 1=runter, 2=links, 3=rechts)

    /**
     * Konstruktor.
     * 
     * @param direction Schussrichtung (0=hoch, 1=runter, 2=links, 3=rechts)
     */
    public Shooter(int direction) {
        this.direction = direction;
    }

    /**
     * Schusslogik: Regelmäßig Projektile abfeuern.
     */
    @Override
    public void act() {
        if (cooldown <= 0) {
            fire();
            cooldown = fireRate + Greenfoot.getRandomNumber(30);  // Etwas Variation
        } else {
            cooldown--;
        }
    }

    /**
     * Feuert ein Projektil in die festgelegte Richtung ab.
     */
    private void fire() {
        Bullet bullet = new Bullet(direction);
        getWorld().addObject(bullet, getX(), getY());
    }
}