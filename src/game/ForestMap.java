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
    private final Map<Coordinates, Entity> entities  = new HashMap<>();

    public ForestMap() {

    }

    public void placeEntity(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        return entities.get(coordinates);
    }

    public void moveEntityTo(Entity entity, Coordinates coords) {
        Coordinates currentCoords = getCurrentCoords(entity);
        entities.remove(currentCoords);
        entities.put(coords, entity);
    }

    public void removeEntity(Coordinates currentCoords) {
        entities.remove(currentCoords);
    }

    public boolean isEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
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
        return coords.getX() > 0 && coords.getX() <= MAP_WIDTH && coords.getY() > 0 && coords.getY() <= MAP_HEIGHT;
    }

    public void addHerbivore() {
        while (true) {
            int x = random.nextInt(MAP_WIDTH);
            int y = random.nextInt(MAP_HEIGHT);

            Coordinates coordinates = new Coordinates(x, y);
            if (entities.get(coordinates) == null) {
                entities.put(coordinates, new Herbivore(1, 100));
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

    public Map<Coordinates, Entity> getAllEntities() {
        return Map.copyOf(entities);
    }
}