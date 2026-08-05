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
    private static final int GRASS_MINIMUM = 4;
    private static final int HERBIVORE_MINIMUM = 4;
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
        return pathFinder.findPath(map, from, to);
    }

    public boolean canMove(Creature creature, Coordinates target) {
        if (!map.isWithinBorders(target)) {
            return false;
        }
        if (map.isEmpty(target)) {
            return true;
        }
        Entity entity = map.getEntity(target);

        return isEdibleFor(entity, creature);
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

        Coordinates nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<Coordinates, Entity> entry : map.getMap().entrySet()) {
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

    public boolean isTargetClose(Creature creature, Coordinates target) {
        Coordinates myPos = getCoordinates(creature);
        return Math.abs(myPos.getX() - target.getX()) + Math.abs(myPos.getY() - target.getY()) == 1;
    }

    public void move(Creature creature, Coordinates coordinates) {
        if (canMove(creature, coordinates)) {
            map.moveEntityTo(creature, coordinates);
        } else  {
            randomMove(creature);
        }
    }

    public void devourEntity(Creature creature, Coordinates targetCoords) {
        Entity edible = map.getEntity(targetCoords);
        if (creature instanceof Predator && edible instanceof Herbivore) {
            ((Herbivore) edible).takeDamage(((Predator) creature).getAttack());
            if (((Herbivore) edible).getHealth() <= 0) {
                map.removeEntity(targetCoords);
                map.moveEntityTo(creature, targetCoords);
            }
        }
        if (creature instanceof Herbivore && edible instanceof Grass) {
            map.removeEntity(targetCoords);
            map.moveEntityTo(creature, targetCoords);
            System.out.println("Rabbit ate a grass! Rabbit's coords are " + map.getCurrentCoords(creature) + " Grass coords were "
                    + targetCoords);
        }
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

    public Map<Coordinates, Entity> getMap() {
        return map.getMap();
    }
}
