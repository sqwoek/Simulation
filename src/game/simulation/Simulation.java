package game.simulation;

import game.ForestMap;
import game.MapRenderer;
import game.simulation.actions.factory.InitActionsFactory;
import game.simulation.actions.factory.TurnActionsFactory;
import game.simulation.actions.Action;

import java.util.List;

public class Simulation {
    private final ForestMap forestMap;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private boolean running = false;
    private int turnCount;

    public Simulation() {
        this.forestMap = new ForestMap();
        this.turnCount = 0;
        this.initActions = InitActionsFactory.createActions();
        this.turnActions = TurnActionsFactory.createActions();
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
        MapRenderer.printMap(forestMap);
        running = true;
        while (running) {
            nextTurn();
            MapRenderer.printMap(forestMap);
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

    public void resumeSimulation() {
        running = true;
    }

    public int getTurnCount() {
        return turnCount;
    }
}