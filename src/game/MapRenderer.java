package game;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.FieldObject;

import java.util.Map;

public class MapRenderer {
    public static void printMap(Map<Coordinates, Entity> map) {
        System.out.println("--------------------");
        for (int x = 1; x <= 5; x++) {
            System.out.print("| ");
            for (int y = 1; y <= 5; y++) {
                Coordinates coordinates = new Coordinates(x, y);
                Entity entity = map.get(coordinates);
                if (entity instanceof FieldObject fieldObject) {
                    System.out.print(fieldObject.getName());
                } else {
                    if (entity instanceof Creature) {
                        if (entity instanceof Herbivore) {
                            System.out.print("\uD83D\uDC07");
                        } else if (entity instanceof Predator) {
                            System.out.print("\uD83D\uDC3A");
                        }
                    } else {
                        System.out.print("⬛");
                    }
                }
                if (x <= 4) System.out.print(" ");
            }
            System.out.println(" |");
        }
        System.out.println("--------------------");
    }
}
