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
        List<Creature> creatures = new ArrayList<>();
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Herbivore(1, 100));
        creatures.add(new Predator(2, 100, 35));
        creatures.add(new Predator(2, 100, 35));
        initializeMap(creatures);
    }

    public void initializeMap(List<Creature> creatures) {
        putFieldObjects(map);
        for (int i = 0; i < creatures.size(); i++) {
            int x = random.nextInt(1, MAP_SIZE);
            int y = random.nextInt(1, MAP_SIZE);
            putCreatures(new Coordinates(x, y), creatures.get(i));
        }
    }

    public void putFieldObjects(Map<Coordinates, Entity> map) {
        for (int i = 1; i <= MAP_SIZE; i++) {
            for (int j = 1; j <= MAP_SIZE; j++) {
                Coordinates coordinates = new Coordinates(i, j);
                int rndm = random.nextInt(10);
                FieldObject fieldObject = switch (rndm) {
                    case 0 -> new Rock();
                    case 1 -> new Tree();
                    case 2 -> new Grass();
                    default -> null;
                };
                map.put(coordinates, fieldObject);
            }
        }
    }

    public void putCreatures(Coordinates coordinates, Creature creature) {
        map.put(coordinates, creature);
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

    public Entity getEntity(Coordinates coordinates) {
        return map.get(coordinates);
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
}