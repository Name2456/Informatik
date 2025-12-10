import greenfoot.*;
import java.util.List;

public class Player extends Actor {
    private int step = 3;
    private int startX = 30;
    private int startY = 30;
    private int lives = 5;

    public void addedToWorld(World world) { startPosition(); updateHUD(); }

    public void act() {
        int xOld = getX(), yOld = getY();
        int dxMod = 0, dyMod = 0;
        boolean invert = false;

        for (WindZone wz : getIntersectingObjects(WindZone.class)) {
            dxMod += wz.windDx; dyMod += wz.windDy;
        }
        if (!getIntersectingObjects(InvertZone.class).isEmpty()) invert = true;

        int sL = invert ? step : -step, sR = invert ? -step : step;
        int sU = invert ? step : -step, sD = invert ? -step : step;

        if (Greenfoot.isKeyDown("left")) setLocation(getX()+sL, getY());
        if (Greenfoot.isKeyDown("right")) setLocation(getX()+sR, getY());
        if (Greenfoot.isKeyDown("up")) setLocation(getX(), getY()+sU);
        if (Greenfoot.isKeyDown("down")) setLocation(getX(), getY()+sD);
        if (dxMod!=0 || dyMod!=0) setLocation(getX()+dxMod, getY()+dyMod);

        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld); Greenfoot.playSound("hit_wall.mp3");
        }
        if (!getIntersectingObjects(Enemy.class).isEmpty()) loseLife();
        if (!getIntersectingObjects(Bullet.class).isEmpty()) loseLife();
        if (!getIntersectingObjects(TargetArea.class).isEmpty()) {
            Greenfoot.playSound("level_up.mp3"); nextLevel();
        }
        checkCheats();
    }

    private void checkCheats() {
        if (Greenfoot.isKeyDown("1")) Greenfoot.setWorld(new Level1());
        if (Greenfoot.isKeyDown("2")) Greenfoot.setWorld(new Level2());
        if (Greenfoot.isKeyDown("3")) Greenfoot.setWorld(new Level3());
        if (Greenfoot.isKeyDown("4")) Greenfoot.setWorld(new Level4());
        if (Greenfoot.isKeyDown("5")) Greenfoot.setWorld(new Level5());
    }

    private void loseLife() {
        Greenfoot.playSound("hit_enemy.mp3");
        lives--;
        updateHUD();
        if (lives <= 0) Greenfoot.setWorld(new Level1());
        else startPosition();
    }

    public void startPosition() { setLocation(startX, startY); }
    public void resetLives() { lives = 5; updateHUD(); }
    public int getLives() { return lives; }
    public void updateHUD() {
        if (getWorld() instanceof LabeledWorld) ((LabeledWorld)getWorld()).showHUD(lives);
    }

    public void nextLevel() {
        World cw = getWorld();
        if (cw instanceof Level1) Greenfoot.setWorld(new Level2());
        else if (cw instanceof Level2) Greenfoot.setWorld(new Level3());
        else if (cw instanceof Level3) Greenfoot.setWorld(new Level4());
        else if (cw instanceof Level4) Greenfoot.setWorld(new Level5());
        else if (cw instanceof Level5) {
            Greenfoot.playSound("victory.mp3");
            getWorld().showText("YEAH, YOU WON!", 300, 180);
            getWorld().showText("Congratulations!", 300, 220);
            Greenfoot.stop();
        }
    }
}