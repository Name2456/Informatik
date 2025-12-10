import greenfoot.*;

/**
 * Level2 – Zweites Level
 * Horizontales Layout mit vertikalen GreenDot-Gegnern.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Level2 extends World implements LabeledWorld {

    public Level2() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // Obere Wand (rechts Lücke)
        Wall top = new Wall();
        addObject(top, 0, 0);
        top.placeWall(0, 100, 540, 60);

        // Untere Wand (links Lücke)
        Wall bottom = new Wall();
        addObject(bottom, 0, 0);
        bottom.placeWall(60, 260, 540, 60);

        // Zielbereich unten rechts
        addObject(new TargetArea(), 560, 360);

        // GreenDots (vertikal)
        int minY = 145;
        int maxY = 255;
        addObject(new GreenDot(minY, maxY, 1, 30), 200, 200);
        addObject(new GreenDot(minY, maxY, 2, 30), 300, 200);
        addObject(new GreenDot(minY, maxY, 1, 30), 400, 200);
        addObject(new GreenDot(minY, maxY, 2, 30), 500, 200);
    }

    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) p.resetLives();
        showHUD(5);
    }

    @Override
    public void showHUD(int lives) {
        showText("Level: 2", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }

    @Override
    public String levelName() {
        return "Level 2";
    }
}