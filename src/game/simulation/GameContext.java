package game.simulation;

import game.ForestMap;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.Grass;
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
        Coordinates coords = getCoordinates(creature);
        if (coords == null) {
            return;
        }
        List<Coordinates> directions = getNeighboringCoordinates(coords);
        //TODO: get rid of randomizer
        for (int i = 0; i < 100; i++) {
            int idx = rndm.nextInt(directions.size());
            Coordinates next = directions.get(idx);
            if (canMove(creature, next)) {
                map.moveEntityTo(creature, next);
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

    public Coordinates getEmptyCell() {
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

            int distance = Math.abs(coords.x() - entry.getKey().x()) +
                    Math.abs(coords.y() - entry.getKey().y());
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

    public void moveTowardsTarget(Creature creature) {
        Coordinates from = getCoordinates(creature);
        Coordinates target = getNearestTargetCoords(creature);
        if (from == null || target == null) {
            return;
        }
        List<Coordinates> path = pathFinder.getPath(map, from, target);
        if (!path.isEmpty()) {
            move(creature, path.get(0));
            return;
        }
        randomMove(creature);
        return;
    }

    public boolean isTargetClose(Creature creature, Entity target) {
        Coordinates coords = getCoordinates(creature);
        Coordinates targetCoords = getCoordinates(target);
        if (coords == null || targetCoords == null) {
            return false;
        }
        return Math.abs(coords.x() - targetCoords.x()) + Math.abs(coords.y() - targetCoords.y()) == 1;
    }

    public void consume(Creature eater, Entity edible) {
        Coordinates targetCoords = getCoordinates(edible);
        if (targetCoords == null) {
            return;
        }
        map.removeEntity(targetCoords);
        map.moveEntityTo(eater, targetCoords);
    }

    public Entity getNeighbourFood(Creature creature) {
        Coordinates coords = getCoordinates(creature);
        if (coords == null) {
            return null;
        }

        List<Coordinates> neighbors = getNeighboringCoordinates(coords);
        for (Coordinates c : neighbors) {
            Entity potentialFood = map.getEntity(c);
            if (potentialFood != null && creature.isFood(potentialFood)) {
                return potentialFood;
            }
        }
        return null;
    }

    public void attack(Creature attacker, Creature target, int damage) {
        System.out.println(attacker + " " + getCoordinates(attacker) + " attacked " + getCoordinates(target));
        target.takeDamage(damage);
    }

    private static List<Coordinates> getNeighboringCoordinates(Coordinates coords) {
        return List.of(
                coords.shift(1, 0),
                coords.shift(-1, 0),
                coords.shift(0, 1),
                coords.shift(0, -1)
        );
    }

    public void removeEntity(Entity target) {
        Coordinates targetCoords = getCoordinates(target);
        map.removeEntity(targetCoords);
    }

    public Map<Coordinates, Entity> getMap() {
        return map.getMap();
    }
}
