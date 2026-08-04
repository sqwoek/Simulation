package game.simulation;

import game.ForestMap;
import game.MapRenderer;
import game.simulation.factory.InitActionsFactory;
import game.simulation.factory.TurnActionsFactory;

import java.util.List;

public class Simulation {
    private final ForestMap forestMap;
    private final List<WorldAction> initWorldActions;
    private final List<WorldAction> turnWorldActions;
    private boolean running = false;
    private int turnCount;

    public Simulation() {
        this.forestMap = new ForestMap();
        this.turnCount = 0;
        this.initWorldActions = InitActionsFactory.createActions();
        this.turnWorldActions = TurnActionsFactory.createActions();
    }

    public void nextTurn() {
        for (WorldAction turnWorldAction : turnWorldActions) {
            turnWorldAction.execute(forestMap);
        }
    }

    public void startSimulation() {
        for (WorldAction initWorldAction : initWorldActions) {
            initWorldAction.execute(forestMap);
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