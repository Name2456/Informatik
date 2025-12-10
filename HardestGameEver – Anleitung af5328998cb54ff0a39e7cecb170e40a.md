# HardestGameEver – Anleitung

## HardestGameEver – Anleitung (Erklärung)

Diese Anleitung erklärt den Code so, dass du jede Zeile nachvollziehen kannst. Ziel: Jemand ohne Vorkenntnisse versteht, wie das Spiel funktioniert und wie man Level baut.

---

## 1) Was ist dieses Spiel?

HardestGameEver ist ein Geschicklichkeitsspiel. Du steuerst eine Figur (roter Block) mit den Pfeiltasten zum orangefarbenen Ziel. Wände blockieren, Gegner schaden.

---

## 2) Wie Greenfoot arbeitet (wichtige Grundideen)

- **Welt (World)**: Die "Bühne" mit Breite 600 und Höhe 400 Pixeln. Sie ruft fortlaufend für alle Objekte die Methode `act()` auf (Spiel-Takt).
- **Actor**: Baustein für Figuren. Jeder Actor kann `act()` überschreiben, um sich pro Takt zu bewegen oder zu reagieren.
- **Lifecycle**:
    1. Objekt wird in die Welt eingefügt (`addObject`).
    2. Greenfoot ruft einmal `addedToWorld(world)` auf (Start-Hook).
    3. In jedem Takt ruft Greenfoot `act()` auf (Spiel läuft).

---

## 3) Projektübersicht (Dateien)

