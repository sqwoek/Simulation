package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.SimulationContext;
import game.simulation.entities.creatures.Creature;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.creatures.Predator;
import game.simulation.entities.fieldObjects.FieldObject;
import game.simulation.entities.fieldObjects.Grass;
import game.simulation.entities.fieldObjects.Rock;
import game.simulation.entities.fieldObjects.Tree;
import game.simulation.factory.EntityFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class InitializeAction implements Action {
    private static final int MAP_SIZE = 10;
    private static final int HERBIVORE_COUNT = 4;
    private static final int PREDATOR_COUNT = 2;
    private static final int SPAWN_RANGE = 10;
    private static final int ROCK_INDEX = 0;
    private static final int TREE_INDEX = 1;
    private static final int GRASS_INDEX = 2;
    private final Random random = new Random();
    private final EntityFactory entityFactory = EntityFactory.getInstance();

    @Override
    public void execute(SimulationContext simulationContext) {
        placeFieldObjects(simulationContext);
        List<Creature> creatures = getListOfCreatures();
        placeCreatures(creatures, simulationContext);
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

    private void placeCreatures(List<Creature> creatures, SimulationContext simulationContext) {
        for (Creature creature : creatures) {
            Coordinates coordinates = simulationContext.getEmptyCell();
            simulationContext.addEntity(creature, coordinates);
        }
    }

    private void placeFieldObjects(SimulationContext simulationContext) {
        int fieldObjectsCount = (MAP_SIZE * MAP_SIZE);
        for (int i = 1; i <= fieldObjectsCount; i++) {
            Coordinates coordinates = simulationContext.getEmptyCell();
            Optional<FieldObject> objectOpt = getFieldObject();
            if (objectOpt.isPresent()) {
                simulationContext.addEntity(objectOpt.get(), coordinates);
            }
        }
    }

    private Optional<FieldObject> getFieldObject() {
        int chanceRoll = random.nextInt(SPAWN_RANGE);
        return Optional.ofNullable((FieldObject) switch (chanceRoll) {
            case ROCK_INDEX -> entityFactory.create(Rock.class);
            case TREE_INDEX -> entityFactory.create(Tree.class);
            case GRASS_INDEX -> entityFactory.create(Grass.class);
            default -> null;
        });
    }
}