package game.simulation;

import game.ForestMap;
import game.MapRenderer;
import game.simulation.creatures.Creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Simulation {
    private final ForestMap forestMap;
    private final Map<Coordinates, Entity> map;
    private boolean running = false;
    private int turnCount;

    public Simulation() {
        this.forestMap = new ForestMap();
        this.map = forestMap.getMap();
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
            Coordinates currentCoords = forestMap.getCurrentCoords(creature);
            if (currentCoords == null) {
                continue;
            }
            creature.makeMove(forestMap);
            Coordinates newCoords = forestMap.getCurrentCoords(creature);
            if (!currentCoords.equals(newCoords)) {
                forestMap.moveCreatureTo(creature, newCoords);
            }
        }
    }

    public void startSimulation() {
        MapRenderer.printMap(map);
        running = true;
        while (running) {
            nextTurn();
            System.out.println("TURN " + turnCount);
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