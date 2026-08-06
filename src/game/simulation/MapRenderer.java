package game.simulation;

import game.simulation.entities.Entity;

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
                if (entityOpt.isPresent()) {
                    Entity entity = entityOpt.get();
                    String image = getImage(entity);
                    System.out.print(image);
                } else {
                    System.out.print(GROUND_IMAGE);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String getImage(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Grass" -> GRASS_IMAGE;
            case "Tree" -> TREE_IMAGE;
            case "Rock" -> ROCK_IMAGE;
            case "Herbivore" -> HERBIVORE_IMAGE;
            case "Predator" -> PREDATOR_IMAGE;
            default -> throw new IllegalStateException("Unexpected value: " + entity.getClass().getSimpleName());
        };
    }
}
