package game.simulation;

import game.ForestMap;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.Grass;
import game.simulation.pathFinders.BreadthPathFinder;
import game.simulation.pathFinders.PathFinder;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameContext {
    private static final int GRASS_MINIMUM = 4;
    private static final int HERBIVORE_MINIMUM = 4;
    private final ForestMap map;
    private final PathFinder pathFinder;
    private final Random rndm = new Random();

    public GameContext(ForestMap map, PathFinder pathFinder) {
        this.map = map;
        this.pathFinder = pathFinder;
    }

    public void move(Creature creature, Coordinates coordinates) {
        if (canMove(creature, coordinates)) {
            map.moveEntityTo(creature, coordinates);
        } else {
            randomMove(creature);
        }
    }

    public boolean canMove(Creature creature, Coordinates targetCoords) {
        if (!map.isWithinBorders(targetCoords)) {
            return false;
        }
        if (map.isEmpty(targetCoords)) {
            return true;
        }
        Entity target = map.getEntity(targetCoords);
        return creature.isFood(target);
    }

    public boolean isEdibleFor(Entity entity, Creature creature) {
        if (creature instanceof Herbivore && entity instanceof Grass) {
            return true;
        }
        if (creature instanceof Predator && entity instanceof Herbivore) {
            return true;
        }
        return false;
    }

    public void randomMove(Creature creature) {
        System.out.println(creature + " want to random move");
        Coordinates pos = getCoordinates(creature);
        if (pos == null) {
            return;
        }
        List<Coordinates> directions = List.of(
                pos.shift(1, 0),
                pos.shift(-1, 0),
                pos.shift(0, 1),
                pos.shift(0, -1)
        );
        //TODO: get rid of randomizer
        for (int i = 0; i < 100; i++) {
            int idx = rndm.nextInt(directions.size());
            Coordinates next = directions.get(idx);
            if (canMove(creature, next)) {
                map.moveEntityTo(creature, next);
                System.out.println(creature + " random move to " + next);
                return;
            }
        }
    }

    public Coordinates getCoordinates(Entity entity) {
        return map.getCurrentCoords(entity);
    }

    public boolean needAddEntity(Entity entity) {
        long count = map.getMap().values().stream()
                .filter(e -> entity.getClass().isInstance(e))
                .count();
        if (entity instanceof Grass) {
            return count < GRASS_MINIMUM;
        }
        if (entity instanceof Herbivore) {
            return count < HERBIVORE_MINIMUM;
        }
        return false;
    }

    public void addEntity(Entity entity, Coordinates coords) {
        map.placeEntity(coords, entity);
    }

    public Coordinates findEmptyCell() {
        while (true) {
            int x = rndm.nextInt(map.getWidth()) + 1;
            int y = rndm.nextInt(map.getHeight()) + 1;
            Coordinates coords = new Coordinates(x, y);
            if (map.isEmpty(coords)) {
                return coords;
            }
        }
    }

    public Coordinates getNearestTargetCoords(Creature creature) {
        Coordinates coords = getCoordinates(creature);
        if (coords == null) {
            return null;
        }
        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<Coordinates, Entity> entry : map.getMap().entrySet()) {
            Entity possibleTarget = entry.getValue();
            if (!creature.isFood(possibleTarget)) {
                continue;
            }

            int distance = Math.abs(coords.getX() - entry.getKey().getX()) +
                    Math.abs(coords.getY() - entry.getKey().getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = entry.getKey();
            }
        }
        return nearest;
    }

    public boolean hasTarget(Creature creature) {
        if (getNearestTargetCoords(creature) != null) {
            return true;
        }
        return false;
    }

    public boolean moveTowardsTarget(Creature creature) {
        Coordinates from = getCoordinates(creature);
        Coordinates target = getNearestTargetCoords(creature);
        if (from == null || target == null) {
            //TODO: check this
            if (from == null) {
                System.out.println("Who i am?");
            }
            if (target == null) {
                System.out.println("I have no target");
            }
            return false;
        }
        List<Coordinates> path = pathFinder.getPath(map, from, target);
        if (!path.isEmpty()) {
            System.out.println(from + creature.toString() + " moving onto " + path.get(0));
            move(creature, path.get(0));
            return true;
        }
        randomMove(creature);
        return false;
    }

    public boolean isTargetClose(Creature creature) {
        Coordinates coords = getCoordinates(creature);
        Coordinates target = getNearestTargetCoords(creature);
        if (coords == null || target == null) {
            return false;
        }
        return Math.abs(coords.getX() - target.getX()) + Math.abs(coords.getY() - target.getY()) == 1;
    }

    public void devourEntity(Creature creature) {
        Coordinates targetCoords = getNearestTargetCoords(creature);
        if (targetCoords == null || !isTargetClose(creature)) {
            return;
        }
        Entity target = map.getEntity(targetCoords);
        if (target == null || !creature.isFood(target)) {
            return;
        }

        creature.devourTarget(this, target);
    }

    public void consume(Creature eater, Entity edible) {
        Coordinates targetCoords = getCoordinates(edible);
        if (targetCoords == null) {
            return;
        }
        map.removeEntity(targetCoords);
        map.moveEntityTo(eater, targetCoords);
    }
}
