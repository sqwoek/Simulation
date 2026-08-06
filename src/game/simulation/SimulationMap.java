package game.simulation;

import game.simulation.entities.Entity;
import game.simulation.exceptions.InvalidCoordinateException;

import java.util.*;

public class SimulationMap {
    private final int width;
    private final int height;
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public SimulationMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void placeEntity(Coordinates coordinates, Entity entity) {
        validate(coordinates);
        entities.put(coordinates, entity);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entities.get(coordinates));
    }

    public void removeEntity(Coordinates coordinates) {
        validate(coordinates);
        entities.remove(coordinates);
    }

    public boolean isEmpty(Coordinates coordinates) {
        validate(coordinates);
        return !entities.containsKey(coordinates);
    }

    public Optional<Coordinates> getCoordinates(Entity target) {
        for (Map.Entry<Coordinates, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == target) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    public boolean isWithinBorders(Coordinates coordinates) {
        return coordinates.x() > 0 && coordinates.x() <= width &&
                coordinates.y() > 0 && coordinates.y() <= height;
    }

    public void validate(Coordinates coordinates) {
        if (!isWithinBorders(coordinates)) {
            throw new InvalidCoordinateException(coordinates);
        }
    }

    public Map<Coordinates, Entity> getMap() {
        return new HashMap<>(entities);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}