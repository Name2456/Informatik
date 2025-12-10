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
        this.delta = delta;
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