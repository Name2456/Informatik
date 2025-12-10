import greenfoot.*;

/**
 * Level5 – Fünftes und letztes Level
 * InvertZone (verkehrte Tasten), RandomWalker, Strider – kniffliges Finale!
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Level5 extends World implements LabeledWorld {

    public Level5() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // Wände: Engerer Parcours
        Wall w1 = new Wall();
        addObject(w1, 0, 0);
        w1.placeWall(120, 80, 40, 240);

        Wall w2 = new Wall();
        addObject(w2, 0, 0);
        w2.placeWall(250, 0, 40, 280);

        Wall w3 = new Wall();
        addObject(w3, 0, 0);
        w3.placeWall(380, 120, 40, 280);

        Wall w4 = new Wall();
        addObject(w4, 0, 0);
        w4.placeWall(510, 0, 40, 250);

        // Ziel ganz rechts unten
        addObject(new TargetArea(), 565, 360);

        // InvertZone im mittleren Bereich
        addObject(new InvertZone(120, 400), 315, 200);

        // RandomWalker im ersten Gang
        addObject(new RandomWalker(), 60, 200);

        // Strider im zweiten Gang
        addObject(new Strider(), 185, 150);
        addObject(new Strider(), 185, 350);

        // GreenDots im dritten Gang
        addObject(new GreenDot(130, 390, 3, 25), 315, 250);

        // Pulsar im letzten Gang
        addObject(new Pulsar(25, 55, 2), 445, 300);

        // Shooter am Ende
        addObject(new Shooter(2), 530, 50);
    }

    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) p.resetLives();
        showHUD(5);
    }

    @Override
    public void showHUD(int lives) {
        showText("Level: 5", 90, 20);
        showText("Leben: " + lives, 200, 20);
    }

    @Override
    public String levelName() {
        return "Level 5";
    }
}