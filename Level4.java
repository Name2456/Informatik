import greenfoot.*;

/**
 * Level4 – Viertes Level
 * Seitenwind (WindZone), Shooter und Follower.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Level4 extends World implements LabeledWorld {

    public Level4() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // Wände: Labyrinth-artig
        Wall w1 = new Wall();
        addObject(w1, 0, 0);
        w1.placeWall(150, 0, 50, 250);

        Wall w2 = new Wall();
        addObject(w2, 0, 0);
        w2.placeWall(150, 300, 50, 100);

        Wall w3 = new Wall();
        addObject(w3, 0, 0);
        w3.placeWall(300, 150, 50, 250);

        Wall w4 = new Wall();
        addObject(w4, 0, 0);
        w4.placeWall(450, 0, 50, 250);

        // Ziel unten rechts
        addObject(new TargetArea(), 560, 360);

        // WindZone mit Seitenwind nach links
        addObject(new WindZone(-1, 0, 140, 400), 225, 200);

        // Shooter (schießt nach rechts)
        addObject(new Shooter(3), 170, 320);

        // Follower
        addObject(new Follower(), 400, 100);
        addObject(new Follower(), 520, 200);

        // Ein paar BlueDots
        addObject(new BlueDot(360, 440, 3), 400, 300);
    }

    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) p.resetLives();
        showHUD(5);
    }

    @Override
    public void showHUD(int lives) {
        showText("Level: 4", 70, 20);
        showText("Leben: " + lives, 170, 20);
    }

    @Override
    public String levelName() {
        return "Level 4";
    }
}