package game.simulation;

import game.simulation.actions.Action;
import game.simulation.pathFinders.PathFinder;

import java.util.List;

public class Simulation {
    private final SimulationMap simulationMap;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private final SimulationContext gameContext;
    private final Object lock = new Object();
    private volatile boolean running = true;
    private volatile boolean isPaused = false;
    private int turnCount;

    public Simulation(SimulationMap simulationMap, PathFinder pathFinder, List<Action> initActions, List<Action> turnActions) {
        this.turnCount = 0;
        this.simulationMap = simulationMap;
        this.initActions = initActions;
        this.turnActions = turnActions;
        this.gameContext = new SimulationContext(simulationMap, pathFinder);
    }

    public void nextTurn() {
        for (Action turnWorldAction : turnActions) {
            turnWorldAction.execute(gameContext);
        }
        turnCount++;
        System.out.print("Current move: " + turnCount);
    }

    public void startSimulation() {
        for (Action initWorldAction : initActions) {
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
            MapRenderer.printMap(simulationMap);
            delayBetweenTurns();
        }
    }

    private static void delayBetweenTurns() {
        try {
            Thread.sleep(1500);
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