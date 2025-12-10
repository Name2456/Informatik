import greenfoot.*;

/**
 * Wall – Hindernisse im Spiel
 * Kann beliebig positioniert und skaliert werden.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Wall extends Actor {

    /**
     * Wände tun von alleine nichts.
     */
    @Override
    public void act() {
        // Statisches Hindernis
    }

    /**
     * Setzt die Größe des Wall-Bilds.
     * 
     * @param width  Breite in Pixeln
     * @param height Höhe in Pixeln
     */
    public void setSize(int width, int height) {
        GreenfootImage wallImg = getImage();
        wallImg.scale(width, height);
    }

    /**
     * Setzt die Position der Wand (obere linke Ecke).
     * 
     * @param x X-Koordinate der oberen linken Ecke
     * @param y Y-Koordinate der oberen linken Ecke
     */
    public void setPos(int x, int y) {
        GreenfootImage wallImg = getImage();
        setLocation(x + wallImg.getWidth() / 2, y + wallImg.getHeight() / 2);
    }

    /**
     * Platziert diese Wand mit Position und Größe.
     * x, y beziehen sich auf die obere linke Ecke!
     * 
     * @param x      X-Koordinate der oberen linken Ecke
     * @param y      Y-Koordinate der oberen linken Ecke
     * @param width  Breite der Wand
     * @param height Höhe der Wand
     */
    public void placeWall(int x, int y, int width, int height) {
        setSize(width, height);
        setPos(x, y);
    }
}