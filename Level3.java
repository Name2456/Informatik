import greenfoot.*;

/**
 * Level3 – Drittes Level
 * Labyrinth mit Pulsaren, BlueDots und GreenDots.
 * 
 * @author Felix Krusch
 * @version 1.2
 */
public class Level3 extends World implements LabeledWorld {

    public Level3() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // === WÄNDE ===
        // Wand 1: Horizontal oben (x=183, y=99)
        Wall w1 = new Wall();
        addObject(w1, 0, 0);
        w1.placeWall(183, 99, 300, 40);

        // Wand 2: Vertikal Mitte unten (x=293, y=290)
        Wall w2 = new Wall();
        addObject(w2, 0, 0);
        w2.placeWall(293, 290, 40, 110);

        // Wand 3: Vertikal rechts (x=495, y=148)
        Wall w3 = new Wall();
        addObject(w3, 0, 0);
        w3.placeWall(495, 148, 40, 252);

        // Wand 4: Vertikal oben Mitte (x=297, y=12)
        Wall w4 = new Wall();
        addObject(w4, 0, 0);
        w4.placeWall(297, 12, 40, 80);

        // === ZIEL === oben rechts (x=540, y=62)
        addObject(new TargetArea(), 540, 62);

        // === GEGNER ===
        // BlueDot 1: x1=9 bis x2=143, y=122
        addObject(new BlueDot(9, 143, 2), 80, 122);

        // BlueDot 2: x1=9 bis x2=143, y=222
        addObject(new BlueDot(9, 143, 3), 80, 222);

        // GreenDot 1: y1=182 bis y2=251, x=452
        addObject(new GreenDot(182, 251, 2, 28), 452, 220);

        // GreenDot 2: y1=182 bis y2=251, x=352
        addObject(new GreenDot(182, 251, 2, 28), 352, 220);

        // Pulsar 1: x=348, y=83
        addObject(new Pulsar(20, 45, 1), 348, 83);

        // Pulsar 2: x=457, y=83
        addObject(new Pulsar(20, 45, 2), 457, 83);
    }

    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) p.resetLives();
        showHUD(5);
    }

    @Override
    public void showHUD(int lives) {
        showText("Level: 3", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }

    @Override
    public String levelName() {
        return "Level 3";
    }
}