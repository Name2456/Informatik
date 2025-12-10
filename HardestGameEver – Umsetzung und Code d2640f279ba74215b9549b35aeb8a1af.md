# HardestGameEver – Umsetzung und Code

## HardestGameEver – Vollständiger Code

Alle Klassen vollständig implementiert mit **allen Features** aus der Aufgabe:

- ✅ Vollständige Steuerung (4 Richtungen)
- ✅ Mehrere BlueDot-Gegner mit zufälliger Geschwindigkeit
- ✅ Sounds (Wand, Gegner, Level-Up)
- ✅ GreenDot (vertikal)
- ✅ Pulsar (pulsierend)
- ✅ Level 1, 2, 3, 4, 5
- ✅ WindZone (Seitenwind)
- ✅ InvertZone (verkehrte Tasten)
- ✅ RandomWalker, Strider, Shooter, Follower
- ✅ HUD (Level + Leben)
- ✅ Leben-System (5 Leben, bei 0 zurück zu Level 1)
- ✅ Cheats (Zifferntasten 1-5)
- ✅ Gratulationstext am Ende

---

### 🔴 Benötigte Grafiken

Lege folgende Bilder im `images/`-Ordner ab (quadratisch, z.B. 40×40 px):

- `player.png` – Roter Block (Spieler)
- `bluedot.png` – Blauer Kreis
- `greendot.png` – Grüner Kreis
- `pulsar.png` – Gelber/Oranger Kreis
- `wall.png` – Graues/Schwarzes Rechteck
- `target.png` – Oranges/Gelbes Zielfeld
- `windzone.png` – Halbtransparentes blaues Rechteck
- `invertzone.png` – Halbtransparentes lila Rechteck
- `randomwalker.png` – Weißer Kreis
- `strider.png` – Cyan Kreis
- `shooter.png` – Roter Kreis mit Punkt
- `bullet.png` – Kleiner roter Punkt (10×10 px)
- `follower.png` – Magenta Kreis

### 🔊 Benötigte Sounds

Lege folgende Sounds im `sounds/`-Ordner ab:

