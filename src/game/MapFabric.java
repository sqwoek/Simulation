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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapFabric {
    private final Map<Coordinates, Entity> map = new HashMap<>();
    private final Random random = new Random();

    public MapFabric() {
        initializeMap(new Predator(2, 20), new Herbivore(1));
    }

    public void initializeMap(Creature predator, Creature herbivore) {
        putFieldObjects(map);
        putCreatures(predator, herbivore);
    }

    public void putFieldObjects(Map<Coordinates, Entity> map) {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
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

    public void putCreatures(Creature predator, Creature herbivore) {
        map.put(new Coordinates(1, 4), predator);
        map.put(new Coordinates(4, 2), herbivore);
    }

    public Map<Coordinates, Entity> getMap() {
        return map;
    }
}
