package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.GameContext;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.FieldObject;
import game.simulation.fieldObjects.Grass;
import game.simulation.fieldObjects.Rock;
import game.simulation.fieldObjects.Tree;

import java.util.List;
import java.util.Random;

public class InitializeWorldAction implements WorldAction {
    private static final int MAP_SIZE = 10;
    private final Random random = new Random();
    private final List<Creature> creatures = List.of(
            new Herbivore(1, 50),
            new Herbivore(1, 50),
            new Herbivore(1, 50),
            new Predator(2, 100, 35),
            new Predator(2, 100, 35)
    );

    @Override
    public void execute(GameContext gameContext) {
        placeFieldObjects(gameContext);
        placeCreatures(gameContext);
    }

    private void placeCreatures(GameContext gameContext) {
        for (Creature creature : creatures) {
            Coordinates coordinates = gameContext.getEmptyCell();
            gameContext.addEntity(creature, coordinates);
        }
    }

    private void placeFieldObjects(GameContext gameContext) {
        for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
            Coordinates coordinates = gameContext.getEmptyCell();
            FieldObject object = getFieldObject();
            if (object != null) {
                gameContext.addEntity(object, coordinates);
            }
        }
    }

    private FieldObject getFieldObject() {
        int rndm = random.nextInt(MAP_SIZE);
        return switch (rndm) {
            case 0 -> new Rock();
            case 1 -> new Tree();
            case 2 -> new Grass();
            default -> null;
        };
    }
}