- `hit_[wall.mp](http://wall.mp)3` – Kurzer Aufprall-Sound
- `hit_[enemy.mp](http://enemy.mp)3` – Kurzer Schaden-Sound
- `level_[up.mp](http://up.mp)3` – Erfolgs-Sound
- [`victory.mp](http://victory.mp)3` – Gewonnen-Sound

---

## [Player.java](http://Player.java)

```java
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
            setLocation(xOld, yOld); Greenfoot.playSound("hit_[wall.mp](http://wall.mp)3");
        }
        if (!getIntersectingObjects(Enemy.class).isEmpty()) loseLife();
        if (!getIntersectingObjects(Bullet.class).isEmpty()) loseLife();
        if (!getIntersectingObjects(TargetArea.class).isEmpty()) {
            Greenfoot.playSound("level_[up.mp](http://up.mp)3"); nextLevel();
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
        Greenfoot.playSound("hit_[enemy.mp](http://enemy.mp)3");
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
            Greenfoot.playSound("[victory.mp](http://victory.mp)3");
            getWorld().showText("YEAH, YOU WON!", 300, 180);
            getWorld().showText("Congratulations!", 300, 220);
            Greenfoot.stop();
        }
    }
}
```

---

## [Enemy.java](http://Enemy.java)

```java
import greenfoot.*;

/**
 * Enemy – Basisklasse für alle Gegner
 * Enthält gemeinsame Hilfsmethoden.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Enemy extends Actor {
    
    /**
     * Korrekturschritt rückwärts (z.B. bei Wandkollision).
     * 
     * @param dx Verschiebung in X-Richtung rückgängig machen
     * @param dy Verschiebung in Y-Richtung rückgängig machen
     */
    protected void bounceBack(int dx, int dy) {
        setLocation(getX() - dx, getY() - dy);
    }
}
```

---

## [BlueDot.java](http://BlueDot.java)

```java
import greenfoot.*;

/**
 * BlueDot – Horizontal pendelnder Gegner
 * Bewegt sich zwischen minX und maxX hin und her.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class BlueDot extends Enemy {
    private int minX;   // Linke Grenze
    private int maxX;   // Rechte Grenze
    private int speed;  // Geschwindigkeit (positiv = rechts, negativ = links)

    /**
     * Konstruktor mit allen Parametern.
     * 
     * @param minX  Linke Grenze des Bewegungsbereichs
     * @param maxX  Rechte Grenze des Bewegungsbereichs
     * @param speed Geschwindigkeit (0 = zufällig 1-5)
     */
    public BlueDot(int minX, int maxX, int speed) {
        this.minX = minX;
        this.maxX = maxX;
        // Bei speed <= 0: Zufällige Geschwindigkeit zwischen 1 und 5
        this.speed = (speed <= 0) ? Greenfoot.getRandomNumber(5) + 1 : speed;
    }

    /**
     * Konstruktor mit Standardgrenzen (120-480).
     * 
     * @param speed Geschwindigkeit (0 = zufällig)
     */
    public BlueDot(int speed) {
        this(120, 480, speed);
    }

    /**
     * Bewegungslogik: Horizontal pendeln.
     */
    @Override
    public void act() {
        int dx = speed;
        setLocation(getX() + dx, getY());

        // An Grenzen umkehren
        if (getX() <= minX) {
            setLocation(minX, getY());
            speed = Math.abs(speed);  // Nach rechts
        }
        if (getX() >= maxX) {
            setLocation(maxX, getY());
            speed = -Math.abs(speed);  // Nach links
        }

        // Bei Wandkollision: Korrektur + Richtungswechsel
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            bounceBack(dx, 0);
            speed = -speed;
        }
    }
}
```

---

## [GreenDot.java](http://GreenDot.java)

```java
import greenfoot.*;

/**
 * GreenDot – Vertikal pendelnder Gegner
 * Bewegt sich zwischen minY und maxY hin und her.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class GreenDot extends Enemy {
    private int minY;   // Obere Grenze
    private int maxY;   // Untere Grenze
    private int speed;  // Geschwindigkeit
    private int size;   // Größe in Pixeln

    /**
     * Konstruktor mit Standardgröße (36 px).
     */
    public GreenDot(int minY, int maxY, int speed) {
        this(minY, maxY, speed, 36);
    }

    /**
     * Konstruktor mit allen Parametern.
     * 
     * @param minY  Obere Grenze
     * @param maxY  Untere Grenze
     * @param speed Geschwindigkeit
     * @param size  Größe in Pixeln (mind. 12)
     */
    public GreenDot(int minY, int maxY, int speed, int size) {
        this.minY = minY;
        this.maxY = maxY;
        this.speed = speed;
        this.size = Math.max(12, size);
    }

    /**
     * Wird aufgerufen, wenn der GreenDot zur Welt hinzugefügt wird.
     * Skaliert das Bild auf die gewünschte Größe.
     */
    @Override
    protected void addedToWorld(World w) {
        GreenfootImage img = getImage();
        if (img != null) {
            GreenfootImage scaled = new GreenfootImage(img);
            scaled.scale(size, size);
            setImage(scaled);
        }
    }

    /**
     * Bewegungslogik: Vertikal pendeln.
     */
    @Override
    public void act() {
        int dy = speed;
        setLocation(getX(), getY() + dy);

        // An Grenzen umkehren
        if (getY() <= minY) {
            setLocation(getX(), minY);
            speed = Math.abs(speed);  // Nach unten
        }
        if (getY() >= maxY) {
            setLocation(getX(), maxY);
            speed = -Math.abs(speed);  // Nach oben
        }

        // Bei Wandkollision: Korrektur + Richtungswechsel
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            bounceBack(0, dy);
            speed = -speed;
        }
    }
}
```

---

## [Pulsar.java](http://Pulsar.java)

```java
import greenfoot.*;

public class Pulsar extends Enemy {
    private int minSize;
    private int maxSize;
    private int delta;
    private int current;
    private GreenfootImage base;

    public Pulsar(int minSize, int maxSize, int delta) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        [this.delta](http://this.delta) = delta;
        this.current = minSize;
    }

    @Override
    protected void addedToWorld(World w) {
        base = new GreenfootImage(getImage());
        updateSize();
    }

    @Override
    public void act() {
        current += delta;
        if (current >= maxSize) { current = maxSize; delta = -Math.abs(delta); }
        if (current <= minSize) { current = minSize; delta = Math.abs(delta); }
        updateSize();
    }

    private void updateSize() {
        if (base != null) {
            GreenfootImage scaled = new GreenfootImage(base);
            scaled.scale(current, current);
            setImage(scaled);
        }
    }
}
```

---

## [RandomWalker.java](http://RandomWalker.java)

```java
import greenfoot.*;

/**
 * RandomWalker – Zufällig umherlaufender Gegner
 * Bewegt sich jeden Takt in eine zufällige Richtung.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class RandomWalker extends Enemy {
    private int step = 2;  // Schrittweite

    /**
     * Bewegungslogik: Zufällige Richtung wählen.
     */
    @Override
    public void act() {
        int xOld = getX();
        int yOld = getY();

        // Zufällige Richtung (0=hoch, 1=runter, 2=links, 3=rechts)
        int direction = Greenfoot.getRandomNumber(4);

        switch (direction) {
            case 0: setLocation(getX(), getY() - step); break;  // Hoch
            case 1: setLocation(getX(), getY() + step); break;  // Runter
            case 2: setLocation(getX() - step, getY()); break;  // Links
            case 3: setLocation(getX() + step, getY()); break;  // Rechts
        }

        // Bei Wandkollision: Zurück zur alten Position
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld);
        }

        // Am Weltrand: Zurück zur alten Position
        if (getX() <= 5 || getX() >= getWorld().getWidth() - 5 ||
            getY() <= 5 || getY() >= getWorld().getHeight() - 5) {
            setLocation(xOld, yOld);
        }
    }
}
```

---

## [Strider.java](http://Strider.java)

```java
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
```

---

## [Shooter.java](http://Shooter.java)

```java
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
```

---

## [Bullet.java](http://Bullet.java)

```java
import greenfoot.*;

/**
 * Bullet – Projektil vom Shooter
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class Bullet extends Actor {
    private int dx = 0;
    private int dy = 0;
    private int speed = 5;

    /**
     * Konstruktor.
     * 
     * @param direction Richtung (0=hoch, 1=runter, 2=links, 3=rechts)
     */
    public Bullet(int direction) {
        switch (direction) {
            case 0: dy = -speed; break;  // Hoch
            case 1: dy = speed; break;   // Runter
            case 2: dx = -speed; break;  // Links
            case 3: dx = speed; break;   // Rechts
        }
    }

    /**
     * Bewegungslogik: Geradeaus fliegen, bei Wand/Rand verschwinden.
     */
    @Override
    public void act() {
        setLocation(getX() + dx, getY() + dy);

        // Am Weltrand entfernen
        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }

        // Bei Wandkollision entfernen
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            getWorld().removeObject(this);
        }
    }
}
```

---

## [Follower.java](http://Follower.java)

```java
import greenfoot.*;
import java.util.List;

/**
 * Follower – Verfolgt den Spieler
 * Bewegt sich alle paar Takte einen Schritt auf den Player zu.
 * 
 * @author Felix Krusch
 * @version 1.1
 */
public class Follower extends Enemy {
    private int step = 2;       // Schrittweite
    private int delay = 8;      // Takte zwischen Bewegungen
    private int counter = 0;    // Zähler

    @Override
    public void act() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;

        counter++;
        if (counter < delay) return;  // Warte bis delay erreicht
        counter = 0;

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
```

---

## [Wall.java](http://Wall.java)

```java
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
```

---

## [TargetArea.java](http://TargetArea.java)

```java
import greenfoot.*;

/**
 * TargetArea – Zielbereich
 * Wenn der Spieler diesen erreicht, geht es ins nächste Level.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class TargetArea extends Actor {

    /**
     * Das Ziel tut von alleine nichts.
     * Die Kollisionsprüfung erfolgt im Player.
     */
    @Override
    public void act() {
        // Statisches Ziel
    }
}
```

---

## [WindZone.java](http://WindZone.java)

```java
import greenfoot.*;

/**
 * WindZone – Bereich mit Seitenwind
 * Verschiebt den Spieler in eine Richtung.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class WindZone extends Actor {
    public int windDx;  // Wind-Stärke X
    public int windDy;  // Wind-Stärke Y

    /**
     * Konstruktor.
     * 
     * @param windDx Verschiebung pro Takt in X-Richtung
     * @param windDy Verschiebung pro Takt in Y-Richtung
     * @param width  Breite der Zone
     * @param height Höhe der Zone
     */
    public WindZone(int windDx, int windDy, int width, int height) {
        this.windDx = windDx;
        this.windDy = windDy;

        // Halbtransparentes blaues Rechteck erstellen
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(new java.awt.Color(100, 150, 255, 80));
        img.fill();
        setImage(img);
    }

    @Override
    public void act() {
        // Die Zone selbst tut nichts – Player prüft Kollision
    }
}
```

---

## [InvertZone.java](http://InvertZone.java)

```java
import greenfoot.*;

/**
 * InvertZone – Bereich mit invertierten Tasten
 * Innerhalb dieser Zone sind links/rechts und oben/unten vertauscht.
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public class InvertZone extends Actor {

    /**
     * Konstruktor.
     * 
     * @param width  Breite der Zone
     * @param height Höhe der Zone
     */
    public InvertZone(int width, int height) {
        // Halbtransparentes lila Rechteck erstellen
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(new java.awt.Color(200, 100, 255, 80));
        img.fill();
        setImage(img);
    }

    @Override
    public void act() {
        // Die Zone selbst tut nichts – Player prüft Kollision
    }
}
```

---

## [LabeledWorld.java](http://LabeledWorld.java) (Interface)

```java
/**
 * LabeledWorld – Interface für Welten mit HUD-Anzeige
 * 
 * @author Felix Krusch
 * @version 1.0
 */
public interface LabeledWorld {
    
    /**
     * Zeichnet die HUD-Anzeige (Level, Leben).
     * 
     * @param lives Aktuelle Lebenanzahl
     */
    void showHUD(int lives);
    
    /**
     * Gibt den Level-Namen zurück.
     * 
     * @return Name des Levels
     */
    String levelName();
}
```

---

## [Level1.java](http://Level1.java)

```java
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
```

---

## [Level2.java](http://Level2.java)

```java
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

        // GreenDots (vertikal) - LANGSAMER gemacht!
        int minY = 165;
        int maxY = 255;
        addObject(new GreenDot(minY, maxY, 1, 30), 200, 200);
        addObject(new GreenDot(minY, maxY, 1, 30), 300, 200);
        addObject(new GreenDot(minY, maxY, 1, 30), 400, 200);
        addObject(new GreenDot(minY, maxY, 1, 30), 500, 200);
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
```

---

## [Level3.java](http://Level3.java)

```java
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
```

---

## [Level4.java](http://Level4.java)

```java
import greenfoot.*;

/**
 * Level4 – Viertes Level
 * Features: WindZone, Shooter, Follower
 * Einfacheres Layout mit klarem Pfad
 * 
 * @author Felix Krusch
 * @version 1.1
 */
public class Level4 extends World implements LabeledWorld {

    public Level4() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // === WÄNDE === (anpassen nach Bedarf!)
        // Horizontale Barriere oben
        Wall w1 = new Wall();
        addObject(w1, 0, 0);
        w1.placeWall(100, 80, 400, 40);  // Lücke links und rechts

        // Horizontale Barriere unten
        Wall w2 = new Wall();
        addObject(w2, 0, 0);
        w2.placeWall(100, 280, 400, 40); // Lücke links und rechts

        // === ZIEL === rechts unten
        addObject(new TargetArea(), 550, 350);

        // === WINDZONE === im mittleren Bereich (Wind nach links)
        addObject(new WindZone(-1, 0, 200, 160), 300, 200);

        // === SHOOTER === schießt nach unten
        addObject(new Shooter(1), 300, 60);

        // === FOLLOWER === im unteren Bereich
        addObject(new Follower(), 450, 350);

        // === BLUEDOTS ===
        addObject(new BlueDot(20, 90, 2), 50, 180);
        addObject(new BlueDot(510, 580, 2), 540, 180);
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
```

---

## [Level5.java](http://Level5.java)

```java
import greenfoot.*;

/**
 * Level5 - FINALE
 * Features: InvertZone, RandomWalker, Strider, Shooter
 * Herausforderung: Invertierte Steuerung im Hauptbereich
 * 
 * @author Felix Krusch
 * @version 1.1
 */
public class Level5 extends World implements LabeledWorld {

    public Level5() {
        super(600, 400, 1);
        setup();
        addObject(new Player(), 30, 30);
        label();
    }

    private void setup() {
        // === WÄNDE === (anpassen nach Bedarf!)
        // Vertikale Wand links
        Wall w1 = new Wall();
        addObject(w1, 0, 0);
        w1.placeWall(150, 100, 40, 300); // Lücke oben

        // Vertikale Wand rechts
        Wall w2 = new Wall();
        addObject(w2, 0, 0);
        w2.placeWall(450, 0, 40, 300);   // Lücke unten

        // === ZIEL === rechts unten
        addObject(new TargetArea(), 550, 350);

        // === INVERTZONE === großer Bereich in der Mitte
        addObject(new InvertZone(250, 300), 300, 200);

        // === RANDOMWALKER === im linken Bereich
        addObject(new RandomWalker(), 75, 250);

        // === STRIDER === im rechten Bereich
        addObject(new Strider(), 520, 150);

        // === SHOOTER === schießt nach links
        addObject(new Shooter(2), 550, 50);

        // === GREENDOT === im Invertbereich
        addObject(new GreenDot(120, 350, 2, 25), 300, 200);

        // === PULSAR ===
        addObject(new Pulsar(25, 50, 1), 75, 50);
    }

    private void label() {
        showText(levelName(), 70, 380);
        Player p = getObjects(Player.class).isEmpty() ? null : getObjects(Player.class).get(0);
        if (p != null) p.resetLives();
        showHUD(5);
    }

    @Override
    public void showHUD(int lives) {
        showText("Level: 5 (FINALE)", 90, 20);
        showText("Leben: " + lives, 200, 20);
    }

    @Override
    public String levelName() {
        return "Level 5 - FINALE";
    }
}
```

---

## 📋 Zusammenfassung der Erweiterungen

| Nr. | Erweiterung | Punkte | Status |
| --- | --- | --- | --- |
| 1 | Vollständige Steuerung (4 Richtungen) | 1 P | ✅ |
| 2 | Zwei weitere BlueDots in Level1 | 1 P | ✅ |
| 3 | Zufällige Geschwindigkeit (1-5) | 1 P | ✅ |
| 4 | Sounds (Wand, Gegner, Level-Up) | 2 P | ✅ |
| 5 | Level2 mit Wänden und TargetArea | 1 P | ✅ |
| 6 | GreenDot-Klasse (vertikal) | 3 P | ✅ |
| 7 | Drittes Level (Level3) | 2 P | ✅ |
| 8 | Pulsar-Gegner (pulsierend) | 2 P | ✅ |
| 9a | RandomWalker | 3 P | ✅ |
| 9b | Strider (Teleport) | 3 P | ✅ |
| 9c | Shooter + Bullet | 3 P | ✅ |
| 9d | Follower | 3 P | ✅ |
| 9e | WindZone (Seitenwind) | 3 P | ✅ |
| 9f | InvertZone (verkehrte Tasten) | 3 P | ✅ |
| 10 | Gratulationstext am Ende | 1 P | ✅ |
| 11 | Level-Anzeige (HUD) | 1 P | ✅ |
| 12 | Cheats (Tasten 1-5) | 2 P | ✅ |
| 13 | Leben-System (5 Leben) | 3 P | ✅ |

**Gesamt: 38+ Punkte** 🎉