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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InitializeWorldAction implements WorldAction {
    private static final int MAP_SIZE = 10;
    private static final int HERBIVORE_COUNT = 3;
    private static final int PREDATOR_COUNT = 2;
    private final Random random = new Random();

    private final EntityFactory entityFactory = EntityFactory.getInstance();

    @Override
    public void execute(GameContext gameContext) {
        placeFieldObjects(gameContext);
        List<Creature> creatures = getListOfCreatures();
        placeCreatures(creatures, gameContext);
    }

    private List<Creature> getListOfCreatures() {
        List<Creature> creatures = new ArrayList<>();
        for (int i = 0; i < HERBIVORE_COUNT; i++) {
            creatures.add((Creature) entityFactory.create(Herbivore.class));
        }
        for (int i = 0; i < PREDATOR_COUNT; i++) {
            creatures.add((Creature) entityFactory.create(Predator.class));
        }
        return creatures;
    }

    private void placeCreatures(List<Creature> creatures, GameContext gameContext) {
        for (Creature creature : creatures) {
            Coordinates coordinates = gameContext.getEmptyCell();
            gameContext.addEntity(creature, coordinates);
        }
    }

    private void placeFieldObjects(GameContext gameContext) {
        for (int i = 1; i <= MAP_SIZE * MAP_SIZE; i++) {
            Coordinates coordinates = gameContext.getEmptyCell();
            FieldObject object = getFieldObject();
            if (object != null) {
                gameContext.addEntity(object, coordinates);
            }
        }
    }

    private FieldObject getFieldObject() {
        int rndm = random.nextInt(MAP_SIZE);
        return (FieldObject) switch (rndm) {
            case 0 -> entityFactory.create(Rock.class);
            case 1 -> entityFactory.create(Tree.class);
            case 2 -> entityFactory.create(Grass.class);
        };
    }
}
