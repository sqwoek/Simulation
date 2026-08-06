package game.simulation;

import game.simulation.actions.Action;
import game.simulation.actions.AddEntityAction;
import game.simulation.actions.InitializeAction;
import game.simulation.actions.MoveAnimalsAction;
import game.simulation.entities.creatures.Herbivore;
import game.simulation.entities.fieldObjects.Grass;
import game.simulation.factory.EntityFactory;
import game.simulation.pathFinders.BreadthPathFinder;
import game.simulation.pathFinders.PathFinder;

import java.util.ArrayList;
import java.util.List;

public class SimulationInitializer {
    private static final int MAP_WIDTH = 10;
    private static final int MAP_HEIGHT = 10;
    private static final int HERBIVORE_MINIMUM = 4;
    private static final int PREDATOR_MINIMUM = 2;
    private static final int GRASS_MINIMUM = 10;

    public static Simulation create() {
        SimulationMap simulationMap = new SimulationMap(MAP_WIDTH, MAP_HEIGHT);
        PathFinder breadthPathFinder = new BreadthPathFinder();
        EntityFactory entityFactory = new EntityFactory();

        int mapSize = MAP_WIDTH * MAP_HEIGHT;
        List<Action> initActions = new ArrayList<>();
        initActions.add(new InitializeAction(entityFactory, mapSize, HERBIVORE_MINIMUM, PREDATOR_MINIMUM));

        List<Action> turnActions = new ArrayList<>();
        turnActions.add(new AddEntityAction(entityFactory, Grass.class, GRASS_MINIMUM));
        turnActions.add(new AddEntityAction(entityFactory, Herbivore.class, HERBIVORE_MINIMUM));
        turnActions.add(new MoveAnimalsAction());

        return new Simulation(simulationMap, breadthPathFinder, initActions, turnActions);
    }
}
