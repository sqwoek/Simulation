package game.simulation;

import game.simulation.actions.Action;
import game.simulation.pathFinders.PathFinder;

import java.util.List;

public class Simulation {
    private final SimulationMap simulationMap;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final SimulationContext simulationContext;
    private final Object pauseLock = new Object();
    private volatile boolean running = true;
    private volatile boolean isPaused = false;
    private int turnCount;

    public Simulation(SimulationMap simulationMap, PathFinder pathFinder, List<Action> initActions, List<Action> turnActions) {
        this.turnCount = 0;
        this.simulationMap = simulationMap;
        this.initActions = initActions;
        this.turnActions = turnActions;
        this.simulationContext = new SimulationContext(simulationMap, pathFinder);
    }

    public void nextTurn() {
        executeActions(turnActions);
        turnCount++;
        System.out.print("Current move: " + turnCount);
        MapRenderer.printMap(simulationMap);
    }

    public void start() {
        executeActions(initActions);
        while (running) {
            synchronized (pauseLock) {
                while (isPaused) {
                    try {
                        pauseLock.wait();
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
            delayBetweenTurns();
        }
    }

    public void pause() {
        synchronized (pauseLock) {
            isPaused = true;
        }
    }

    public void stop() {
        synchronized (pauseLock) {
            running = false;
            isPaused = false;
            pauseLock.notifyAll();
        }
    }

    public void pauseSimulation() {
        synchronized (pauseLock) {
            isPaused = true;
        }
    }

    public void resume() {
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll();
        }
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(simulationContext);
        }
    }

    private static void delayBetweenTurns() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}