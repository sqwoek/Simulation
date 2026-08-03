package game.simulation;

import game.ForestMap;
import game.MapRenderer;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.fieldObjects.Grass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulation {
    private final ForestMap forestMap;
    private final Map<Coordinates, Entity> map;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private boolean running = false;
    private int turnCount;

    public Simulation() {
        this.forestMap = new ForestMap();
        this.map = forestMap.getMap();
        this.turnCount = 0;
        this.initActions = InitActionsFactory.createActions();
        this.turnActions = TurnActionsFactory.createActions();
        startSimulation();
    }

    public void nextTurn() {
        for (Action turnAction : turnActions) {
            turnAction.execute(forestMap);
        }
    }

    public void startSimulation() {
        for (Action initAction : initActions) {
            initAction.execute(forestMap);
        }
        MapRenderer.printMap(map);
        running = true;
        while (running) {
            nextTurn();
            MapRenderer.printMap(map);
            turnCount++;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                System.out.println("error");
            }
        }
    }

    public void pauseSimulation() {
        running = false;
    }

    public int getTurnCount() {
        return turnCount;
    }
}