| Klasse | Beschreibung |
| --- | --- |
| [**Player.java**](http://Player.java) | Deine Spielfigur. Steuert Bewegung, Kollisionen, Leben, Levelwechsel, Sounds, Cheats. |
| [**Enemy.java**](http://Enemy.java) | Gemeinsame Basis für Gegner. Enthält Hilfsmethode `bounceBack()`. |
| [**BlueDot.java**](http://BlueDot.java) | Horizontal pendelnder Gegner (zwischen minX und maxX). |
| [**GreenDot.java**](http://GreenDot.java) | Vertikal pendelnder Gegner (zwischen minY und maxY). |
| [**Pulsar.java**](http://Pulsar.java) | Gegner, der am Platz pulsiert (größer/kleiner). |
| [**RandomWalker.java**](http://RandomWalker.java) | Gegner, der sich zufällig in eine Richtung bewegt. |
| [**Strider.java**](http://Strider.java) | Gegner mit fester Richtung, teleportiert am Rand. |
| [**Shooter.java**](http://Shooter.java) | Stationärer Gegner, der Projektile abfeuert. |
| [**Bullet.java**](http://Bullet.java) | Projektil vom Shooter. |
| [**Follower.java**](http://Follower.java) | Verfolgt den Spieler langsam. |
| [**Wall.java**](http://Wall.java) | Wand mit Methode `placeWall(x, y, width, height)`. |
| [**TargetArea.java**](http://TargetArea.java) | Ziel-Feld. Berührt der Player es, geht es ins nächste Level. |
| [**WindZone.java**](http://WindZone.java) | Unsichtbare Zone, die den Spieler in eine Richtung schiebt. |
| [**InvertZone.java**](http://InvertZone.java) | Zone, in der die Steuerung invertiert ist. |
| [**LabeledWorld.java**](http://LabeledWorld.java) | Interface für Welten mit HUD-Anzeige. |
| [**Level1-5.java**](http://Level1-5.java) | Welten mit `setup()` für Layout, `label()` für HUD. |

---

## 4) Koordinaten und Maße

- **Ursprung (0,0)** ist oben links. X nach rechts, Y nach unten.
- **Empfehlung**: Wanddicke 40-80 px. Levelgröße 600×400 (Zelle 1×1).
- `Wall.placeWall(x, y, w, h)`: x,y sind linke obere Ecke der Wand. w,h sind Größe.

---

## 5) [Player.java](http://Player.java) – Erklärung

Der Player macht fünf Dinge: **bewegen**, **Kollisionen prüfen**, **Leben verwalten**, **Level schalten**, **Cheats**.

### Attribute

- `step = 3` → Schrittweite pro Tastendruck (Pixel)
- `startX, startY = 30` → Startposition für Reset
- `lives = 5` → Verbleibende Leben

### addedToWorld(World world)

Wird einmalig aufgerufen, wenn der Player in die Welt eingefügt wird:

- Setzt den Spieler auf die Startposition
- Aktualisiert das HUD (Level, Leben)

### act() – Die Hauptlogik

1. **Position merken**: `xOld, yOld` speichern für Kollisionskorrektur
2. **Zonen prüfen**: WindZone (Seitenwind) und InvertZone (verkehrte Tasten)
3. **Steuerung**: Pfeiltasten bewegen den Spieler (ggf. invertiert)
4. **Wind anwenden**: Falls in WindZone, wird der Spieler zusätzlich verschoben
5. **Wandkollision**: Falls in Wand → zurück zur alten Position + Sound
6. **Gegnerkollision**: Leben verlieren, zurück zum Start (oder Game Over)
7. **Ziel erreicht**: Sound + nächstes Level
8. **Cheats**: Tasten 1-5 wechseln direkt ins entsprechende Level

### Wichtige Methoden

- `loseLife()` → Leben -1, HUD aktualisieren, bei 0 Leben → Level 1
- `startPosition()` → Spieler auf Startkoordinaten setzen
- `resetLives()` → Leben auf 5 zurücksetzen (neues Level)
- `updateHUD()` → Ruft `showHUD()` der aktuellen Welt auf
- `nextLevel()` → Wechselt basierend auf aktueller Welt ins nächste Level
- `showVictory()` → Zeigt Gratulationstext und stoppt das Spiel

---

## 6) [Enemy.java](http://Enemy.java) – Warum eine Basisklasse?

Alle Gegner erben von `Enemy`. Vorteil: Gemeinsame Funktionalität muss nur einmal geschrieben werden.

### bounceBack(int dx, int dy)

Korrekturschritt rückwärts – wird aufgerufen, wenn ein Gegner gegen eine Wand läuft:

- Bewegt den Gegner um `dx` und `dy` zurück
- Wird von BlueDot, GreenDot etc. genutzt

---

## 7) [BlueDot.java](http://BlueDot.java) – Horizontal pendeln

### Konstruktor

- `minX, maxX` → Grenzen des Korridors
- `speed` → Geschwindigkeit (bei 0 wird zufällig 1-5 gewählt)

### act() – Bewegungslogik

1. Position um `speed` in X-Richtung ändern
2. **An Grenzen umkehren**: Bei `minX` → positiver Speed, bei `maxX` → negativer Speed
3. **Wandkollision**: `bounceBack()` aufrufen + Richtung umkehren

---

## 8) [GreenDot.java](http://GreenDot.java) – Vertikal pendeln

Gleiches Muster wie BlueDot, nur in **Y-Richtung** statt X-Richtung.

### Zusätzlich: Größenanpassung

- `size` Parameter erlaubt unterschiedliche Größen
- In `addedToWorld()` wird das Bild auf die gewünschte Größe skaliert

---

## 9) [Pulsar.java](http://Pulsar.java) – Größe pulsieren lassen

### Attribute

- `minSize, maxSize` → Größenbereich
- `delta` → Änderung pro Takt (positiv = wachsen, negativ = schrumpfen)
- `current` → Aktuelle Größe
- `base` → **Originalbild als Vorlage** (wichtig!)

### act() – Pulsationslogik

1. `current += delta` (größer oder kleiner werden)
2. Bei `maxSize` → `delta` negativ machen (schrumpfen)
3. Bei `minSize` → `delta` positiv machen (wachsen)
4. Bild von der **Originalvorlage** aus neu skalieren (sonst wird es unscharf!)

---

## 10) [RandomWalker.java](http://RandomWalker.java) – Zufällige Bewegung

### act() – Bewegungslogik

1. Zufällige Richtung wählen (0-3 für hoch/runter/links/rechts)
2. Position ändern
3. Bei Wandkollision oder Weltrand: Zurück zur alten Position

---

## 11) [Strider.java](http://Strider.java) – Geradeaus mit Teleport

### addedToWorld()

Wählt eine zufällige Richtung (`dx`, `dy` jeweils -1, 0 oder 1).

### act() – Bewegungslogik

1. Position um `dx * speed` und `dy * speed` ändern
2. **Am Weltrand**: Teleport auf die gegenüberliegende Seite

---

## 12) [Shooter.java](http://Shooter.java) – Projektile abfeuern

### Attribute

- `cooldown` → Aktuelle Wartezeit bis zum nächsten Schuss
- `fireRate = 60` → Takte zwischen Schüssen
- `direction` → Schussrichtung (0=hoch, 1=runter, 2=links, 3=rechts)

### act() – Schusslogik

1. Wenn `cooldown <= 0`: Neues `Bullet` erzeugen + `cooldown` zurücksetzen
2. Sonst: `cooldown--`

---

## 13) [Bullet.java](http://Bullet.java) – Das Projektil

### Konstruktor

Setzt `dx` und `dy` basierend auf der Richtung:

- 0 (hoch): dy = -speed
- 1 (runter): dy = +speed
- 2 (links): dx = -speed
- 3 (rechts): dx = +speed

### act()

1. Position ändern
2. Am Weltrand oder bei Wandkollision: `removeObject(this)` – Projektil verschwindet

---

## 14) [Follower.java](http://Follower.java) – Den Spieler verfolgen

### act() – Verfolgungslogik

1. Spieler-Position holen
2. Richtung berechnen (ist Spieler links/rechts/oben/unten?)
3. Einen Schritt in Richtung Spieler machen
4. Bei Wandkollision: Zurück zur alten Position

**Wichtig**: `step = 1` macht den Follower langsam – sonst wäre er zu schwer!

---

## 15) [Wall.java](http://Wall.java) – Wände platzieren

### placeWall(x, y, width, height)

- Setzt die Größe des Bildes auf `width × height`
- Setzt die Position so, dass `(x, y)` die **obere linke Ecke** ist
- Wichtig: Greenfoot platziert Actors normalerweise am Mittelpunkt, daher die Umrechnung!

---

## 16) [WindZone.java](http://WindZone.java) & [InvertZone.java](http://InvertZone.java) – Spezialzonen

### WindZone

- `windDx, windDy` → Verschiebung pro Takt
- Im Konstruktor wird ein halbtransparentes blaues Rechteck erstellt
- Der Player prüft in `act()`, ob er in einer WindZone ist

### InvertZone

- Im Konstruktor wird ein halbtransparentes lila Rechteck erstellt
- Der Player prüft, ob er drin ist → `invert = true` → Tasten vertauscht

---

## 17) LabeledWorld – Das Interface

```java
public interface LabeledWorld {
    void showHUD(int lives);
    String levelName();
}
```

**Warum ein Interface?**

- Der Player kann `showHUD()` aufrufen, ohne zu wissen, welches Level gerade läuft
- Jede Welt entscheidet selbst, wie der Text angezeigt wird

---

## 18) Level-Aufbau – Das Muster

Jedes Level folgt demselben Muster:

### Konstruktor

1. `super(600, 400, 1)` → Welt mit 600×400 Pixeln anlegen
2. `setup()` → Wände, Gegner, Ziel platzieren
3. `addObject(new Player(), 30, 30)` → Spieler spawnen
4. `label()` → HUD initialisieren

### setup()

Hier werden alle Objekte für das Level erstellt:

- `Wall` mit `placeWall(x, y, w, h)`
- Gegner an bestimmten Positionen
- `TargetArea` als Ziel

### label()

- Level-Name unten anzeigen
- Leben auf 5 zurücksetzen
- HUD zeichnen

---

## 19) Sounds richtig einbinden

Dateien im `sounds/`-Ordner ablegen:

- `hit_[wall.mp](http://wall.mp)3` → Wenn der Spieler gegen eine Wand läuft
- `hit_[enemy.mp](http://enemy.mp)3` → Wenn der Spieler einen Gegner berührt
- `level_[up.mp](http://up.mp)3` → Wenn das Ziel erreicht wird
- [`victory.mp](http://victory.mp)3` → Wenn das Spiel gewonnen wird

Aufruf im Code: `Greenfoot.playSound("[dateiname.mp](http://dateiname.mp)3")`

---

## 20) Häufige Fehler und Lösungen

| Problem | Lösung |
| --- | --- |
| Gegner "klebt" an Wand | Startkoordinate oder min/max-Grenzen anpassen |
| Player steckt fest | Wandkanten prüfen (Überlappungen vermeiden) |
| HUD fehlt | Implementiert die Welt `LabeledWorld`? Ruft Player `updateHUD()` auf? |
| Bild wird unscharf | Bei Pulsar: Immer von der Originalvorlage aus skalieren! |
| Sound spielt nicht | Dateiname und Ordner prüfen (sounds/) |

---

## 21) Implementierte Features (38+ Punkte)

| Nr. | Feature | Erklärung |
| --- | --- | --- |
| 1 | Steuerung (4 Richtungen) | Alle Pfeiltasten in `act()` implementiert |
| 2 | Mehrere BlueDots | In Level1 mit `addObject()` eingefügt |
| 3 | Zufällige Geschwindigkeit | `speed <= 0` → `Greenfoot.getRandomNumber(5) + 1` |
| 4 | Sounds | `Greenfoot.playSound()` bei Kollisionen |
| 5 | Level2 Layout | `setup()` mit Wänden und TargetArea |
| 6 | GreenDot | Vertikales Pendeln mit minY/maxY |
| 7 | Level3 | Neues Layout mit mehreren Gängen |
| 8 | Pulsar | Größe pulsiert zwischen minSize und maxSize |
| 9a | RandomWalker | Zufällige Richtung pro Takt |
| 9b | Strider | Feste Richtung + Teleport am Rand |
| 9c | Shooter + Bullet | Projektile abfeuern |
| 9d | Follower | Bewegt sich auf Spieler zu |
| 9e | WindZone | Seitenwind-Effekt |
| 9f | InvertZone | Tasten vertauscht |
| 10 | Gratulationstext | `showVictory()` nach Level 5 |
| 11 | HUD | `showText()` für Level und Leben |
| 12 | Cheats | Tasten 1-5 in `checkCheats()` |
| 13 | Leben-System | 5 Leben, bei 0 → Level 1 |

---

## 22) Grafiken erstellen

Lege folgende Bilder im `images/`-Ordner ab (jeweils 40×40 px, außer bullet):

| Dateiname | Beschreibung | Farbe |
| --- | --- | --- |
| `player.png` | Quadrat | Rot |
| `bluedot.png` | Kreis | Blau |
| `greendot.png` | Kreis | Grün |
| `pulsar.png` | Kreis | Orange/Gelb |
| `wall.png` | Rechteck | Grau/Schwarz |
| `target.png` | Quadrat | Orange |
| `randomwalker.png` | Kreis | Weiß |
| `strider.png` | Kreis | Cyan |
| `shooter.png` | Kreis mit Punkt | Dunkelrot |
| `bullet.png` | Kreis (10×10 px!) | Rot |
| `follower.png` | Kreis | Magenta |

**WindZone** und **InvertZone** generieren ihre Bilder automatisch im Code!

---

## 23) Tipps zum Experimentieren

- **Schwierigkeit anpassen**: Geschwindigkeit der Gegner ändern
- **Neues Level bauen**: Level-Klasse kopieren, `setup()` anpassen
- **Neuen Gegner**: Von `Enemy` erben, eigene `act()` schreiben
- **Mehr Zonen**: WindZone/InvertZone kopieren, andere Effekte einbauen