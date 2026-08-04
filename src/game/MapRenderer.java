package game;

import game.simulation.Coordinates;
import game.simulation.Entity;
import game.simulation.creatures.Creature;
import game.simulation.fieldObjects.FieldObject;

public class MapRenderer {
    private static final String GRASS_IMAGE = "\uD83C\uDF31";
    private static final String ROCK_IMAGE = "\uD83E\uDEA8";
    private static final String TREE_IMAGE = "\uD83C\uDF33";
    private static final String HERBIVORE_IMAGE = "\uD83D\uDC07";
    private static final String PREDATOR_IMAGE = "\uD83D\uDD34";
    private static final String GROUND_IMAGE = "\u2B1B";

    public static void printMap(ForestMap forestMap) {


        System.out.println();
        System.out.println("====================");
        for (int y = 1; y <= forestMap.getHeight(); y++) {
            for (int x = 1; x <= forestMap.getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                Entity entity = forestMap.getEntity(coordinates);
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
                    } else {
                            System.out.print(GROUND_IMAGE);
                    }
                }
                if (x <= forestMap.getWidth() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
