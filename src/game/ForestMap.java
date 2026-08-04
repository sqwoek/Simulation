package game;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.Grass;

import java.util.*;

public class ForestMap {
    private final Random random = new Random();
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 10;
    private final Map<Coordinates, Entity> cells = new HashMap<>();

    public ForestMap() {

    }

    public void placeEntity(Coordinates coordinates, Entity entity) {
        cells.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        return cells.get(coordinates);
    }

    public void moveEntityTo(Entity entity, Coordinates coords) {
        Coordinates currentCoords = getCurrentCoords(entity);
        cells.remove(currentCoords);
        cells.put(coords, entity);
    }

    public void removeEntity(Coordinates currentCoords) {
        cells.remove(currentCoords);
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return !cells.containsKey(coordinates);
    }

    public Map<Coordinates, Entity> getMap() {
        return new HashMap<>(cells);
    }

    public Coordinates getCurrentCoords(Entity target) {
        for (Map.Entry<Coordinates, Entity> entry : cells.entrySet()) {
            if (entry.getValue() == target) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Coordinates getNearestTargetCords(Creature creature, Class<? extends Entity> target) {
        Coordinates currentCoords = getCurrentCoords(creature);
        if (currentCoords == null) {
            return null;
        }
        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Coordinates, Entity> entry : cells.entrySet()) {
            Entity entity = entry.getValue();
            if (target.isInstance(entity)) {
                Coordinates cord = entry.getKey();
                int distance = Math.abs(cord.getX() - currentCoords.getX()) +
                        Math.abs(cord.getY() - currentCoords.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = cord;
                }
            }
        }
        return nearest;
    }

    public boolean isSquareGoodForMove(Creature creature, Coordinates target) {
        if (target.getX() <= 0 || target.getX() > MAP_WIDTH ||
                target.getY() <= 0 || target.getY() > MAP_HEIGHT) {
            return false;
        }
        Entity entity = cells.get(target);
        if (entity == null) {
            return true;
        }
        if (creature instanceof Herbivore && entity instanceof Grass) {
            return true;
        }
        if (creature instanceof Predator && entity instanceof Herbivore) {
            return true;
        }
        return false;
    }

    public boolean isTargetClose(Creature creature, Coordinates targetCoords) {
        Coordinates currentCoords = getCurrentCoords(creature);
        if (currentCoords == null) {
            throw new RuntimeException();
        }
        int dx = Math.abs(currentCoords.getX() - targetCoords.getX());
        int dy = Math.abs(currentCoords.getY() - targetCoords.getY());
        return (dx + dy) == 1;
    }

    public void addGrass() {
        while (true) {
            int x = random.nextInt(MAP_WIDTH);
            int y = random.nextInt(MAP_HEIGHT);

            Coordinates coordinates = new Coordinates(x, y);
            if (cells.get(coordinates) == null) {
                cells.put(coordinates, new Grass());
                return;
            }
        }
    }

    public void addHerbivore() {
        while (true) {
            int x = random.nextInt(MAP_WIDTH);
            int y = random.nextInt(MAP_HEIGHT);

            Coordinates coordinates = new Coordinates(x, y);
            if (cells.get(coordinates) == null) {
                cells.put(coordinates, new Herbivore(1, 100));
                return;
            }
        }
    }

    public int getWidth() {
        return MAP_WIDTH;
    }

    public int getHeight() {
        return MAP_HEIGHT;
    }
}