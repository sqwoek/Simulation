package game.simulation;

import game.simulation.entities.creatures.Creature;
import game.simulation.entities.Entity;
import game.simulation.entities.fieldObjects.FieldObject;

import java.util.Optional;

public class MapRenderer {
    private static final String GRASS_IMAGE = "\uD83C\uDF31";
    private static final String ROCK_IMAGE = "\uD83E\uDEA8";
    private static final String TREE_IMAGE = "\uD83C\uDF33";
    private static final String HERBIVORE_IMAGE = "\uD83D\uDC07";
    private static final String PREDATOR_IMAGE = "\uD83D\uDD34";
    private static final String GROUND_IMAGE = "\u2B1B";

    public static void printMap(SimulationMap simulationMap) {


        System.out.println();
        System.out.println("====================");
        for (int y = 1; y <= simulationMap.getHeight(); y++) {
            for (int x = 1; x <= simulationMap.getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                Optional<Entity> entityOpt = simulationMap.getEntity(coordinates);
                if (entityOpt.isEmpty()) {
                    System.out.print(GROUND_IMAGE);
                } else {
                    Entity entity = entityOpt.get();
                    if (entity instanceof FieldObject fieldObject) {
                        switch (fieldObject.getClass().getSimpleName()) {
                            case "Grass":
                                System.out.print(GRASS_IMAGE);
                                break;
                            case "Tree":
                                System.out.print(TREE_IMAGE);
                                break;
                            case "Rock":
                                System.out.print(ROCK_IMAGE);
                                break;
                        }
                    } else {
                        if (entity instanceof Creature creature) {
                            switch (creature.getClass().getSimpleName()) {
                                case "Herbivore":
                                    System.out.print(HERBIVORE_IMAGE);
                                    break;
                                case "Predator":
                                    System.out.print(PREDATOR_IMAGE);
                                    break;
                            }
                        }
                    }
                }
                if (x <= simulationMap.getWidth() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
