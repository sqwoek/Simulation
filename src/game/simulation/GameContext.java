package game.simulation;

import game.ForestMap;
import game.simulation.creatures.Creature;
import game.simulation.creatures.Herbivore;
import game.simulation.creatures.Predator;
import game.simulation.fieldObjects.Grass;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameContext {
    private final ForestMap map;
    private final BreadthPathFinder pathFinder;
    private final Random rndm = new Random();

    public GameContext(ForestMap map, BreadthPathFinder pathFinder) {
        this.map = map;
        this.pathFinder = pathFinder;
    }

    public Coordinates getCoordinates(Entity entity) {
        return map.getCurrentCoords(entity);
    }

    public List<Coordinates> findPath(Creature creature, Coordinates to) {
        Coordinates from = map.getCurrentCoords(creature);
        return pathFinder.findPath(map, creature, from, to);
    }

    public boolean canMove(Creature creature, Coordinates target) {
        if (map.isCellEmpty(target)) {
            return true;
        }
        Entity entity = map.getEntity(target);

        if (creature instanceof Herbivore && entity instanceof Grass) {
            return true;
        }
        if (creature instanceof Predator && entity instanceof Herbivore) {
            return true;
        }
        return false;
    }

    public Coordinates findNearestTarget(Creature creature) {
        Coordinates coords = getCoordinates(creature);
        if (coords == null) {
            return null;
        }

        Class<? extends Entity> targetType = null;
        if (creature instanceof Herbivore) {
            targetType = Grass.class;
        }
        if (creature instanceof Predator) {
            targetType = Herbivore.class;
        }

        if (targetType == null) {
            throw new RuntimeException("Couldn't find target for entity: " + creature.getClass());
        }

        Map<Coordinates, Entity> allEntities = map.getAllEntities();

        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<Coordinates, Entity> entry : allEntities.entrySet()) {
            if (targetType.isInstance(entry.getValue())) {
                int distance = Math.abs(coords.getX() - entry.getKey().getX()) +
                        Math.abs(coords.getY() - entry.getKey().getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = entry.getKey();
                }
            }
        }
        return nearest;
    }

    public Coordinates randomMove(Creature creature) {
        Coordinates pos = getCoordinates(creature);
        List<Coordinates> directions = List.of(
                pos.shift(1, 0),
                pos.shift(-1, 0),
                pos.shift(0, 1),
                pos.shift(0, -1)
        );
        for (int i = 0; i < directions.size(); i++) {
            int idx = rndm.nextInt(directions.size());
            Coordinates next = directions.get(idx);
            if (canMove(creature, next)) {
                return next;
            }
        }
        return pos;
    }

    public boolean isTargetClose(Creature creature, Coordinates target) {
        Coordinates myPos = getCoordinates(creature);
        return Math.abs(myPos.getX() - target.getX()) + Math.abs(myPos.getY() - target.getY()) == 1;
    }

    public void move(Creature creature, Coordinates coordinates) {
        map.moveEntityTo(creature, coordinates);
    }
}
