package game.simulation;

import game.simulation.entities.creatures.Creature;
import game.simulation.entities.Entity;
import game.simulation.pathFinders.PathFinder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SimulationContext {
    private final SimulationMap map;
    private final PathFinder pathFinder;
    private final Random rndm = new Random();

    public SimulationContext(SimulationMap map, PathFinder pathFinder) {
        this.map = map;
        this.pathFinder = pathFinder;
    }

    public void randomMove(Creature creature) {
        Optional<Coordinates> coordinatesOpt = getCoordinates(creature);
        if (coordinatesOpt.isEmpty()) {
            return;
        }
        Coordinates coordinates = coordinatesOpt.get();
        List<Coordinates> directions = getNeighbourCoordinates(coordinates);

        for (Coordinates next : directions) {
            if (canMove(creature, next)) {
                moveEntityTo(creature, next);
                return;
            }
        }
    }

    public Optional<Coordinates> getCoordinates(Entity entity) {
        return map.getCoordinates(entity);
    }

    public long getEntityTypeCount(Entity entity) {
        return map.getMap().values().stream()
                .filter(e -> entity.getClass().isInstance(e))
                .count();
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

    public boolean hasTarget(Creature creature) {
        return getNearestTargetCoordinates(creature).isPresent();
    }

    public void moveTowardsTarget(Creature creature) {
        Optional<Coordinates> fromOpt = getCoordinates(creature);
        Optional<Coordinates> targetOpt = getNearestTargetCoordinates(creature);
        if (fromOpt.isEmpty() || targetOpt.isEmpty()) {
            return;
        }

        Coordinates from = fromOpt.get();
        Coordinates target = targetOpt.get();

        List<Coordinates> path = pathFinder.getPath(map, from, target);
        if (!path.isEmpty()) {
            move(creature, path.get(0));
            return;
        }
        randomMove(creature);
    }

    public boolean isTargetClose(Creature creature, Entity target) {
        Optional<Coordinates> coordinatesOpt = getCoordinates(creature);
        Optional<Coordinates> targetCoordinatesOpt = getCoordinates(target);
        if (coordinatesOpt.isEmpty() || targetCoordinatesOpt.isEmpty()) {
            return false;
        }
        Coordinates coordinates = coordinatesOpt.get();
        Coordinates targetCoordinates = targetCoordinatesOpt.get();
        return Math.abs(coordinates.x() - targetCoordinates.x()) + Math.abs(coordinates.y() - targetCoordinates.y()) == 1;
    }

    public void consume(Creature eater, Entity edible) {
        Optional<Coordinates> targetCoordinatesOpt = getCoordinates(edible);
        if (targetCoordinatesOpt.isEmpty()) {
            return;
        }
        Coordinates targetCoordinates = targetCoordinatesOpt.get();
        map.removeEntity(targetCoordinates);
        moveEntityTo(eater, targetCoordinates);
    }

    public Optional<Entity> getNeighbourFood(Creature creature) {
        Optional<Coordinates> coordinatesOpt = getCoordinates(creature);
        if (coordinatesOpt.isEmpty()) {
            return Optional.empty();
        }
        Coordinates coordinates = coordinatesOpt.get();

        List<Coordinates> neighbors = getNeighbourCoordinates(coordinates);
        for (Coordinates c : neighbors) {
            Optional<Entity> potentialFood = map.getEntity(c);
            if (potentialFood.isPresent() && creature.isFood(potentialFood.get())) {
                return potentialFood;
            }
        }
        return Optional.empty();
    }

    public void attack(Creature attacker, Creature target, int damage) {
        target.takeDamage(damage);
    }

    private Optional<Coordinates> getNearestTargetCoordinates(Creature creature) {
        Optional<Coordinates> coordinatesOpt = getCoordinates(creature);
        if (coordinatesOpt.isEmpty()) {
            return Optional.empty();
        }
        Coordinates coordinates = coordinatesOpt.get();
        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<Coordinates, Entity> entry : map.getMap().entrySet()) {
            Entity possibleTarget = entry.getValue();
            if (!creature.isFood(possibleTarget)) {
                continue;
            }

            int distance = Math.abs(coordinates.x() - entry.getKey().x()) +
                    Math.abs(coordinates.y() - entry.getKey().y());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = entry.getKey();
            }
        }
        return Optional.ofNullable(nearest);
    }

    private static List<Coordinates> getNeighbourCoordinates(Coordinates coords) {
        return List.of(
                coords.shift(1, 0),
                coords.shift(-1, 0),
                coords.shift(0, 1),
                coords.shift(0, -1)
        );
    }

    private void move(Creature creature, Coordinates coordinates) {
        if (canMove(creature, coordinates)) {
            moveEntityTo(creature, coordinates);
        } else {
            randomMove(creature);
        }
    }

    private boolean canMove(Creature creature, Coordinates targetCoordinates) {
        if (!map.isWithinBorders(targetCoordinates)) {
            return false;
        }
        if (map.isEmpty(targetCoordinates)) {
            return true;
        }
        Optional<Entity> targetOpt = map.getEntity(targetCoordinates);
        if (targetOpt.isEmpty()) {
            return false;
        }
        return creature.isFood(targetOpt.get());
    }

    private void moveEntityTo(Entity entity, Coordinates targetCoordinates) {
        Optional<Coordinates> coordinatesOpt = getCoordinates(entity);
        if (coordinatesOpt.isEmpty()) {
            throw new IllegalStateException("Cannot move entity not on map: " + entity);
        }
        Coordinates coordinates = coordinatesOpt.get();
        map.removeEntity(coordinates);
        map.placeEntity(targetCoordinates, entity);
    }

    public Map<Coordinates, Entity> getMap() {
        return map.getMap();
    }
}
