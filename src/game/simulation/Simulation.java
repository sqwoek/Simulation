package game.simulation;

import game.MapFabric;
import game.MapRenderer;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;

import java.util.Map;

public class Simulation {
    private final Map<Coordinates, Entity> map;
    public Creature herbivore;
    public Creature predator;
    private boolean running = false;
    private int turnCount;
    // actions

    public Simulation() {
        this.map = new MapFabric().getMap();
        this.turnCount = 0;
        this.herbivore = new Herbivore(1);
        this.predator = new Predator(2, 20);
        startSimulation();
    }

    public void nextTurn() {
        herbivore.makeMove();
        predator.makeMove();
    }

    public void startSimulation() {
        running = true;
        while (running) {
            MapRenderer.printMap(map);
            nextTurn();
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