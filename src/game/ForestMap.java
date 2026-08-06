package game;

import game.simulation.Coordinates;
import game.simulation.Entity;

import java.util.*;

public class ForestMap {
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 10;
    private final Map<Coordinates, Entity> entities  = new HashMap<>();

    public ForestMap() {

    }

    public void placeEntity(Coordinates coords, Entity entity) {
        if (isWithinBorders(coords)) {
            entities.put(coords, entity);
        } else {
            throw new RuntimeException("Invalid coordinate.");
        }
    }

    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public void moveEntityTo(Entity entity, Coordinates coords) {
        if (isWithinBorders(coords)) {
            Coordinates currentCoords = getCurrentCoords(entity);
            removeEntity(currentCoords);
            placeEntity(coords, entity);
//            entities.remove(currentCoords);
//            entities.put(coords, entity);
        } else {
            throw new RuntimeException("Invalid coordinate.");
        }
    }

    public void removeEntity(Coordinates coords) {
        if (isWithinBorders(coords)) {
            entities.remove(coords);
        } else {
            throw new RuntimeException("Invalid coordinate.");
        }
    }

    public boolean isEmpty(Coordinates coords) {
        return !entities.containsKey(coords);
    }

    public Map<Coordinates, Entity> getMap() {
        return new HashMap<>(entities);
    }

    public Coordinates getCurrentCoords(Entity target) {
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == target) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isWithinBorders(Coordinates coords) {
        return coords.x() > 0 && coords.x() <= MAP_WIDTH && coords.y() > 0 && coords.y() <= MAP_HEIGHT;
    }

    public int getWidth() {
        return MAP_WIDTH;
    }

    public int getHeight() {
        return MAP_HEIGHT;
    }
}