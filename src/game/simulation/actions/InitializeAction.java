package game.simulation.actions;

import game.simulation.Coordinates;
import game.simulation.SimulationContext;
import game.simulation.entities.Entity;
import game.simulation.entities.creatures.Creature;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.creatures.Predator;
import game.simulation.entities.fieldObjects.Grass;
import game.simulation.entities.fieldObjects.Rock;
import game.simulation.entities.fieldObjects.Tree;
import game.simulation.factory.EntityFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class InitializeAction implements Action {
    private static final double OBJECT_COVERAGE = 0.3;
    private static final int SPAWN_RANGE = 3;
    private static final int ROCK_INDEX = 0;
    private static final int TREE_INDEX = 1;
    private static final int GRASS_INDEX = 2;
    private final int mapSize;
    private final int herbivoreMinimum;
    private final int predatorMinimum;
    private final Random random = new Random();
    private final EntityFactory entityFactory;

    public InitializeAction(EntityFactory entityFactory, int mapSize, int herbivoreMinimum, int predatorMinimum) {
        this.mapSize = mapSize;
        this.herbivoreMinimum = herbivoreMinimum;
        this.predatorMinimum = predatorMinimum;
        this.entityFactory = entityFactory;
    }

    @Override
    public void execute(SimulationContext simulationContext) {
        placeFieldObjects(simulationContext);
        List<Creature> creatures = getListOfCreatures();
        placeCreatures(creatures, simulationContext);
    }

    private List<Creature> getListOfCreatures() {
        List<Creature> creatures = new ArrayList<>();
        for (int i = 0; i < herbivoreMinimum; i++) {
            creatures.add((Creature) entityFactory.create(Herbivore.class));
        }
        for (int i = 0; i < predatorMinimum; i++) {
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
        for (int i = 1; i <= mapSize * OBJECT_COVERAGE; i++) {
            Coordinates coordinates = simulationContext.getEmptyCell();
            Entity object = getFieldObject();
            simulationContext.addEntity(object, coordinates);
        }
    }

    private Entity getFieldObject() {
        int randomIndex = random.nextInt(SPAWN_RANGE);
        return switch (randomIndex) {
            case ROCK_INDEX -> entityFactory.create(Rock.class);
            case TREE_INDEX -> entityFactory.create(Tree.class);
            case GRASS_INDEX -> entityFactory.create(Grass.class);
            default -> throw new IllegalStateException("Unexpected value: " + randomIndex);
        };
    }
}