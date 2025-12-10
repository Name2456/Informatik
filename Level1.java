import greenfoot.*;

/**
 * Level1 – Erstes Level
 * Einfaches Layout mit horizontalen BlueDot-Gegnern.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Level1 extends World implements LabeledWorld {

    public Level1() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // Linke Wand (unten Lücke)
        Wall left = new Wall();
        addObject(left, 0, 0);
        left.placeWall(100, 0, 80, 340);

        // Rechte Wand (oben Lücke)
        Wall right = new Wall();
        addObject(right, 0, 0);
        right.placeWall(420, 60, 80, 340);

        // Zielbereich unten rechts
        addObject(new TargetArea(), 560, 360);

        // BlueDots mit zufälliger Geschwindigkeit (0 = zufällig 1-5)
        int minX = 190;
        int maxX = 410;
        addObject(new BlueDot(minX, maxX, 0), 250, 100);
        addObject(new BlueDot(minX, maxX, 0), 300, 160);
        addObject(new BlueDot(minX, maxX, 0), 350, 220);
        addObject(new BlueDot(minX, maxX, 0), 280, 280);
    }

    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) p.resetLives();
        showHUD(5);
    }

    @Override
    public void showHUD(int lives) {
        showText("Level: 1", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }

    @Override
    public String levelName() {
        return "Level 1";
    }
}