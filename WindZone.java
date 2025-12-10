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
    }

    @Override
    public void act() {
        // Die Zone selbst tut nichts – Player prüft Kollision
    }
}