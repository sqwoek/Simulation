package game.simulation;

import game.MapFabric;
import game.MapRenderer;
import game.simulation.creatures.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulation {
    private final MapFabric fabric;
    private final Map<Coordinates, Entity> map;
    private boolean running = false;
    private int turnCount;

    public Simulation() {
        this.fabric = new MapFabric();
        this.map = fabric.getMap();
        this.turnCount = 0;
        startSimulation();
    }

    public void nextTurn() {
        List<Creature> creatures = new ArrayList<>();
        for (Entity entity : map.values()) {
            if (entity instanceof Creature creature) {
                creatures.add(creature);
            }
        }

        for (Creature creature : creatures) {
            Coordinates coords = creature.getCoordinates();
            Map<Coordinates, Entity> worldView = fabric.getObjectsAround(coords, creature.getSpeed());
            creature.makeMove(worldView);

            if (!coords.equals(creature.getCoordinates())) {
                map.remove(coords);
                map.put(creature.getCoordinates(), creature);
            }
        }
        // remove dead animals

        // update world if needed
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