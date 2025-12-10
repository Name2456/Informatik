import greenfoot.*;
import java.util.List;

/**
 * Follower - Gegner, der den Spieler aktiv verfolgt.
 * 
 * Der Follower ist ein intelligenter Gegner, der sich kontinuierlich auf den
 * Spieler zubewegt. Im Gegensatz zu anderen Gegnern mit festen Bewegungsmustern
 * reagiert der Follower dynamisch auf die Position des Spielers.
 * 
 * Bewegungsverhalten:
 * - Bewegt sich in jedem Takt einen Schritt auf den Spieler zu
 * - Berechnet Richtung zum Spieler (horizontal und vertikal)
 * - Kann diagonal laufen (wenn Spieler diagonal ist)
 * - Stoppt bei Wandkollision (kann nicht durch Wände)
 * - Langsame Geschwindigkeit (step=1) macht ihn vermeidbar
 * 
 * Strategische Bedeutung:
 * - Aktive Verfolgung: Spieler kann sich nicht verstecken
 * - Vorhersehbar: Bewegt sich immer auf Spieler zu
 * - Langsam: Spieler kann entkommen, wenn er schnell ist
 * - Gefährlich in Kombination mit anderen Gegnern
 * - Gut für offene Bereiche
 * 
 * Besonderheiten:
 * - Einfache KI: Bewegt sich direkt auf Spieler zu (kein Pathfinding)
 * - Kann in Sackgassen stecken bleiben (wenn Wand zwischen Follower und Spieler)
 * - Langsame Geschwindigkeit ist Balance-Entscheidung (sonst zu schwer)
 * 
 * Verwendung in Leveln:
 * - Level 4: Zwei Follower im mittleren und rechten Bereich
 * 
 * @author Felix Krusch
 * @version 2.0
 */
public class Follower extends Enemy {
    
    // ==================== KONSTANTEN ====================
    
    /** 
     * Schrittweite pro Takt in Pixeln.
     * Bewusst langsam (1 Pixel), damit der Spieler entkommen kann.
     * Höhere Werte würden den Follower zu schwer zu vermeiden machen.
     */
    private static final int DEFAULT_STEP = 1;
    
    // ==================== ATTRIBUTE ====================
    
    /** Aktuelle Schrittweite (kann angepasst werden) */
    private int step = DEFAULT_STEP;
    
    // ==================== VERFOLGUNGS-LOGIK ====================
    
    /**
     * Wird in jedem Spieltakt aufgerufen und steuert die Verfolgung des Spielers.
     * 
     * Ablauf:
     * 1. Spieler-Objekt in der Welt finden
     * 2. Wenn kein Spieler vorhanden: Nichts tun
     * 3. Alte Position speichern (für Kollisionskorrektur)
     * 4. Richtung zum Spieler berechnen (horizontal und vertikal)
     * 5. In Richtung Spieler bewegen
     * 6. Bei Wandkollision: Zurück zur alten Position
     * 
     * Richtungsberechnung:
     * - Wenn Spieler links ist: dx = -step (nach links)
     * - Wenn Spieler rechts ist: dx = +step (nach rechts)
     * - Wenn Spieler oben ist: dy = -step (nach oben)
     * - Wenn Spieler unten ist: dy = +step (nach unten)
     * - Beide Richtungen können gleichzeitig aktiv sein (diagonal)
     */
    @Override
    public void act() {
        // === SCHRITT 1: Spieler finden ===
        List<Player> players = getWorld().getObjects(Player.class);
        
        // === SCHRITT 2: Kein Spieler vorhanden? ===
        // Dies kann passieren, wenn der Spieler gerade entfernt wurde
        // oder das Level neu geladen wird
        if (players.isEmpty()) {
            return;
        }
        
        // === SCHRITT 3: Spieler-Objekt holen ===
        Player player = players.get(0);
        
        // === SCHRITT 4: Alte Position speichern ===
        int xOld = getX();
        int yOld = getY();
        
        // === SCHRITT 5: Richtung zum Spieler berechnen ===
        int dx = 0;  // Bewegung in X-Richtung
        int dy = 0;  // Bewegung in Y-Richtung
        
        // Horizontale Richtung
        if (player.getX() < getX()) {
            // Spieler ist links vom Follower
            dx = -step;
        }
        if (player.getX() > getX()) {
            // Spieler ist rechts vom Follower
            dx = step;
        }
        // Wenn player.getX() == getX(), bleibt dx = 0 (keine horizontale Bewegung)
        
        // Vertikale Richtung
        if (player.getY() < getY()) {
            // Spieler ist über dem Follower
            dy = -step;
        }
        if (player.getY() > getY()) {
            // Spieler ist unter dem Follower
            dy = step;
        }
        // Wenn player.getY() == getY(), bleibt dy = 0 (keine vertikale Bewegung)
        
        // === SCHRITT 6: Bewegung ausführen ===
        setLocation(getX() + dx, getY() + dy);
        
        // === SCHRITT 7: Wandkollision prüfen ===
        // Wenn der Follower eine Wand berührt, wird er zurückgesetzt
        // Dies verhindert, dass er durch Wände läuft
        if (!getIntersectingObjects(Wall.class).isEmpty()) {
            setLocation(xOld, yOld);
        }
    }
}
