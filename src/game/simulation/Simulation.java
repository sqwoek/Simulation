package game.simulation;

import game.simulation.actions.Action;
import game.simulation.factory.InitActionsFactory;
import game.simulation.factory.TurnActionsFactory;
import game.simulation.pathFinders.BreadthPathFinder;
import game.simulation.pathFinders.PathFinder;

import java.util.List;

public class Simulation {
    private final SimulationMap forestMap;
    private final List<Action> initWorldActions;
    private final List<Action> turnWorldActions;
    private final SimulationContext gameContext;
    private final Object lock = new Object();
    private volatile boolean running = true;
    private volatile boolean isPaused = false;
    private int turnCount;

    public Simulation() {
        this.forestMap = new SimulationMap();
        this.turnCount = 0;
        PathFinder pathFinder = new BreadthPathFinder();
        this.initWorldActions = InitActionsFactory.createActions();
        this.gameContext = new SimulationContext(forestMap, pathFinder);
        this.turnWorldActions = TurnActionsFactory.createActions();
    }

    public void nextTurn() {
        for (Action turnWorldAction : turnWorldActions) {
            turnWorldAction.execute(gameContext);
        }
    }

    public void startSimulation() {
        for (Action initWorldAction : initWorldActions) {
            initWorldAction.execute(gameContext);
        }
        while (running) {
            synchronized (lock) {
                while (isPaused) {
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            if (!running) {
                return;
            }
            nextTurn();
            MapRenderer.printMap(forestMap);
            turnCount++;
            delayBetweenTurns();
        }
    }

    private static void delayBetweenTurns() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void stopSimulation() {
        synchronized (lock) {
            running = false;
            isPaused = false;
            lock.notifyAll();
        }
    }

    public void pauseSimulation() {
        synchronized (lock) {
            isPaused = true;
        }
    }

    public void resumeSimulation() {
        synchronized (lock) {
            isPaused = false;
            lock.notifyAll();
        }
    }

    public int getTurnCount() {
        return turnCount;
    }
}