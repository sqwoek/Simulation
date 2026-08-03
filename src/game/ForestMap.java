package game;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.FieldObject;
import game.simulation.fieldObjects.Grass;
import game.simulation.fieldObjects.Rock;
import game.simulation.fieldObjects.Tree;

import java.util.*;

public class ForestMap {
    private final Map<Coordinates, Entity> map = new HashMap<>();
    private final Random random = new Random();
    private static final int MAP_SIZE = 10;

    public ForestMap() {

    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        map.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        return map.get(coordinates);
    }

    public Map<Coordinates, Entity> getMap() {
        return map;
    }

    public boolean isEmpty(Coordinates coordinates) {
        if (map.containsKey(coordinates)) {
            return false;
        }
        return true;
    }

    public Coordinates getCurrentCoords(Entity target) {
        for (Map.Entry<Coordinates, Entity> entry : map.entrySet()) {
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
        for (Map.Entry<Coordinates, Entity> entry : map.entrySet()) {
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
        if (target.getX() <= 0 || target.getX() > MAP_SIZE ||
                target.getY() <= 0 || target.getY() > MAP_SIZE) {
            return false;
        }
        Entity entity = map.get(target);
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

    public void moveCreatureTo(Creature creature, Coordinates coords) {
        Coordinates currentCoords = getCurrentCoords(creature);
        map.remove(currentCoords);
        map.put(coords, creature);
    }

    public void removeEntity(Coordinates currentCoords) {
        map.remove(currentCoords);
    }

    public void addGrass() {
        while (true) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);

            Coordinates coordinates = new Coordinates(x, y);
            if (map.get(coordinates) == null) {
                map.put(coordinates, new Grass());
                return;
            }
        }
    }

    public void addHerbivore() {
        while (true) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);

            Coordinates coordinates = new Coordinates(x, y);
            if (map.get(coordinates) == null) {
                map.put(coordinates, new Herbivore(1, 100));
                return;
            }
        }
    }
}